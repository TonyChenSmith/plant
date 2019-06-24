package org.tonygatins.tonysmith.api.conch;

import org.tonygatins.tonysmith.api.conch.resource.Brush;
import org.tonygatins.tonysmith.api.conch.resource.Color;

/**
 * 功能类：画师(绘制行为）。
 * (拥有所有与画面有关的功能)
 * @author Tony Chen Smith
 */
public final class Painter
{
	//绘制句柄。
	private final TGConchGE.LocalConchGE handler;
	
	//构造一个画师。
	protected Painter(TGConchGE.LocalConchGE handler)
	{
		this.handler=handler;
	}
	
	//单色绘制
	
	/**
	 * 绘制RGB颜色。
	 * @param red 红色值。
	 * @param green 绿色值。
	 * @param blue 蓝色值。
	 */
	public void drawRGB(int red,int green,int blue)
	{
		handler.nativeDrawRGB(red,green,blue);
	}
	
	/**
	 * 绘制ARGB颜色。
	 * @param alpha 透明度值。
	 * @param red 红色值。
	 * @param green 绿色值。
	 * @param blue 蓝色值。
	 */
	public void drawARGB(int alpha,int red,int green,int blue)
	{
		handler.nativeDrawARGB(alpha,red,green,blue);
	}
	
	/**
	 * 绘制画笔。
	 * @param brush 画笔对象。
	 */
	public void drawBrush(Brush brush)
	{
		handler.nativeDrawBrush(brush);
	}
	
	public void drawColor(Color color)
	{
		
	}
}
