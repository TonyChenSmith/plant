package org.tonygatins.tonysmith.api.conch;

import android.graphics.Bitmap;
import org.tonygatins.tonysmith.api.conch.resource.Paper;

/**
 * 安卓底层实现：纸张。
 * @author Tony Chen Smith
 */
final class AndroidPaper extends Paper<Bitmap>
{
	//本地纸张对象。
	private Bitmap nativePaper;
	
	/* 私有构造方法。
	 * @param width 宽度值。
	 * @param height 高度值。
	 */
	private AndroidPaper(int width,int height)
	{
		nativePaper=Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
	}
	
	/**
	 * 设置纸。
	 * @param width 纸的宽度。
	 * @param height 纸的高度。
	 */
	@Override
	public void setPaper(int width,int height)
	{
		// TODO: Implement this method
		if(isRecycled())
		{
			throw new ConchGEException("");
		}
		nativePaper.setHeight(height);
		nativePaper.setWidth(width);
	}

	/**
	 * 设置纸。该方法性质为设置大小深拷贝。
	 * @param parent 父辈纸资源对象。
	 */
	@Override
	public void setPaper(Paper parent)
	{
		// TODO: Implement this method
		if(isRecycled())
		{
			throw new ConchGEException("");
		}
		nativePaper.setHeight(parent.getHeight());
		nativePaper.setWidth(parent.getWidth());
	}

	/**
	 * 获得纸（本地资源对象）。
	 * @return 本地的资源对象。
	 */
	@Override
	public Bitmap getNativePaper()
	{
		// TODO: Implement this method
		if(isRecycled())
		{
			throw new ConchGEException("");
		}
		return nativePaper;
	}

	/**
	 * 获得纸张宽度。
	 * @return 纸张的宽度。
	 */
	@Override
	public int getWidth()
	{
		// TODO: Implement this method
		if(isRecycled())
		{
			throw new ConchGEException("");
		}
		return nativePaper.getWidth();
	}

	/**
	 * 获得纸张的长度（高度）。
	 * @return 纸张的长度(高度）。
	 */
	@Override
	public int getHeight()
	{
		// TODO: Implement this method
		if(isRecycled())
		{
			throw new ConchGEException("");
		}
		return nativePaper.getHeight();
	}

	/**
	 * 判断是否已回收。
	 * @return true为已回收，false为未回收。
	 */
	@Override
	public boolean isRecycled()
	{
		// TODO: Implement this method
		return nativePaper == null;
	}

	/**
	 * 回收方法。
	 */
	@Override
	public void recycle()
	{
		// TODO: Implement this method
		nativePaper.recycle();
		nativePaper=null;
	}
	
}
