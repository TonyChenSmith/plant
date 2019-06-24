package org.tonygatins.tonysmith.api.conch;

import android.graphics.Typeface;
import java.io.File;
import java.util.Arrays;
import org.tonygatins.tonysmith.api.conch.resource.Font;
import org.tonygatins.tonysmith.api.conch.resource.FontStyle;

/**
 * 安卓底层实现：字体。
 * @author Tony Chen Smith
 */
final class AndroidFont extends Font<Typeface>
{
	//本地字体对象。
	private final Typeface nativeFont;
	
	//字体风格。
	private final FontStyle[] styles;
	
	//是否无衬线。
	private final boolean isSans;
	
	/* 私有构造方法。
	 * @param file 文件对象。
	 * @param styles 风格数组。
	 */
	private AndroidFont(File file,boolean isSans,FontStyle styles[])
	{
		nativeFont=Typeface.createFromFile(file);
		this.isSans=isSans;
		this.styles=styles;
	}
	
	/* 私有构造方法。
	 * @param font 本地字体对象。
	 * @param styles 风格数组。
	 */
	private AndroidFont(Typeface font,boolean isSans,FontStyle styles[])
	{
		nativeFont=font;
		this.isSans=isSans;
		this.styles=styles;
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
		return false;
	}

	/**
	 * 回收字体。(无需实现)
	 */
	@Override
	public void recycle()
	{
		// TODO: Implement this method
	}
	
	/**
	 * 获得字体的风格。
	 * @return 字体风格的枚举常量数组。
	 */
	@Override
	public FontStyle[] getStyles()
	{
		// TODO: Implement this method
		return Arrays.copyOf(styles,styles.length);
	}
	
	/**
	 * 本地通过文件对象构造字体。
	 * @param file 文件对象。
	 * @param isSans 无衬线标志。
	 * @param styles 字体风格数组。
	 * @return 一个字体对象。
	 */
	protected static Font nativeCreateFont(File file,boolean isSans,FontStyle styles[])
	{
		return new AndroidFont(file,isSans,styles);
	}
	
	/**
	 * 本地通过本地字体对象构造字体。
	 * @param font 本地字体对象。
	 * @param isSans 无衬线标志。
	 * @param styles 字体风格数组。
	 * @return 一个字体对象。
	 */
	protected static Font nativeCreateFont(Typeface font,boolean isSans,FontStyle styles[])
	{
		return new AndroidFont(font,isSans,styles);
	}
}
