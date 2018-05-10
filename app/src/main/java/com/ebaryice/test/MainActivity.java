package com.ebaryice.test;

import android.Manifest;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ebaryice.mypermissionschecker.PermissionChecker;

public class MainActivity extends AppCompatActivity {

    Button button;
    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CAMERA
    };
    private PermissionChecker permissionChecker;
    private int requestType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);

        permissionChecker = new PermissionChecker(this);

        button.setOnClickListener(getPermission);

    }

    View.OnClickListener getPermission = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            requestType = 1;
            if (permissionChecker.isLackPermissions(PERMISSIONS)){
                permissionChecker.requestPermissions();
            }else {
                openCamera();
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PermissionChecker.PERMISSION_REQUEST_CODE:
                if (permissionChecker.hasAllPermissionsGranted(grantResults)){
                    if (requestType == 1){
                        openCamera();
                    }
                }else {
                    permissionChecker.showDialog();
                }
                break;
        }
    }
    private void openCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(intent);
    }
}
