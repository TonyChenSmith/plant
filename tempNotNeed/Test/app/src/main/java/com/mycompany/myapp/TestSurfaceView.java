package com.mycompany.myapp;

import android.content.Context;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.graphics.Rect;
import android.graphics.Canvas;
import android.view.SurfaceHolder.Callback;
import android.view.Surface;

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
