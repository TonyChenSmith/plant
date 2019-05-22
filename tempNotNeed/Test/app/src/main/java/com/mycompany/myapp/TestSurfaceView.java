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
	
	public TestSurfaceView(Context context)
	{
		super(context);
		getHolder().addCallback(new CallBack());
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
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder)
		{
			// TODO: Implement this method
		}
	}
}
