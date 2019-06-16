package org.tonygatins.tonysmith.api.conch;

import android.graphics.Paint;
import org.tonygatins.tonysmith.api.conch.resource.Brush;
import android.graphics.Color;

/**
 * 安卓底层实现:画笔。
 * @author Tony Chen Smith
 */
final class AndroidBrush extends Brush<Paint>
{
	//本地的画笔对象。
	private final Paint nativeBrush;
	
	/**
	 * 构造一个画笔。
	 * (给主句柄类使用)
	 */
	protected AndroidBrush()
	{
		this.nativeBrush=new Paint();
	}
	
	/**
	 * 获得本地画笔对象。
	 * @return 唯一本地画笔对象。
	 */
	@Override
	public Paint getBrush()
	{
		// TODO: Implement this method
		return nativeBrush;
	}
	
	void test(){
	}

	/**
	 * 设置笔刷。
	 * @param brush 笔刷对象。
	 */
	@Override
	public void set(Brush brush)
	{
		// TODO: Implement this method
		nativeBrush.set(((AndroidBrush)brush).nativeBrush);
	}
}
