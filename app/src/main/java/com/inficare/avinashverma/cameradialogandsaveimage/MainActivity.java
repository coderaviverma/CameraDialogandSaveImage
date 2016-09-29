package com.inficare.avinashverma.cameradialogandsaveimage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private ImageView imgUser;
    private File sourceFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgUser = (ImageView) findViewById(R.id.iv_image);
        Utility.selectImage(this);

//        Utility.showToast(this, Utility.getMacAddr());
    }

/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case Constants.REQUEST_CAMERA:
                if (resultCode == Activity.RESULT_OK && data != null) {

                    Utility.showToast(this, Utility.mImageUri.toString());
                    Utility.showToast(this, "Camera success");
                }
                break;
            case Constants.SELECT_FILE:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    Utility.showToast(this, "Gallery success");
//                    Utility.showToast(this, Utility.mImageUri.toString());

                }
                break;
        }

    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.REQUEST_CAMERA) {
                // if (data != null) {
                Bitmap bitmap = null;
                Uri imgUri = Utility.mImageUri;


                try {

                    Matrix matrix = new Matrix();
                    String path = Utility.getRealPathFromURI(this, imgUri);
                    bitmap = Utility.getBitmap(path, 500, 500);
                    matrix.postRotate(Utility.getImageOrientation(path + ""));
                    Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                            bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                    imgUser.setImageBitmap(rotatedBitmap);

                    Uri temp = Utility.getImageUri(this, rotatedBitmap);
                    String temppath = Utility.getRealPathFromURI(this, temp);
                    sourceFile = new File(temppath);


                } catch (OutOfMemoryError e) {

                    e.printStackTrace();
                }
            }

            if (requestCode == Constants.SELECT_FILE) {
                if (data != null) {
                    Uri imageUri = data.getData();
                    String path = Utility.getRealPathFromURI(this, imageUri);
                    try {
                        Matrix matrix = new Matrix();
                        Bitmap bitmap = Utility.getBitmap(path, 500, 500);
                        matrix.postRotate(Utility.getImageOrientation(path + ""));
                        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                                bitmap.getWidth(), bitmap.getHeight(), matrix, true);

                        if (rotatedBitmap != null) {
                            imgUser.setImageBitmap(rotatedBitmap);
                            sourceFile = new File(path);
                        }

                    } catch (OutOfMemoryError e) {

                        e.printStackTrace();
                    }
                }
            }

        }
    }

}
