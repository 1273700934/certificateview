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
第2步。添加依赖关系

	dependencies {
	        implementation 'com.github.1273700934:certificateview:v1.0'
	}
  
  在view中：
      <com.ding.voicecyber.certificateview.CertificateView
        android:id="@+id/con_id"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_margin="60dp"
        app:certificate_name="身份证（请横向对准证件,点击图片刷新）:"
        app:media_type = "1">
    </com.ding.voicecyber.certificateview.CertificateView>
    
    后台：
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
