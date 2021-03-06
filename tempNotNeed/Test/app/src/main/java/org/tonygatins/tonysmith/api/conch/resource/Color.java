package org.tonygatins.tonysmith.api.conch.resource;

import org.tonygatins.tonysmith.api.conch.AndroidColor;

/**
 * 引擎绘制资源：颜料。
 * @author Tony Chen Smith
 */
public abstract class Color
{
	/**
	 * 获得本地颜色值。(因为颜色的实现都是数字变量）
	 * @return 颜色的整型数字。
	 */
	public abstract int getNativeColor();
	
	/**
	 * 获得透明度值。
	 * @return 透明度的整型数字。
	 */
	public abstract int getAlpha();
	
	/**
	 * 获得红色值。
	 * @return 红色的整型数字。
	 */
	public abstract int getRed();
	
	/**
	 * 获得绿色值。
	 * @return 绿色的整型数字。
	 */
	public abstract int getGreen();
	
	/**
	 * 获得蓝色值。
	 * @return 蓝色的整型数字。
	 */
	public abstract int getBlue();
	
	/**
	 * 获得直观颜色(HSV)数组。
	 * @return 直观颜色数组对象。
	 */
	public abstract float[] getHSV();
	
	/**
	 * 通过透明度值与直观颜色数组对象设置颜料对象。
	 * @param alpha 透明度整型值。
	 * @param hsv 直观颜色数组对象。
	 */
	public abstract void setColor(int alpha,float[] hsv);
	
	/**
	 * 通过直观颜色数组对象设置颜料对象。
	 * @param hsv 直观颜色数组对象。
	 */
	public abstract void setColor(float[] hsv);
	
	/**
	 * 通过颜色字符串对象设置颜料对象。
	 * @param colorText 颜色字符串对象。
	 */
	public abstract void setColor(String colorText);
	
	/**
	 * 通过红色值，绿色值，蓝色值设置颜料对象。
	 * @param red 红色值。
	 * @param green 绿色值。
	 * @param blue 蓝色值。
	 */
	public abstract void setColor(int red,int green,int blue);
	
	/**
	 * 通过透明度值，红色值，绿色值，蓝色值设置颜料对象。
	 * @param alpha 透明度值。
	 * @param red 红色值。
	 * @param green 绿色值。
	 * @param blue 蓝色值。
	 */
	public abstract void setColor(int alpha,int red,int green,int blue);
	
	/**
	 * 通过颜料对象重新设置颜料对象。
	 * @param parent 父辈颜料对象。
	 */
	public abstract void setColor(Color parent);
	
	/**
	 * 回收方法。（不一定会进行有效实现）
	 */
	public abstract void recycle();
	
	/**
	 * 判断是否已回收。
	 * @return true为已回收，false为未回收。
	 */
	public abstract boolean isRecycled();
}
