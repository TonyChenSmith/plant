package org.tonygatins.tonysmith.api.conch;

import org.tonygatins.tonysmith.api.conch.TGConchGE;
import android.graphics.Canvas;
import android.graphics.Bitmap;
import android.graphics.Paint;

final class AndroidConchGE extends TGConchGE.LocalConchGE
{
	private final Canvas background;
	
	private final Bitmap backgroundMap;
	
	protected AndroidConchGE(int backgroundWidth,int backgroundHeight)
	{
		backgroundMap=Bitmap.createBitmap(backgroundWidth,backgroundHeight,Bitmap.Config.ARGB_8888);
		background=new Canvas(backgroundMap);
	}
	
	protected void test()
	{
		Canvas canvas = new Canvas();
	}
	
	//单色绘制
	
	/**
	 * 绘制ARGB颜色。
	 * @param alpha 透明度值。
	 * @param red 红色值。
	 * @param green 绿色值。
	 * @param blue 蓝色值。
	 */
	@Override
	protected void nativeDrawARGB(int alpha,int red,int green,int blue)
	{
		// TODO: Implement this method
		background.drawARGB(alpha,red,green,blue);
	}

	/**
	 * 绘制RGB颜色。
	 * @param red 红色值。
	 * @param green 绿色值。
	 * @param blue 蓝色值。
	 */
	@Override
	protected void nativeDrawRGB(int red,int green,int blue)
	{
		// TODO: Implement this method
		background.drawRGB(red,green,blue);
	}
}
