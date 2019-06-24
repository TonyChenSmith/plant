package org.tonygatins.tonysmith.api.conch;

import org.tonygatins.tonysmith.api.conch.TGConchGE;
import android.graphics.Canvas;
import android.graphics.Bitmap;
import android.graphics.Paint;
import org.tonygatins.tonysmith.api.conch.resource.Brush;
import org.tonygatins.tonysmith.api.conch.resource.Paper;

/**
 * 安卓底层实现:句柄。
 * @author Tony Chen Smith
 */
final class AndroidConchGE extends TGConchGE.LocalConchGE
{
	private final Canvas background;
	
	private final Bitmap backgroundMap;
	
	protected AndroidConchGE(Paper paper)
	{
		backgroundMap=(Bitmap)paper.getNativePaper();
		background=new Canvas(backgroundMap);
	}
	
	protected void test()
	{
		Canvas canvas = new Canvas();
	}
	
	//单色绘制
	
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
	
	/**
	 * 本地绘制ARGB颜色。
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
	 * 绘制画笔。
	 * @param brush 画笔对象。
	 */
	@Override
	protected void nativeDrawBrush(Brush<?> brush)
	{
		// TODO: Implement this method
		background.drawPaint((Paint)brush.getBrush());
	}
	
	//绘制点线
	
	/**
	 * 绘制一个点。
	 * @param x 横坐标。
	 * @param y 纵坐标。
	 * @param brush 画笔对象。
	 */
	@Override
	protected void nativeDrawPoint(float x,float y,Brush<?> brush)
	{
		// TODO: Implement this method
		background.drawPoint(x,y,(Paint)brush.getBrush());
	}
}
