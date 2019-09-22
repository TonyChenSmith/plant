package org.tonygatins.tonysmith.conch.location;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.Canvas;

/**
 * 刷新的界面组件。
 * @author Tony Chen Smith
 */
class ConchSurface extends SurfaceView implements SurfaceHolder.Callback
{

	//创建
	@Override
	public void surfaceCreated(SurfaceHolder holder)
	{
		// TODO: Implement this method
		Canvas canvas=holder.lockCanvas();
		canvas.drawRGB(22,55,22);
		holder.unlockCanvasAndPost(canvas);
	}

	//事件改变
	@Override
	public void surfaceChanged(SurfaceHolder holder,int p2,int p3,int p4)
	{
		// TODO: Implement this method
	}

	//销毁
	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		// TODO: Implement this method
	}
	
	//初始化绘制界面
	protected ConchSurface(Context context)
	{
		super(context);
		getHolder().addCallback(this);
	}
}
