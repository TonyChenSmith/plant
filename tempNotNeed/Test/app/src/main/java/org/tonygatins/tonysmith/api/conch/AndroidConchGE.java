package org.tonygatins.tonysmith.api.conch;

import org.tonygatins.tonysmith.api.conch.TGConchGE;
import android.graphics.Canvas;
import android.graphics.Bitmap;
import android.graphics.Paint;
import org.tonygatins.tonysmith.api.conch.resource.Brush;
import org.tonygatins.tonysmith.api.conch.resource.Paper;
import org.tonygatins.tonysmith.api.conch.resource.Color;

/**
 * 安卓底层实现:句柄。
 * @author Tony Chen Smith
 */
final class AndroidConchGE extends TGConchGE.LocalConchGE
{
	//本地画布对象。
	private final Canvas background;
	
	//本地画布基底纸张对象。
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
	 * 本地绘制画笔。
	 * @param brush 画笔对象。
	 */
	@Override
	protected void nativeDrawBrush(Brush<?> brush)
	{
		// TODO: Implement this method
		background.drawPaint((Paint)brush.getBrush());
	}
	
	/**
	 * 本地绘制颜料。
	 * @param color 颜料对象。
	 */
	@Override
	protected void nativeDrawColor(Color color)
	{
		// TODO: Implement this method
		background.drawColor(color.getNativeColor());
	}
	
	//绘制点线
	
	/**
	 * 本地绘制一个点。
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
