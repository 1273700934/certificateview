package com.example.voicecyber.sample;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ding.voicecyber.certificateview.CertificateView;
import com.ding.voicecyber.certificateview.ImageCommon;

public class MainActivity extends AppCompatActivity {

    CertificateView certificateView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        PermissionsControl permissionsControl = new PermissionsControl( MainActivity.this );
        permissionsControl.startWork();
        certificateView = findViewById( R.id.con_id );
        certificateView.setActivity( MainActivity.this );
        //设置保存目录 根目录下建文件夹
        certificateView.setRootPath( "sample" );
        //设置照片文件名
        certificateView.setMediaName( "6663" );
        //水印
        certificateView.setWaterText( "天天向上，好好学习" );
       // 水印大小
        certificateView.setWaterSize( 300 );
        //水印颜色
        certificateView.setWaterColor( Color.GREEN );
        //照片压缩
        certificateView.setWaterRatio( 8 );

    }



    //获得图片 压缩 加水印
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if(requestCode == certificateView.getCAMERAMedia()){
                //保存展示图片
                certificateView.setReturnImage();
                String front = certificateView.getImage_front_name();
                String back = certificateView.getImage_back_name();
                Uri frontUri = ImageCommon.getImageContentUri( MainActivity.this,front );
            }
        }
    }

}
