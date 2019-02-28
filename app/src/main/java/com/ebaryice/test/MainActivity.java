package com.ebaryice.test;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ebaryice.mypermissionschecker.PermissionChecker;
import com.ebaryice.mypermissionschecker.PermissionListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button button;
    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET
    };
    PermissionChecker checker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checker = new PermissionChecker(this);

        button = findViewById(R.id.button);
        button.setOnClickListener(getPermission);
    }

    View.OnClickListener getPermission = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (checker.checkIsOverMarshmallow()){
                checker.requestPermissions(PERMISSIONS, new PermissionListener() {
                    @Override
                    public void onGranted() {
                        Toast.makeText(MainActivity.this,"授权成功",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDenied(List<String> deniedPermissions) {
                        Toast.makeText(MainActivity.this,"用户拒绝"+deniedPermissions.toString()+"授权",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onShouldShowRationale(List<String> deniedPermissions) {
                        Toast.makeText(MainActivity.this,"建议在设置中开启"+deniedPermissions.toString()+"权限，否则软件无法正常使用",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    };

}
