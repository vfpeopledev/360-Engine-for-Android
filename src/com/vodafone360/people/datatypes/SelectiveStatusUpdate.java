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

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 
 * Data type encapsulating the data needed for updating the status on a social network in a 
 * selectable way.
 *
 */
public class SelectiveStatusUpdate extends BaseDataType implements Parcelable {
	private String mStatusText;
	private List<String> mNetworks;

	/**
	 * Empty default constructor.
	 */
	public SelectiveStatusUpdate() {}
	
	/**
	 * 
	 * Constructs this object with a given status text and a list of networks to post the status to.
	 * 
	 * @param statusText The status text to post for the given social networks.
	 * @param networks The networks to post the status text for.
	 * 
	 */
	public SelectiveStatusUpdate(final String statusText, final List<String> networks) {
		mStatusText = statusText;
		mNetworks = networks;
	}
	
	/**
	 * 
	 * Gets the status text.
	 * 
	 * @return The status text or null if it was not set before.
	 */
	public String getStatusText() {
		return mStatusText;
	}

	/**
	 * 
	 * Sets the status text.
	 * 
	 * @param statusText The status text to set.
	 */
	public void setStatusText(String statusText) {
		this.mStatusText = statusText;
	}

	/**
	 * 
	 * Gets the networks the status should be set for.
	 * 
	 * @return Gets the networks or an empty list (only update 360 status) if the status was null.
	 */
	public List<String> getNetworks() {
		if (mNetworks == null) {
			return new ArrayList<String>(0);
		}
		
		return mNetworks;
	}

	/**
	 * 
	 * Sets the networks whose status should be updated
	 * 
	 * @param networks
	 */
	public void setNetworks(List<String> networks) {
		this.mNetworks = networks;
	}
	
	
	@Override
	public int getType() {
		return SELECTIVE_STATUS_UPDATE_TYPE;
	}

	@Override
	public int describeContents() {
		return 1;
	}

    /**
     * Read RegistrationDetails item from supplied Parcel.
     * 
     * @param in Parcel containing RegistrationDetails.
     */
	private void readFromParcel(Parcel in) {
		mStatusText = in.readString();
		in.readStringList(mNetworks);
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		if (mStatusText != null) {
			dest.writeString(mStatusText);
		}
		
		if (mNetworks != null) {
			dest.writeStringList(mNetworks);
		}
	}
	
    /***
     * Parcelable creator for RegistrationDetails.
     */
    public static Parcelable.Creator<SelectiveStatusUpdate> CREATOR
        = new Parcelable.Creator<SelectiveStatusUpdate>() {

        @Override
        public SelectiveStatusUpdate createFromParcel(final Parcel source) {
        	SelectiveStatusUpdate result =  new SelectiveStatusUpdate();
            result.readFromParcel(source);
            return result;
        }

        @Override
        public SelectiveStatusUpdate[] newArray(final int size) {
            return new SelectiveStatusUpdate[size];
        }
    };
}
