package com.aliya.base.sample.ui.activity.photo;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.aliya.base.sample.R;
import com.aliya.base.sample.base.ActionBarActivity;
import com.aliya.base.sample.databinding.ActivityPhotoBinding;
import com.aliya.permission.Permission;
import com.aliya.permission.PermissionCallback;
import com.aliya.permission.PermissionManager;

import java.io.File;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

/**
 * PhotoActivity
 *
 * @author a_liYa
 * @date 2020/11/26 18:49.
 */
public class PhotoActivity extends ActionBarActivity implements View.OnClickListener {

    private static final int SELECT_PHOTO = 100;

    private ActivityPhotoBinding mViewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewBinding = ActivityPhotoBinding.inflate(getLayoutInflater());
        setContentView(mViewBinding.getRoot());

        mViewBinding.tvOpenAlbum.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_open_album:
                PermissionManager.request(this, new PermissionCallback() {
                    @Override
                    public void onGranted(boolean isAlready) {
                        openAlbum();
                    }

                    @Override
                    public void onDenied(@NonNull List<String> deniedPermissions,
                                         @Nullable List<String> neverAskPermissions) {

                    }
                }, Permission.STORAGE_WRITE, Permission.STORAGE_WRITE);
                break;
        }
    }

    private void openAlbum() {
//        Intent intent =
//                new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Intent intent = new Intent();
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(intent, SELECT_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SELECT_PHOTO:
                Uri uri = data.getData();
                String imagePath = Uris.getRealPathFromUriAboveApiAndroidK(this, uri);
                Uri imageUri = fileToUri(new File(imagePath));
                Log.e("TAG", "onActivityResult: " + imageUri);
                break;
        }
    }

    public Uri fileToUri(File file) {
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(this, getPackageName() + ".fileProvider", file);
        } else {
            uri = Uri.parse(file.getAbsolutePath());
        }
        return uri;
    }

}
