package com.mycompany.myapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

//测试用的界面
public class TestSurfaceView extends SurfaceView
{
	static InputStream drawable;
	static Bitmap draw;
	
	public TestSurfaceView(Context context)
	{
		super(context);
		getHolder().addCallback(new CallBack());
		try
		{
			drawable = getContext().getAssets().open("field.png");
			draw=BitmapFactory.decodeStream(drawable);
		}
		catch(IOException e)
		{}
	}
	
	private static class CallBack implements SurfaceHolder.Callback
	{

		@Override
		public void surfaceCreated(SurfaceHolder holder)
		{
			// TODO: Implement this method
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder,int format,int width,int height)
		{
			// TODO: Implement this method
			Canvas canvas = holder.lockCanvas();
			Log.i("Test Format",Integer.toHexString(format));
			Log.i("Test Width",Integer.toString(width));
			Log.i("Test Height",Integer.toString(height));
			canvas.drawColor(Color.WHITE);
			canvas.translate(300.5f,500.5f);
			canvas.drawBitmap(draw,null,new Rect(0,0,draw.getWidth(),draw.getHeight()),null);
			Log.i("Bitmap Width",Integer.toString(canvas.getWidth()));
			Log.i("Bitmap Height",Integer.toString(canvas.getHeight()));
			holder.unlockCanvasAndPost(canvas);
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder)
		{
			// TODO: Implement this method
			try
			{
				draw.recycle();
				drawable.close();
			}
			catch(IOException e)
			{}
		}
	}
}
