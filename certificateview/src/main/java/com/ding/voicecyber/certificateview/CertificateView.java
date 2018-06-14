package com.ding.voicecyber.certificateview;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.ding.voicecyber.certificateview.ShowPhoto.DragPhotoActivity;

import java.io.File;


/**
 * Created by 12737 on 14/05/2018.
 */


//调用须知
//media_type是必须的 没有默认为1
//  certificate.setActivity( Activity );
//  certificate.setMediaInfo( String );
public class CertificateView extends LinearLayout {

    private TextView certificate_name;
    private TextView certificate_front;
    private Button certificate_front_camera;
    private ImageView certificate_front_image;
    private TextView certificate_back;
    private Button certificate_back_camera;
    private ImageView certificate_back_image;

    private Activity mContext;
    private String certificate_name_val;
    private static final int REQUEST_CAMERA = 605;
    private String MediaName;
    private static final String FRONT = "1.PNG";
    private static final String BACK = "2.PNG";
    private static final String FRONT_tmp = "1.tmp";
    private static final String BACK_tmp = "2.tmp";
    private String image_front_name = "";
    private String image_back_name = "";
    private String image_front_name_tmp;
    private String image_back_name_tmp;
    private int Media = 0 ;
    private static  String RootPath = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/audiorecord/wav/";
    private String WaterText = "水印";
    private int WaterSize = 600;
    private int WaterColor = Color.GREEN;
    private int WaterRatio = 8;

