## MyPermissionsChecker
---
### This lib is for solving the Android6.0 runtime permissions, usage is as follows:
##### Step 1.Add it in your root build.gradle at the end of repositories:
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
##### Step 2.Add the dependency:
```
dependencies {
  implementation 'com.github.TheSkyToRain:mypermissionschecker:1.1.0'
  }
```
##### Step 3.Call method:
```
PermissionChecker pc = new PermissionChecker(this);
pc.requestPermissions(permissions, new PermissionListener() {
            @Override
            public void onGranted() {
            // 授权完成
            }

            @Override
            public void onDenied(List<String> deniedPermissions) {
            // 授权失败
            }

            @Override
            public void onShouldShowRationale(List<String> deniedPermissions) {
            // 建议用户开启某些权限
            }
        });
```

Above is all.
