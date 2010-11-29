/*
 * CDDL HEADER START
 *
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the "License").
 * You may not use this file except in compliance with the License.
 *
 * You can obtain a copy of the license at
 * src/com/vodafone360/people/VODAFONE.LICENSE.txt or
 * http://github.com/360/360-Engine-for-Android
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * When distributing Covered Code, include this CDDL HEADER in each file and
 * include the License file at src/com/vodafone360/people/VODAFONE.LICENSE.txt.
 * If applicable, add the following below this CDDL HEADER, with the fields
 * enclosed by brackets "[]" replaced with your own identifying information:
 * Portions Copyright [yyyy] [name of copyright owner]
 *
 * CDDL HEADER END
 *
 * Copyright 2010 Vodafone Sales & Services Ltd.  All rights reserved.
 * Use is subject to license terms.
 */
package com.vodafone360.people.engine.groups;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.vodafone360.people.database.DatabaseHelper;
import com.vodafone360.people.datatypes.BaseDataType;
import com.vodafone360.people.datatypes.GroupItem;
import com.vodafone360.people.datatypes.ItemList;
import com.vodafone360.people.engine.BaseEngine;
import com.vodafone360.people.engine.IEngineEventCallback;
import com.vodafone360.people.engine.EngineManager.EngineId;
import com.vodafone360.people.service.ServiceStatus;
import com.vodafone360.people.service.ServiceUiRequest;
import com.vodafone360.people.service.agent.NetworkAgent;
import com.vodafone360.people.service.io.ResponseQueue.DecodedResponse;
import com.vodafone360.people.service.io.api.GroupPrivacy;
import com.vodafone360.people.utils.LogUtils;

public class GroupsEngine extends BaseEngine {
    /**
     * Max number of groups to fetch from server in one request.
     */
    private static final int MAX_DOWN_PAGE_SIZE = 24;

    /**
     * Current page number being fetched.
     */
    private int mPageNo;
    
    /**
     * The DatabaseHelper instance.
     */
    private DatabaseHelper mDb;
    
    /**
     * The list containing all the groups received from the backend.
     */
    private ArrayList<GroupItem> mReceivedGroups = new ArrayList<GroupItem>();
    
    
    public GroupsEngine(Context context, IEngineEventCallback eventCallback, DatabaseHelper db) {
        super(eventCallback);
        mEngineId = EngineId.GROUPS_ENGINE;
        mDb = db;
    }
    
    @Override
    public long getNextRunTime() {
        // we only run if we have a request or a response in the queue
        if (isUiRequestOutstanding() || isCommsResponseOutstanding()) {
            return 0;
        }
        
        return -1;
    }

    @Override
    public void onCreate() {
        // nothing needed
    }

    @Override
    public void onDestroy() {
        // nothing needed
    }

    @Override
    protected void onTimeoutEvent() {
    }

    @Override
    protected void processCommsResponse(DecodedResponse resp) {
        LogUtils.logD("DownloadGroups.processCommsResponse()");
        
        ServiceStatus status = BaseEngine.getResponseStatus(BaseDataType.ITEM_LIST_DATA_TYPE, resp.mDataTypes);
        if (status == ServiceStatus.SUCCESS) {
        	
            // keep track of the number of groups received in this response
            int responseGroupsCount = 0;
            
            for (int i = 0; i < resp.mDataTypes.size(); i++) {
                ItemList itemList = (ItemList)resp.mDataTypes.get(i);
                if (itemList.mType != ItemList.Type.group_privacy) {
                    completeUiRequest(ServiceStatus.ERROR_UNEXPECTED_RESPONSE);
                    return;
                }
                
                // Store the received Groups
                for (int j = 0; j < itemList.mItemList.size(); j++) {
                    mReceivedGroups.add((GroupItem)itemList.mItemList.get(j));
                }
                responseGroupsCount += itemList.mItemList.size();
            }
            LogUtils.logI("DownloadGroups.processCommsResponse() - Current count of received Groups "
                    + mReceivedGroups.size());

            if (responseGroupsCount < MAX_DOWN_PAGE_SIZE) {
                
                // we received all the Groups, let's reflect that to the database 
                status = updateGroupsDatabase();
                completeUiRequest(status);
                return;
            }
            mPageNo++;
            requestNextGroupsPage();
            return;
        }
        LogUtils.logE("DownloadGroups.processCommsResponse() - Error requesting Zyb groups, error = " + status);
        completeUiRequest(status);
    }

