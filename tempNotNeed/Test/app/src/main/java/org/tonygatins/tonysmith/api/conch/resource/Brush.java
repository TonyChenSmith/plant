package org.tonygatins.tonysmith.api.conch.resource;

/**
 * 引擎绘制资源：笔刷（改变意图样式）
 * @author Tony Chen Smith
 */
public abstract class Brush<T>
{
	/**
	 * 设置笔刷。
	 * @param brush 笔刷对象。
	 */
	public abstract void set(Brush brush);
	
	/**
	 * 获得笔刷。
	 * @return 本地资源对象。
	 */
	public abstract T getBrush();
	
	//慢慢添加
}