    public CertificateView(Context context, AttributeSet attrs) {
        super( context,attrs );
        LayoutInflater.from(context).inflate( R.layout.imageinfo_control, this, true);
        setView();
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CertificateView);
        certificate_name_val = attributes.getString(R.styleable.CertificateView_certificate_name);
        Media = attributes.getInteger(R.styleable.CertificateView_media_type,1);
        certificate_name.setText( certificate_name_val );
        buttonClick();
    }
    public void setActivity(Activity activity){
        mContext = activity;
    }
    public void setMediaName(String mediaName){
        MediaName = mediaName;
        //======》证件文件名 = 媒体文件名+证件类型编号+正(反)面编号
        image_front_name_tmp = MediaName + Media + FRONT_tmp;
        image_back_name_tmp = MediaName + Media + BACK_tmp;
        image_front_name = MediaName + Media + FRONT;
        image_back_name = MediaName + Media + BACK;
        setMedia();
    }
    public void setWaterText(String waterText){
        WaterText = waterText;
    }
    public void setWaterSize(int waterSize){
        WaterSize = waterSize;
    }
    public void setWaterColor(int waterColor){
        WaterColor = waterColor;
    }
    public void setWaterRatio(int waterRatio){
        WaterRatio = waterRatio;
    }
    public int getCAMERAMedia(){
        return REQUEST_CAMERA+Media;
    }
    public String getImage_front_name(){
        return image_front_name;
    }
    public String getImage_front_name_tmp(){
        return image_front_name_tmp;
    }
    public String getImage_back_name(){
        return image_back_name;
    } public String getImage_back_name_tmp(){
        return image_back_name_tmp;
    }

    private static boolean isMediaExists(String midiaImage){
        String imageFullPath = getMediaFullName(midiaImage);
        File file = new File( imageFullPath );
        return file.exists();
    }
    private static boolean isMediaDelete(String midiaImage){
        String imageFullPath = getMediaFullName(midiaImage);
        File file = new File( imageFullPath );
        return file.delete();
    }
    private void setMedia(){
        if(!TextUtils.isEmpty( image_front_name ))
        {
            if(isMediaExists( image_front_name )){
                certificate_front.setText( image_front_name );
                setImage(mContext,certificate_front_image,getImageContentUri( mContext,getMediaFullName(image_front_name) ));
            }
        }
        if(!TextUtils.isEmpty( image_back_name ))
        {
            if(isMediaExists( image_back_name )){
                certificate_back.setText( image_back_name );
                setImage(mContext,certificate_back_image,getImageContentUri( mContext,getMediaFullName(image_back_name) ));
            }
        }
    }


    /** 给Image设置本地图片
     * @param context
     * @param imageView
     * @param imageUri 本地文件URI
     */
    public static void setImage(Context context, ImageView imageView, Uri imageUri){
        try {
            ContentResolver crr = context.getContentResolver();
            Bitmap bitmap_f = BitmapFactory.decodeStream(crr.openInputStream(imageUri));
            imageView.setImageBitmap( bitmap_f );
        } catch (Exception e) {
           Log.e( "setImage",e.getMessage() );
        }
    }

    private void buttonClick(){
        certificate_front_camera.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri image_front = takePhoto( mContext,image_front_name_tmp );
                //setImage( mContext,certificate_front_image,image_front );
            }
        } );
        certificate_back_camera.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri image_back = takePhoto( mContext,image_back_name_tmp );
                //setImage( mContext,certificate_front_image,image_back );
            }
        } );
        certificate_front_image.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isMediaExists( image_front_name) ){
                    blowImage( image_front_name,view );
                }
            }
        } );
        certificate_back_image.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isMediaExists( image_back_name) ) {
                    blowImage( image_back_name, view );
                }
            }
        } );
    }

    private void blowImage(String imageName, View view){
//        Intent intent = new Intent(mContext, DragPhotoActivity.class);
//        int location[] = new int[2];
//        view.getLocationOnScreen(location);
//        intent.putExtra("left", location[0]);
//        intent.putExtra("top", location[1]);
//        intent.putExtra("height", view.getHeight());
//        intent.putExtra("width", view.getWidth());
//        intent.putExtra( "image",imageName);
//        mContext.startActivity(intent);
//        mContext.overridePendingTransition(0,0);
    }
    /** 拍照
     * @param context
     * @param fileName 文件名
     */
    private Uri takePhoto(Activity context, String fileName){
        Intent intent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE);// 启动系统相机
        intent.putExtra( MediaStore.EXTRA_VIDEO_QUALITY, 0);
        String imagePath = getMediaFullName( fileName );
        Log.i( "photo" ,imagePath);
        Uri imageUri = getImageContentUri(context,  imagePath );
        intent.putExtra( MediaStore.EXTRA_OUTPUT, imageUri);// 更改系统默认存储路径
        context.startActivityForResult(intent,REQUEST_CAMERA+Media);
        return imageUri;
    }

    public static String getMediaFullName(String fileName) {
        return RootPath +fileName;
    }

    //设置返回图片到界面
    public void setReturnImage(){
        if(CertificateView.isMediaExists( image_front_name_tmp )){
            imageFileCommon(image_front_name_tmp,image_front_name);
        }
        if(CertificateView.isMediaExists( image_back_name_tmp )){
            imageFileCommon(image_back_name_tmp,image_back_name);
        }
        setMedia();
    }

    private void imageFileCommon(String imageTmp,String ImageTarget){
        String tmpPath = CertificateView.getMediaFullName(imageTmp);
        String imagePath = CertificateView.getMediaFullName(ImageTarget);
        try{
            ContentResolver cr = mContext.getContentResolver();
            File out = new File (imagePath);
            Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(CertificateView.getImageContentUri( mContext ,tmpPath)));
            bitmap = ImageCommon.createWatermark( bitmap,WaterText,WaterColor,WaterSize );
            ImageCommon.compressBitmapToFile(bitmap,out,WaterRatio);
            CertificateView.isMediaDelete( imageTmp );
        }catch (Exception ex){

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



    private void setView(){
        certificate_name = (TextView)findViewById( R.id.certificate_name );
        certificate_front = (TextView)findViewById( R.id.certificate_front );
        certificate_front_camera = (Button) findViewById( R.id.certificate_front_camera );
        certificate_front_image = (ImageView) findViewById( R.id.certificate_front_image );
        certificate_back = (TextView)findViewById( R.id.certificate_back );
        certificate_back_camera = (Button) findViewById( R.id.certificate_back_camera );
        certificate_back_image = (ImageView) findViewById( R.id.certificate_back_image );
    }

}
