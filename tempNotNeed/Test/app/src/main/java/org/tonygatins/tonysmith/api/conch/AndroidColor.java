package org.tonygatins.tonysmith.api.conch;

import java.util.Arrays;
import org.tonygatins.tonysmith.api.conch.resource.Color;

/**
 * 安卓底层实现：颜料。
 * @author Tony Chen Smith
 */
final class AndroidColor extends Color
{
	//初始化属性。
	private AndroidColor()
	{
		colorId=0;
		red=0;
		green=0;
		blue=0;
		alpha=0;
		hsv=new float[3];
	}
	
	//颜色值。
	private int colorId;
	
	//红色值。
	private int red;
	
	//绿色值。
	private int green;
	
	//蓝色值。
	private int blue;
	
	//透明度值。
	private int alpha;
	
	//直观颜色数组。
	private float[] hsv;
	
	/**
	 * 获得本地颜色值。(因为颜色的实现都是数字变量）
	 * @return 颜色的整型数字。
	 */
	@Override
	public int getNativeColor()
	{
		// TODO: Implement this method
		return colorId;
	}
	
	/**
	 * 获得透明度值。
	 * @return 透明度的整型数字。
	 */
	@Override
	public int getAlpha()
	{
		// TODO: Implement this method
		return alpha;
	}
	
	/**
	 * 获得红色值。
	 * @return 红色的整型数字。
	 */
	@Override
	public int getRed()
	{
		// TODO: Implement this method
		return red;
	}
	
	/**
	 * 获得绿色值。
	 * @return 绿色的整型数字。
	 */
	@Override
	public int getGreen()
	{
		// TODO: Implement this method
		return green;
	}

	/**
	 * 获得蓝色值。
	 * @return 蓝色的整型数字。
	 */
	@Override
	public int getBlue()
	{
		// TODO: Implement this method
		return blue;
	}
	
	/**
	 * 获得直观颜色(HSV)数组。
	 * @return 直观颜色数组对象。
	 */
	@Override
	public float[] getHSV()
	{
		// TODO: Implement this method
		return Arrays.copyOf(hsv,hsv.length);
	}

	/**
	 * 本地通过透明度值与直观颜色数组对象构造颜料对象。
	 * @param alpha 透明度整型值。
	 * @param hsv 直观颜色数组对象。
	 * @return 一个相对应的颜料对象。
	 */
	protected static Color nativeCreateColor(int alpha,float[] hsv)
	{
		// TODO: Implement this method
		AndroidColor result=new AndroidColor();
		result.setColor(alpha,hsv);
		return result;
	}

	/**
	 * 本地通过直观颜色数组对象构造颜料对象。
	 * @param hsv 直观颜色数组对象。
	 * @return 一个相对应的颜料对象。
	 */
	protected static Color nativeCreateColor(float[] hsv)
	{
		// TODO: Implement this method
		AndroidColor result=new AndroidColor();
		result.setColor(hsv);
		return result;
	}

	/**
	 * 本地通过颜色字符串对象构造颜料对象。
	 * @param colorText 颜色字符串对象。
	 * @return 一个相对应的颜料对象。
	 */
	protected static Color nativeCreateColor(String colorText)
	{
		// TODO: Implement this method
		AndroidColor result=new AndroidColor();
		result.setColor(colorText);
		return result;
	}

	/**
	 * 本地通过红色值，绿色值，蓝色值构造颜料对象。
	 * @param red 红色值。
	 * @param green 绿色值。
	 * @param blue 蓝色值。
	 * @return 一个相对应的颜料对象。
	 */
	protected static Color nativeCreateColor(int red,int green,int blue)
	{
		// TODO: Implement this method
		AndroidColor result=new AndroidColor();
		result.setColor(red,green,blue);
		return result;
	}

	/**
	 * 本地通过透明度值，红色值，绿色值，蓝色值构造颜料对象。
	 * @param alpha 透明度值。
	 * @param red 红色值。
	 * @param green 绿色值。
	 * @param blue 蓝色值。
	 * @return 一个相对应的颜料对象。
	 */
	protected static Color nativeCreateColor(int alpha,int red,int green,int blue)
	{
		// TODO: Implement this method
		AndroidColor result=new AndroidColor();
		result.setColor(alpha,red,green,blue);
		return result;
	}

