package org.tonygatins.tonysmith.api.conch;

import android.graphics.Typeface;
import org.tonygatins.tonysmith.api.conch.resource.Font;

/**
 * 安卓底层实现：字体。
 * @author Tony Chen Smith
 */
final class AndroidFont extends Font<Typeface>
{
	private void test()
	{
		
	}
	
	@Override
	public Typeface getNativeFont()
	{
		// TODO: Implement this method
		return null;
	}

	@Override
	public boolean isRecycled()
	{
		// TODO: Implement this method
		return false;
	}

	@Override
	public void recycle()
	{
		// TODO: Implement this method
	}
	
}