    @Override
    protected void onRequestComplete() {

        reset();
    }

    @Override
    protected void processUiRequest(ServiceUiRequest requestId, Object data) {
        // for now we only serve the get groups call. later we might need to 
        // differentiate between multiple ServiceUiRequests
        switch (requestId) {
            case GET_GROUPS:
                requestFirstGroupsPage();
                break;
        }
    }

    @Override
    public void run() {
        if (isUiRequestOutstanding() && processUiQueue()) {
            return;
        }
        if (isCommsResponseOutstanding() && processCommsInQueue()) {
            return;
        }
        if (processTimeout()) {
            return;
        }
    }

    /**
     * 
     * Adds a request to get groups from the backend that are associated with
     * the server contacts.
     * 
     */
    public void addUiGetGroupsRequest() {
        LogUtils.logI("GroupsEngine.addUiGetGroupsRequest()");
        addUiRequestToQueue(ServiceUiRequest.GET_GROUPS, null);
    }
    
    /**
     * Requests the first group page.
     */
    private void requestFirstGroupsPage() {
        
        reset();
        requestNextGroupsPage();
    }
    
    /**
     * Requests the next page of groups from the server.
     */
    private void requestNextGroupsPage() {
        if (NetworkAgent.getAgentState() != NetworkAgent.AgentState.CONNECTED) {
            completeUiRequest(ServiceStatus.ERROR_COMMS);
            return;
        }
        int reqId = GroupPrivacy.getGroups(this, mPageNo, MAX_DOWN_PAGE_SIZE);
        setReqId(reqId);
    }
    
    /**
     * Resets the engine.
     */
    private void reset() {
        
        mPageNo = 0;
        mReceivedGroups.clear();
    }
    
    @Override
    public void onReset() {
        
        reset();
        super.onReset();
    }
    
    /**
     * Updates the database with the received groups.
     */
    private ServiceStatus updateGroupsDatabase() {
        
        // the list of groups currently in the database
        final ArrayList<GroupItem> dbGroups = new ArrayList<GroupItem>();
        // the list of groups to modify
        final ArrayList<GroupItem> groupsToModifiy = new ArrayList<GroupItem>();
        // the list of groups to remove
        final ArrayList<GroupItem> groupsToRemove = new ArrayList<GroupItem>();
        // status of database access
        ServiceStatus status;
        
        // add client system groups to the list of received groups
        mDb.getSystemGroups(mReceivedGroups);
        
        // fetch the Groups from the database
        status = mDb.fetchGroupList(dbGroups);
        
        if (status != ServiceStatus.SUCCESS) {
            return status;
        }

        // compare the list of groups in the database with the list of received groups
        // to find out what has changed.
        for (GroupItem groupItem : dbGroups) {
            
            final int index = getGroupIndex(mReceivedGroups, groupItem.mId);
            if (index != -1) {
                // group existing in the database
                final GroupItem receivedGroup = mReceivedGroups.get(index);
                // Note: The system groups have a localized name field which might be different 
                //       to the group name stored in the db. We need to sort them out manually.
                //       So we exclude all receivedGroup.mLocalGroupId != null since they are
                //       useless anyway.
                if (!receivedGroup.isSameAs(groupItem) && receivedGroup.mLocalGroupId != null) {
                    // this group has been modified
                    groupsToModifiy.add(receivedGroup);
                }
                // remove it from the list since we're done with that one
                // the remaining groups will be the ones to add to the database
                mReceivedGroups.remove(index);
            } else {
                // not found in the received groups, need to remove it from the database
                groupsToRemove.add(groupItem);
            }
        }
        
        /*
         * Reflect the changes in the database:
         * -groupsToModify => the groups to update
         * -groupsToRemove => the groups to remove
         * -mReceivedGroups => the groups to add
         */ 
        status = mDb.updateGroupsTable(mReceivedGroups, groupsToModifiy, groupsToRemove);

        return status;
    }
    
    /**
     * Finds the index of a group in a list of groups.
     * 
     * @param groupList the list of groups where to search
     * @param id the id of the group to find
     * @return the zero based index of the group or -1 if not found 
     */
    private int getGroupIndex(List<GroupItem> groupList, long id) {
        
        for (int i = groupList.size() - 1; i >= 0 ; i--) {
            
            final GroupItem groupItem = groupList.get(i);
            
            if (groupItem.mId != null && groupItem.mId == id) {
                return i;
            }
        }
        
        return -1;
    }
}
