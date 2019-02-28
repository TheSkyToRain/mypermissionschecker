package com.ebaryice.mypermissionschecker;

import java.util.List;

public interface PermissionListener {

    void onGranted();

    void onDenied(List<String> deniedPermissions);

    void onShouldShowRationale(List<String> deniedPermissions);
}
