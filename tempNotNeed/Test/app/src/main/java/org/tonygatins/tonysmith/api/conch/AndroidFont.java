package org.tonygatins.tonysmith.api.conch;

import android.graphics.Typeface;
import java.io.File;
import org.tonygatins.tonysmith.api.conch.resource.Font;

/**
 * 安卓底层实现：字体。
 * @author Tony Chen Smith
 */
final class AndroidFont extends Font<Typeface>
{
	//是否回收。
	private boolean isRec;
	
	//本地字体对象。
	private Typeface nativeFont;
	
	//私有构造方法。
	private AndroidFont(File file)
	{
		nativeFont=Typeface.createFromFile(file);
		isRec=false;
	}
	
	/**
	 * 获得本地字体对象。
	 * @return 本地的字体对象。
	 */
	@Override
	public Typeface getNativeFont()
	{
		// TODO: Implement this method
		return nativeFont;
	}

	/**
	 * 判断字体对象是否回收。
	 * @return true为已回收，false为未回收。
	 */
	@Override
	public boolean isRecycled()
	{
		// TODO: Implement this method
		return isRec;
	}

	/**
	 * 回收字体。
	 */
	@Override
	public void recycle()
	{
		// TODO: Implement this method
		nativeFont=null;
		isRec=true;
	}
	
	/**
	 * 本地通过文件对象构造字体。
	 * @param file 文件对象。
	 * @return 一个字体对象。
	 */
	protected static Font nativeCreateFont(File file)
	{
		return new AndroidFont(file);
	}
}
