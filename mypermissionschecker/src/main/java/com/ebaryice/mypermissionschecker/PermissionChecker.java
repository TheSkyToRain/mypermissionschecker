package com.ebaryice.mypermissionschecker;

import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class PermissionChecker {
    private static final String TAG = "PermissionChecker";

    private PermissionFragment fragment;

    public PermissionChecker(FragmentActivity activity){
        fragment = getPermissionFragment(activity);
    }

    private PermissionFragment getPermissionFragment(FragmentActivity activity) {
        PermissionFragment fragment = (PermissionFragment) activity.getSupportFragmentManager().findFragmentByTag(TAG);

        boolean singleInstance = fragment == null;

        if (singleInstance){
            fragment = new PermissionFragment();
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            fragmentManager.
                    beginTransaction()
                    .add(fragment,TAG)
                    .commit();
            fragmentManager.executePendingTransactions();
        }

        return fragment;
    }

    /**
     * 发起权限申请
     * @param permissions
     * @param listener
     */
    public void requestPermissions(String[] permissions,PermissionListener listener){
        fragment.setListener(listener);
        fragment.requestPermissions(permissions);
    }

    /**
     * 判断当前Android版本是否高于6.0
     * @return
     */
    public boolean checkIsOverMarshmallow(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            return true;
        }else {
            return false;
        }
    }
}
