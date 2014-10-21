package com.org.icsfoodapp.pickavator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

public class CaptureImgFrameLayout extends FrameLayout {

	private Rect rect;
	private Paint paint;
	private boolean isCapture;
	public CaptureImgFrameLayout(Context context) {
		super(context);
	}
	public CaptureImgFrameLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint=new Paint();
	}
	public void setIsCapture(boolean isCapture){
		this.isCapture=isCapture;
	}
	public Bitmap captureSelectArea(){
		initRectAndPaint();
		setDrawingCacheEnabled(true);
		buildDrawingCache();
		Bitmap bitmap = getDrawingCache();
		int offset=(int) (paint.getStrokeWidth()/2)+1;
		Bitmap bitmap2 = Bitmap.createBitmap(bitmap, rect.left+offset, rect.top+offset, rect.width()-2*offset, rect.height()-2*offset);
		destroyDrawingCache();//回收Bitmap
		return bitmap2;
	}
	private void initRectAndPaint(){
		if(rect!=null&&paint!=null) return;
		int width=getWidth();
		int height=getHeight();
		if(width==0||height==0) return;
		int halfLength=Math.min(width, height)/2;
		if(rect==null) rect=new Rect();
		rect.set(width/2-halfLength, height/2-halfLength, width/2+halfLength, height/2+halfLength);

		if(paint==null) paint=new Paint();
		paint.setStrokeWidth(halfLength/40);
		paint.setColor(Color.WHITE);
		paint.setStyle(Paint.Style.STROKE);
	}
	
	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		
		if (isCapture) {
			initRectAndPaint();
			canvas.drawRect(rect, paint);
		}
	}
	
}
