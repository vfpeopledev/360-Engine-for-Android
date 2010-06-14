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
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;

public class VersionUtils {

    /**
     * Static Platform Version Code field.
     * Build.VERSION.SDK has existed since the first version of Android.
     * Even though its deprecated it should still be good to use
     */
    private static final int PLATFORM_VERSION_CODE = Integer.parseInt(Build.VERSION.SDK);
    
    /**
     * Mapping version value directly from Android Documentation. 
     * This is done because platforms with versions below wont have the constant defined. 
     */
    private static final int ANDROID16_VERSION = 4; 
    
    /**
     * Returns the Platform Version Code.
     * @return Platform Version Code
     */
    public static int getPlatformVersionCode() {
        return PLATFORM_VERSION_CODE;
    }
    
    /**
     * Checks if the device platform Version is in the 2.X version range.
     * @return true if running on a 2.X Platform, false if not    
     */
    public static boolean is2XPlatform() {
        // If a 3X Platform comes along we need to update this code
        return PLATFORM_VERSION_CODE > ANDROID16_VERSION;
    }
    
    /**
     * Returns the version name from the AndroidManifest.xml file.
     * 
     * @param context Android context.
     * @return Version name as an integer, or "" if name not found.
     */
    public static String getPackageVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            LogUtils.logE("VersionUtils.getPackageVersionName() "
                    + "NameNotFoundException returning empty String", e);
            return "";
        }
    }

    /**
     * Returns the version code from the AndroidManifest.xml file
     * 
     * @param context Android context.
     * @return Version code as an integer, or "" if name not found.
     */
    public static int getPackageVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            LogUtils.logE("VersionUtils.getPackageVersionCode() NameNotFoundException return -1", e);
            return -1;
        }
    }
}
