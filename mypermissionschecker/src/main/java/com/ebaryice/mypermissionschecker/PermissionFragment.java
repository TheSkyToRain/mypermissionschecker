package com.ebaryice.mypermissionschecker;

import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;


public class PermissionFragment extends Fragment {

    private static final String TAG = "PermissionFragment";

    private static final int PERMISSION_REQUEST_CODE = 443;

    private PermissionListener listener;

    public void setListener(PermissionListener permissionListener){
        this.listener = permissionListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    /**
     * 找出没有被授权的权限,发起申请权限
     * @param permissions
     */
    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissions(String... permissions){
        List<String> requestPermissions = new ArrayList<>();

        for (String permission : permissions){
            if (ContextCompat.checkSelfPermission(getContext(),permission) != PackageManager.PERMISSION_GRANTED){
                requestPermissions.add(permission);
            }
        }

        if (requestPermissions.isEmpty()){
            // 已经全部授权
            allPermissionsHasGranted();
        }else {
            // 申请权限
            requestPermissions(requestPermissions.toArray(new String[requestPermissions.size()]),PERMISSION_REQUEST_CODE);
        }
    }

    /**
     * 处理授权结果
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != PERMISSION_REQUEST_CODE){
            return;
        }

        if (grantResults.length > 0){

            // 找出被拒绝的权限
            List<String> deniedPermissionsList = new ArrayList<>();
            for (int i = 0; i < grantResults.length; i++){
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED){
                    deniedPermissionsList.add(permissions[i]);
                }
            }

            if (deniedPermissionsList.isEmpty()){
                allPermissionsHasGranted();
            }else {
                // 如果勾选了不再询问的标签,返回false
                for (String deniedPermission : deniedPermissionsList){
                    boolean flag = shouldShowRequestPermissionRationale(deniedPermission);
                    if (!flag){
                        // 建议用户去设置中授权
                        permissionShouldShowRationale(deniedPermissionsList);
                        return;
                    }
                }
                // 拒绝授权
                permissionHasDenied(deniedPermissionsList);
            }
        }
    }

    private void allPermissionsHasGranted() {
        if (listener != null){
            listener.onGranted();
        }
    }

    private void permissionHasDenied(List<String> deniedList){
        if (listener != null){
            listener.onDenied(deniedList);
        }
    }

    private void permissionShouldShowRationale(List<String> deniedList){
        if (listener != null){
            listener.onShouldShowRationale(deniedList);
        }
    }
}
