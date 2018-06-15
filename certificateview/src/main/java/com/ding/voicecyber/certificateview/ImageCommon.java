package com.ding.voicecyber.certificateview;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class ImageCommon {
    /**
     * @param target
     * @param mark 水印文字
     * @param waterColor 水印颜色
     * @param watersize 水印大小
     * @return
     */
    // 为图片target添加水印文字
    // Bitmap target：被添加水印的图片
    // String mark：水印文章
    public static Bitmap createWatermark(Bitmap target, String mark,int waterColor,int watersize) {
        int w = target.getWidth();
        int h = target.getHeight();

        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);

        Paint p = new Paint();

        // 水印的颜色
        p.setColor( waterColor );

        // 水印的字体大小
        p.setTextSize(watersize);

        p.setAntiAlias(true);// 去锯齿

        canvas.drawBitmap(target, 0, 0, p);

        // 在左边的中间位置开始添加水印
        canvas.drawText(mark, 0, h / 2, p);

        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();

        return bmp;
    }

    /**
     * @param bmp   图片
     * @param file 保存路径
     * @param ratio 压缩大小
     */
    public static void compressBitmapToFile(Bitmap bmp, File file,int ratio){
        // 压缩Bitmap到对应尺寸
        Bitmap result = Bitmap.createBitmap(bmp.getWidth() / ratio, bmp.getHeight() / ratio, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        Rect rect = new Rect(0, 0, bmp.getWidth() / ratio, bmp.getHeight() / ratio);
        canvas.drawBitmap(bmp, null, rect, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 把压缩后的数据存放到baos中
        result.compress(Bitmap.CompressFormat.PNG, 100 ,baos);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param context
     * @param imagePath
     * @return 本地图片URI
     */
    public static Uri getImageContentUri(Context context, String imagePath) {
        Cursor cursor = context.getContentResolver().query( MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Images.Media._ID }, MediaStore.Images.Media.DATA + "=? ",
                new String[] { imagePath }, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex( MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            ContentValues values = new ContentValues();
            values.put( MediaStore.Images.Media.DATA, imagePath);
            return context.getContentResolver().insert( MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        }
    }

    /**  模仿微信朋友圈图片浏览
     * @param context
     * @param imagepath 图片绝对路径
     * @param view
     */
    public static void startPathDragPhotoActivity(Activity context, String imagepath, View view){
        //Intent intent = new Intent(mContext, DragPhotoActivity.class);
        Intent intent = new Intent();
        intent.setAction("android.intent.action.patientIf");
        int location[] = new int[2];
        view.getLocationOnScreen(location);
        intent.putExtra("left", location[0]);
        intent.putExtra("top", location[1]);
        intent.putExtra("height", view.getHeight());
        intent.putExtra("width", view.getWidth());
        intent.putExtra( "imagePath",imagepath);
        context.startActivity(intent);
        context.overridePendingTransition(0,0);
    }

    /**  模仿微信朋友圈图片浏览
     * @param context
     * @param imageUri 图片Uri
     * @param view
     */
    public static void startUriDragPhotoActivity(Activity context, String imageUri, View view){
        //Intent intent = new Intent(mContext, DragPhotoActivity.class);
        Intent intent = new Intent();
        intent.setAction("android.intent.action.patientIf");
        int location[] = new int[2];
        view.getLocationOnScreen(location);
        intent.putExtra("left", location[0]);
        intent.putExtra("top", location[1]);
        intent.putExtra("height", view.getHeight());
        intent.putExtra("width", view.getWidth());
        intent.putExtra( "imageUri",imageUri);
        context.startActivity(intent);
        context.overridePendingTransition(0,0);
    }
}
