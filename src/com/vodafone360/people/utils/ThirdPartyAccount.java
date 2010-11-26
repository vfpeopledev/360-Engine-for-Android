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

package com.vodafone360.people.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.vodafone360.people.R;
import com.vodafone360.people.datatypes.Identity;
import com.vodafone360.people.engine.presence.NetworkPresence.SocialNetwork;
import com.vodafone360.people.utils.LogUtils;

/**
 * Holds data about a third party account i.e. facebook, msn, google.
 */
public class ThirdPartyAccount {
    private Identity mIdentity;

    private Bitmap mBitmap;

    private boolean mIsVerified = false;

    private String mDisplayName;

    /** username */
    private String mIdentityID;

    /**
     * Create a new third party account object.
     * 
     * @param userName - the username for the account.
     * @param identity - Identity details retrieved from server.
     * @param isVerified -
     */
    public ThirdPartyAccount(String userName, Identity identity, boolean isVerified) {
        mIdentityID = userName;
        mIdentity = identity;
        mIsVerified = isVerified;
        mDisplayName = identity.mName;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ThirdPartyAccount: \n\tmUsername = ");
        sb.append(getIdentityID());
        sb.append("\n\tmDisplayName = "); sb.append(getDisplayName());
        sb.append("\n\tmIsVerified = "); sb.append(isVerified());
        return sb.toString();
    }

    /**
     * Gets the Localised string for the given SNS.
     * 
     * @param sns - text of the sns type
     * @return Localised string for the given SNS.
     */
    public static String getSnsString(Context context, String sns) {
        SocialNetwork socialNetwork = SocialNetwork.getNetworkBasedOnString(sns);
        if (socialNetwork == null) {
            return context.getString(R.string.Utils_sns_name_vodafone);
        }

        switch (socialNetwork) {
            case FACEBOOK_COM:
                return context.getString(R.string.Utils_sns_name_facebook);
            case ODNOKLASSNIKI_RU:
                return context.getString(R.string.Utils_sns_name_odnoklassniki);
            case VKONTAKTE_RU:
                return context.getString(R.string.Utils_sns_name_vkontakte);
            case GOOGLE:
                return context.getString(R.string.Utils_sns_name_google);
            case WINDOWS:
                return context.getString(R.string.Utils_sns_name_msn);
            case TWITTER:
                return context.getString(R.string.Utils_sns_name_twitter);
            case HYVES_NL:
                return context.getString(R.string.Utils_sns_name_hyves);
            case STUDIVZ:
                return context.getString(R.string.Utils_sns_name_studivz);
            default:
                LogUtils.logE("SNSIconUtils.getSNSStringResId() SNS String[" + sns + "] is not of a "
                        + "known type, so returning empty string value");
                return "";
        }
    }

    /**
     * @param mBitmap the mBitmap to set
     */
    public void setBitmap(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
    }

    /**
     * @return the mBitmap
     */
    public Bitmap getBitmap() {
        return mBitmap;
    }

    /**
     * @param mIdentity the mIdentity to set
     */
    public void setIdentity(Identity mIdentity) {
        this.mIdentity = mIdentity;
    }

    /**
     * @return the mIdentity
     */
    public Identity getIdentity() {
        return mIdentity;
    }

    /**
     * @param mIsVerified the mIsVerified to set
     */
    public void setIsVerified(boolean mIsVerified) {
        this.mIsVerified = mIsVerified;
    }

    /**
     * @return the mIsVerified
     */
    public boolean isVerified() {
        return mIsVerified;
    }

    /**
     * @param mDisplayName the mDisplayName to set
     */
    public void setDisplayName(String mDisplayName) {
        this.mDisplayName = mDisplayName;
    }

    /**
     * @return the mDisplayName
     */
    public String getDisplayName() {
        return mDisplayName;
    }

    /**
     * @param mIdentityID the mIdentityID to set
     */
    public void setIdentityID(String mIdentityID) {
        this.mIdentityID = mIdentityID;
    }

    /**
     * @return the mIdentityID
     */
    public String getIdentityID() {
        return mIdentityID;
    }
}
