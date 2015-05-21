package com.example.cameraandphotodemo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity implements OnClickListener {
	private static final int REQUEST_CODE_CARMERA = 0X0001;
	private static final int REQUEST_CODE_PHOTO = 0X0002;
	private ImageView mImgPhoto;
	private Button mBtnCamera, mBtnPhoto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mImgPhoto = (ImageView) findViewById(R.id.img_photo);
		mBtnCamera = (Button) findViewById(R.id.btn_camera);
		mBtnPhoto = (Button) findViewById(R.id.btn_photo);
		mBtnCamera.setOnClickListener(this);
		mBtnPhoto.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_camera:
			Intent cameraIntent = new Intent();
			cameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(cameraIntent, REQUEST_CODE_CARMERA);
			break;
		case R.id.btn_photo:
			Intent photoIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(photoIntent, REQUEST_CODE_PHOTO);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (REQUEST_CODE_CARMERA == requestCode && resultCode == Activity.RESULT_OK && data != null) {
			Bitmap bitmap = (Bitmap) data.getExtras().get("data");
			mImgPhoto.setImageBitmap(bitmap);
		}
		if (requestCode == REQUEST_CODE_PHOTO && resultCode == RESULT_OK && data != null) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };
			Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
			cursor.moveToFirst();
			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();
			mImgPhoto.setImageBitmap(BitmapFactory.decodeFile(picturePath));
		}
	}
}
