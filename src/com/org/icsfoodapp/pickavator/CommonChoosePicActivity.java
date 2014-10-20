package com.org.icsfoodapp.pickavator;

import java.io.File;

import com.fax.utils.task.ResultAsyncTask;
import com.fax.utils.view.TopBarContain;
import com.fax.utils.view.TouchImageView;
import com.org.icsfoodapp.R;
import com.org.icsfoodapp.R.array;
import com.org.icsfoodapp.R.drawable;
import com.org.icsfoodapp.R.id;
import com.org.icsfoodapp.R.layout;
import com.org.icsfoodapp.R.string;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class CommonChoosePicActivity extends Activity {
	static final int CHOOSE_IMAGE_FROM_SDCARD=1;
	static final int CHOOSE_IMAGE_FROM_CAMERA=2;
	private static final String Extar_IsCapture="isCapture";
	private File tempCameraFile;
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		listener=null;
	}

	private static ChoosePicListener listener;
	public static void start(Context context,ChoosePicListener choosePicListener){
		start(context, false, choosePicListener);
	}
	public static void start(Context context,boolean isCapture,ChoosePicListener choosePicListener){
		listener=choosePicListener;
		context.startActivity(new Intent(context, CommonChoosePicActivity.class)
			.putExtra(Extar_IsCapture, isCapture));
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(listener==null){
			finish();
			return;
		}
		isCapture=getIntent().getBooleanExtra(Extar_IsCapture, false);
		new AlertDialog.Builder(this).setTitle(R.string.please_choose)
			.setItems(R.array.get_pic_mode, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						switch (which) {
						case 0:
							tempCameraFile=new File(getExternalCacheDir(), "tempCameraFile.tmp");
							Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE)
								.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempCameraFile));
							startActivityForResult(intent2, CHOOSE_IMAGE_FROM_CAMERA);
							break;
						case 1:
							Intent intent = new Intent();
							/* 开启Pictures画面Type设定为image */
							intent.setType("image/*");
							/* 使用Intent.ACTION_GET_CONTENT这个Action */
							intent.setAction(Intent.ACTION_GET_CONTENT);
							/* 取得相片后返回本画面 */
							startActivityForResult(intent, CHOOSE_IMAGE_FROM_SDCARD);
							dialog.dismiss();
							break;
						}
				}
			}).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			})
			.setOnCancelListener(new DialogInterface.OnCancelListener() {
				public void onCancel(DialogInterface dialog) {
					finish();
				}
			})
			.create().show();
	}
	Bitmap photo = null;
	private boolean isCapture;
    @Override  
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {  
        super.onActivityResult(requestCode, resultCode, data);  
        if (resultCode == RESULT_OK) {
        	try {
        		final CaptureImgFrameLayout cf =new CaptureImgFrameLayout(CommonChoosePicActivity.this);
        		cf.setBackgroundResource(R.color.green);
        		TouchImageView imageView = new TouchImageView(CommonChoosePicActivity.this);
        		cf.addView(imageView, -1, -1);
        		
        		setContentView(
        				new TopBarContain(this).setTitle(R.string.preview)
        				.setLeftFinish(null, R.drawable.topbar_ic_back)
        				.setRightBtn(getString(android.R.string.ok), new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								new ResultAsyncTask<Bitmap>(CommonChoosePicActivity.this) {
									@Override
									protected void onPostExecuteSuc(Bitmap result) {
										if(listener!=null) listener.onChoosed(CommonChoosePicActivity.this, result);
									}
									@Override
									protected Bitmap doInBackground(Void... params) {
										if(isCapture) return scaleBitmap(cf.captureSelectArea());
										else return scaleBitmap(photo);
									}
								}.setProgressDialog().execute();
							}
						})
        				.setContentView(cf));
        		
				cf.setIsCapture(isCapture);
				imageView.setCanMoveOverBoder(isCapture);
				
				switch (requestCode) {
				case CHOOSE_IMAGE_FROM_SDCARD:
					Uri uri = data.getData();
					ContentResolver cr = this.getContentResolver();
					BitmapFactory.Options options = new BitmapFactory.Options();
					options.inJustDecodeBounds = true;
					photo = BitmapFactory.decodeStream(cr.openInputStream(uri), null, options);
					int scale = Math.max(options.outWidth / 768, options.outHeight / 1024);
					scale++;
					if (scale < 1) scale = 1;
					options.inJustDecodeBounds = false;
					options.inSampleSize = scale;
					photo = BitmapFactory.decodeStream(cr.openInputStream(uri), null, options);
					photo = scaleBitmap(photo);

					// 将Bitmap设定到ImageView 
					imageView.setImageBitmap(photo);
					break;
				case CHOOSE_IMAGE_FROM_CAMERA:
					photo = BitmapFactory.decodeFile(tempCameraFile.getPath());
					photo = scaleBitmap(photo);
					// 将Bitmap设定到ImageView 
					imageView.setImageBitmap(photo);
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				finish();
			}
        }else{
        	finish();
        }
    }
	//将图片控制在1024x768之内
	private Bitmap scaleBitmap(Bitmap in){
		if(in.getWidth()<=768&&in.getHeight()<=1024) return in;
		float scale=Math.min(768f/in.getWidth(),1024f/in.getHeight());
		Bitmap re=Bitmap.createScaledBitmap(in, (int)(in.getWidth()*scale), (int) (in.getHeight()*scale), true);
		in.recycle();
		return re;
	}
	public interface ChoosePicListener{
		/**
		 * @param activity 还未关闭的activity
		 * @param bitmap
		 */
		public void onChoosed(Activity activity,Bitmap bitmap);
	}
}