	/**
	 * 通过透明度值与直观颜色数组对象设置颜料对象。
	 * @param alpha 透明度整型值。
	 * @param hsv 直观颜色数组对象。
	 */
	@Override
	public void setColor(int alpha,float[] hsv)
	{
		// TODO: Implement this method
		this.colorId=android.graphics.Color.HSVToColor(alpha,hsv);
		this.red=android.graphics.Color.red(this.colorId);
		this.green=android.graphics.Color.green(this.colorId);
		this.blue=android.graphics.Color.blue(this.colorId);
		this.alpha=alpha;
		this.hsv[0]=hsv[0];
		this.hsv[1]=hsv[1];
		this.hsv[2]=hsv[2];
	}

	/**
	 * 通过直观颜色数组对象设置颜料对象。
	 * @param hsv 直观颜色数组对象。
	 */
	@Override
	public void setColor(float[] hsv)
	{
		// TODO: Implement this method
		this.colorId=android.graphics.Color.HSVToColor(hsv);
		this.red=android.graphics.Color.red(this.colorId);
		this.green=android.graphics.Color.green(this.colorId);
		this.blue=android.graphics.Color.blue(this.colorId);
		this.alpha=android.graphics.Color.alpha(this.colorId);
		this.hsv[0]=hsv[0];
		this.hsv[1]=hsv[1];
		this.hsv[2]=hsv[2];
	}

	/**
	 * 通过颜色字符串对象设置颜料对象。
	 * @param colorText 颜色字符串对象。
	 */
	@Override
	public void setColor(String colorText)
	{
		// TODO: Implement this method
		this.colorId=android.graphics.Color.parseColor(colorText);
		this.red=android.graphics.Color.red(this.colorId);
		this.green=android.graphics.Color.green(this.colorId);
		this.blue=android.graphics.Color.blue(this.colorId);
		this.alpha=android.graphics.Color.alpha(this.colorId);
		android.graphics.Color.colorToHSV(this.colorId,this.hsv);
	}

	/**
	 * 通过红色值，绿色值，蓝色值设置颜料对象。
	 * @param red 红色值。
	 * @param green 绿色值。
	 * @param blue 蓝色值。
	 */
	@Override
	public void setColor(int red,int green,int blue)
	{
		// TODO: Implement this method
		this.colorId=android.graphics.Color.rgb(red,green,blue);
		this.red=red;
		this.green=green;
		this.blue=blue;
		this.alpha=android.graphics.Color.alpha(this.colorId);
		android.graphics.Color.colorToHSV(this.colorId,hsv);
	}

	/**
	 * 通过透明度值，红色值，绿色值，蓝色值设置颜料对象。
	 * @param alpha 透明度值。
	 * @param red 红色值。
	 * @param green 绿色值。
	 * @param blue 蓝色值。
	 */
	@Override
	public void setColor(int alpha,int red,int green,int blue)
	{
		// TODO: Implement this method
		this.colorId=android.graphics.Color.argb(alpha,red,green,blue);
		this.red=red;
		this.green=green;
		this.blue=blue;
		this.alpha=alpha;
		android.graphics.Color.colorToHSV(this.colorId,hsv);
	}

	/**
	 * 通过颜料对象重新设置颜料对象。
	 * @param parent 父辈颜料对象。
	 */
	@Override
	public void setColor(Color parent)
	{
		// TODO: Implement this method
		this.colorId=parent.getNativeColor();
		this.alpha=parent.getAlpha();
		this.red=parent.getRed();
		this.green=parent.getGreen();
		this.hsv=parent.getHSV();
	}

	/**
	 * 回收方法。（不实现）
	 */
	@Override
	public void recycle()
	{
		// TODO: Implement this method
	}

	/**
	 * 判断是否已回收。
	 * @return true为已回收，false为未回收。
	 */
	@Override
	public boolean isRecycled()
	{
		// TODO: Implement this method
		return false;
	}
	
	/**
	 * 通过本地颜色值构造一个颜料对象。
	 * @param nativeColor 本地颜色值。
	 * @return 一个颜料对象。
	 */
	protected static Color nativeCreateColor(int nativeColor)
	{
		AndroidColor result=new AndroidColor();
		result.colorId=nativeColor;
		result.red=android.graphics.Color.red(result.colorId);
		result.green=android.graphics.Color.green(result.colorId);
		result.blue=android.graphics.Color.blue(result.colorId);
		result.alpha=android.graphics.Color.alpha(result.colorId);
		android.graphics.Color.colorToHSV(result.colorId,result.hsv);
		return result;
	}
}
