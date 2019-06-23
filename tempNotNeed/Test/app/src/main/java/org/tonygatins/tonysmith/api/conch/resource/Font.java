package org.tonygatins.tonysmith.api.conch.resource;

/**
 * 引擎绘制资源：字体。
 * @author Tony Chen Smith
 */
public abstract class Font<T>
{
	/**
	 * 获得本地字体对象。
	 * @return 本地的字体对象。
	 */
	public abstract T getNativeFont();
	
	/**
	 * 判断字体对象是否回收。
	 * @return true为已回收，false为未回收。
	 */
	public abstract boolean isRecycled();
	
	/**
	 * 回收字体。
	 */
	public abstract void recycle();
}
