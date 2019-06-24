package org.tonygatins.tonysmith.api.conch.resource;

/**
 * 引擎绘制资源：纸（绘制颜色的载体）。
 * @author Tony Chen Smith
 */
public abstract class Paper<T>
{
	/**
	 * 设置纸。
	 * @param width 纸的宽度。
	 * @param height 纸的高度。
	 * @return true为成功设置，false为设置失败。
	 */
	public abstract boolean setPaper(int width,int height);
	
	/**
	 * 设置纸。该方法性质为设置大小深拷贝。
	 * @param parent 父辈纸资源对象。
	 * @return true为成功设置，false为设置失败。
	 */
	public abstract boolean setPaper(Paper parent);
	
	/**
	 * 获得纸（本地资源对象）。
	 * @return 本地的资源对象。
	 */
	public abstract T getPaper();
	
	/**
	 * 获得纸张宽度。
	 * @return 纸张的宽度。
	 */
	public abstract int getPaperWidth();
	
	/**
	 * 获得纸张的长度（高度）。
	 * @return 纸张的长度(高度）。
	 */
	public abstract int getPaperHeight();
	
	/**
	 * 回收方法。（不一定会进行有效实现）
	 */
	public abstract boolean isRecycled();
	
	/**
	 * 判断是否已回收。
	 * @return true为已回收，false为未回收。
	 */
	public abstract void recycle();
}
