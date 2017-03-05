package gdin.com.penpi.commonUtils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;

import java.io.File;

import gdin.com.penpi.homeIndex.HomeActivity;

/**
 * 设置用户头像
 * Created by Administrator on 2017/3/5.
 */
public class ChoseHeadImage {

    /* 头像文件 */
    public static final String IMAGE_FILE_NAME = "temp_head_image.jpg";

    /* 请求识别码 */
    public static final int CODE_GALLERY_REQUEST = 0xa0;
    public static final int CODE_CAMERA_REQUEST = 0xa1;
    public static final int CODE_RESULT_REQUEST = 0xa2;

    /**
     * 从本地相册选取图片作为头像
     */
    public static void fromGallery(HomeActivity homeActivity) {
        Intent intentFromGallery = new Intent();
        // 设置文件类型
        intentFromGallery.setType("image/*");
        intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
        homeActivity.startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
    }

    /**
     * 启动手机相机拍摄照片作为头像
     */
    public static void fromCameraCapture(HomeActivity homeActivity) {
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 判断存储卡是否可用，存储照片文件
        if (hasSdcard()) {
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
        }

        homeActivity.startActivityForResult(intentFromCapture, CODE_CAMERA_REQUEST);
    }

    /**
     * 裁剪原始的图片
     */
    public static void cropRawPhoto(Uri uri, HomeActivity homeActivity) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        // 设置裁剪
        intent.putExtra("crop", "true");

        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // 裁剪后图片的宽(X)和高(Y),480 X 480的正方形。
        int output_X = 480;
        intent.putExtra("outputX", output_X);
        int output_Y = 480;
        intent.putExtra("outputY", output_Y);
        intent.putExtra("return-data", true);

        homeActivity.startActivityForResult(intent, CODE_RESULT_REQUEST);
    }

    /**
     * 提取保存裁剪之后的图片数据，并设置头像部分的View
     */
    private static Bitmap photo = null;
    public static void setImageToHeadView(Intent intent, ImageView personHead) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            photo = extras.getParcelable("data");
            personHead.setImageBitmap(photo);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                MyBitmap.sendImageBitmap(photo);
            }
        }).start();
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        // 有存储的SDCard
        return state.equals(Environment.MEDIA_MOUNTED);
    }
}
