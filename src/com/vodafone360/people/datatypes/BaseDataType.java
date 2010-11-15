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

package com.vodafone360.people.datatypes;

/**
 * BaseDataType - all specific data types derive from this.
 */
abstract public class BaseDataType {
    /**
     * Get the data type
     * @return The data-type.
     */
    abstract public int getType();
    
    /**
     * Unknown data type
     */
    public static final int UNKNOWN_DATA_TYPE = 0;
    /**
     * Activity Contact data type
     */
    public static final int ACTIVITY_CONTACT_DATA_TYPE = 1;
    /**
     * Activity item data type
     */
    public static final int ACTIVITY_ITEM_DATA_TYPE = 2;
    /**
     * Auth Session Holder data type
     */
    public static final int AUTH_SESSION_HOLDER_TYPE = 3;
    /**
     * Chat Msg data type
     */
    public static final int CHAT_MSG_DATA_TYPE = 4;
    /**
     * Contact data type
     */
    public static final int CONTACT_DATA_TYPE = 5;
    /**
     * Contact Changes data type
     */
    public static final int CONTACT_CHANGES_DATA_TYPE = 6;
    /**
     * Contact Detail data type
     */
    public static final int CONTACT_DETAIL_DATA_TYPE = 7;
    /**
     * Contact Detail Deletion data type
     */
    public static final int CONTACT_DETAIL_DELETION_DATA_TYPE = 8;
    /**
     * Contact List Reponse data type
     */
    public static final int CONTACT_LIST_RESPONSE_DATA_TYPE = 9;
    /**
     * Conversation data type
     */
    public static final int CONVERSATION_DATA_TYPE = 10;
    /**
     * External Response Object data type
     */
    public static final int EXTERNAL_RESPONSE_OBJECT_DATA_TYPE = 11;
    /**
     * Group Item data type
     */
    public static final int GROUP_ITEM_DATA_TYPE = 12;
    /**
     * My Identity data type
     */
    public static final int MY_IDENTITY_DATA_TYPE = 13;
    /**
     * Available Identity data type
     */
    public static final int AVAILABLE_IDENTITY_DATA_TYPE = 14;
    /**
     * Identity Capability data type
     */
    public static final int IDENTITY_CAPABILITY_DATA_TYPE = 15;
    /**
     * Item List data type
     */
    public static final int ITEM_LIST_DATA_TYPE = 16;
    /**
     * Presence List data type
     */
    public static final int PRESENCE_LIST_DATA_TYPE = 17;
    /**
     * Public Key Details data type
     */
    public static final int PUBLIC_KEY_DETAILS_DATA_TYPE = 18;
    /**
     * Push Event data type
     */
    public static final int PUSH_EVENT_DATA_TYPE = 19;
    /**
     * Server Error data type
     */
    public static final int SERVER_ERROR_DATA_TYPE = 20;
    
    /**
     * Simple Text data type
     */
    public static final int SIMPLE_TEXT_DATA_TYPE = 21;
    /**
     * Status Msg data type
     */
    public static final int STATUS_MSG_DATA_TYPE = 22;
    /**
     * User Profile data type
     */
    public static final int USER_PROFILE_DATA_TYPE = 23;
    /**
     * System Notification data type
     */    
    public static final int SYSTEM_NOTIFICATION_DATA_TYPE = 24;
    /**
     * Identity Removal Data type.
     */
    public static final int IDENTITY_DELETION_DATA_TYPE = 25;
    /**
     * Selective status update type.
     */
    public static final int SELECTIVE_STATUS_UPDATE_TYPE = 26;
}
