package com.ebaryice.mypermissionschecker;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * Created by EbaryIce on 2018/5/9/009.
 */

public class Checker {
    private final Context mContext;

    public Checker(Context context) {
        mContext = context.getApplicationContext();
    }

    // Check permissions
    public boolean lacksPermissions(String... permissions) {
        for (String permission : permissions) {
            if (lacksPermission(permission)) {
                return true;
            }
        }
        return false;
    }

    // Check lack Permissions
    private boolean lacksPermission(String permission) {
        return ContextCompat.checkSelfPermission(mContext, permission) == PackageManager.PERMISSION_DENIED;
    }
}
