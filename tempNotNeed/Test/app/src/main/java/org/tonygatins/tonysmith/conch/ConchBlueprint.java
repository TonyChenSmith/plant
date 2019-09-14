package org.tonygatins.tonysmith.conch;

/**
 * 绘制用的蓝图，用蓝图制作出一个引擎。
 * @author Tony Chen Smith
 */
public interface ConchBlueprint
{
	/**
	 * 初始化绘制环境。
	 * @param env 绘制环境对象。
	 */
	public abstract void initDrawingEnvironment(ConchEnvironment env);
	
	/**
	 * 绘制图像。
	 * @param env 绘制环境对象。
	 */
	public abstract void draw(ConchEnvironment env);
	
	/**
	 * 短反应（点击）。
	 * @param x X坐标。
	 * @param y Y坐标。
	 * @param env 绘制环境对象。
	 */
	public void shortAct(float x,float y,ConchEnvironment env);
	
	/**
	 * 长反应（移动）。
	 * @param x0 初始X坐标。
	 * @param y0 初始Y坐标。
	 * @param x1 最末X坐标。
	 * @param y1 最末Y坐标。
	 * @param env 绘制环境对象。
	 */
	public void longAct(float x0,float y0,float x1,float y1,ConchEnvironment env);
	
	/**
	 * 回收进程中调用。
	 */
	public abstract void onRecycle();
}
