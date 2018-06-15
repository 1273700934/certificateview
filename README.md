# certificateview
提供一个证件照的控件，可以设置照片水印，水印颜色，大小，照片压缩比等等
使用说明：

将其添加到存储库末尾的根build.gradle中：

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
添加依赖关系

	dependencies {
	        implementation 'com.github.1273700934:certificateview:v1.0'
	}
	
		
  
  
  权限要求：
  	
	
	AndroidManifest.xml添加：
	<uses-permission android:name="android.permission.CAMERA" />
    	<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    	<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
  
  	
	
	在demo实例中包含一个权限控制类PermissionsControl 添加权限：
	
  	 protected String[] needPermissions = {
            //文件读写
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,

            //相机
            Manifest.permission.CAMERA
    };
  
  
  在页面中：
  
 
 	在xml页面根节点加上
	xmlns:app="http://schemas.android.com/apk/res-auto"
	 
      <com.ding.voicecyber.certificateview.CertificateView
        android:id="@+id/con_id"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:certificate_name="某某证件:"
        app:media_type = "1">
    </com.ding.voicecyber.certificateview.CertificateView>
    其中 media_type 是要与业务逻辑关联的 表示自定义证件类型
        
 后台属性设置：
 
 
     	CertificateView certificateView;
        certificateView = findViewById( R.id.con_id );
        certificateView.setActivity( MainActivity.this );
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
	
	
事物处理：
在Activity中添加：
 		
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if(requestCode == certificateView.getCAMERAMedia()){
                //保存展示图片
                certificateView.setReturnImage();
                String front = certificateView.getImage_front_name();
                String back = certificateView.getImage_back_name();
                Uri frontUri = CertificateView.getImageContentUri( MainActivity.this,front );
            }
        }
    }
	
	
	
	
效果：![image](https://github.com/1273700934/certificateview/blob/master/%E5%9B%BE%E5%83%8F/1.gif)


![image](https://github.com/1273700934/certificateview/blob/master/%E5%9B%BE%E5%83%8F/2.gif)


感谢：


		https://github.com/githubwing/DragPhotoView.git
		https://github.com/chrisbanes/PhotoView
等开源项目。

