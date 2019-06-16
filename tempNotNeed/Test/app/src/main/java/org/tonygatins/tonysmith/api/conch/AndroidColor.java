package org.tonygatins.tonysmith.api.conch;

import org.tonygatins.tonysmith.api.conch.resource.Color;

/**
 * 安卓底层实现：颜料。
 * @author Tony Chen Smith
 */
final class AndroidColor extends Color
{
	
	
	@Override
	public int getNativeColor()
	{
		// TODO: Implement this method
		return 0;
	}

	@Override
	public int getAlpha()
	{
		// TODO: Implement this method
		return 0;
	}

	@Override
	public int getRed()
	{
		// TODO: Implement this method
		return 0;
	}

	@Override
	public int getGreen()
	{
		// TODO: Implement this method
		return 0;
	}

	@Override
	public int getBlue()
	{
		// TODO: Implement this method
		return 0;
	}

	@Override
	public float[] getHSV()
	{
		// TODO: Implement this method
		return null;
	}

	@Override
	protected Color nativeCreateColor(int alpha,float[] hsv)
	{
		// TODO: Implement this method
		return null;
	}

	@Override
	protected Color nativeCreateColor(float[] hsv)
	{
		// TODO: Implement this method
		return null;
	}

	@Override
	protected Color nativeCreateColor(String colorText)
	{
		// TODO: Implement this method
		return null;
	}

	@Override
	protected Color nativeCreateColor(int red,int green,int blue)
	{
		// TODO: Implement this method
		return null;
	}

	@Override
	protected Color nativeCreateColor(int alpha,int red,int green,int blue)
	{
		// TODO: Implement this method
		return null;
	}

	@Override
	public void setColor(int alpha,float[] hsv)
	{
		// TODO: Implement this method
	}

	@Override
	public void setColor(float[] hsv)
	{
		// TODO: Implement this method
	}

	@Override
	public void setColor(String colorText)
	{
		// TODO: Implement this method
	}

	@Override
	public void setColor(int red,int green,int blue)
	{
		// TODO: Implement this method
	}

	@Override
	public void setColor(int alpha,int red,int green,int blue)
	{
		// TODO: Implement this method
	}

	@Override
	public void setColor(Color parent)
	{
		// TODO: Implement this method
	}

	@Override
	public void recycle()
	{
		// TODO: Implement this method
	}

	@Override
	public boolean isRecycled()
	{
		// TODO: Implement this method
		return false;
	}
	
}
