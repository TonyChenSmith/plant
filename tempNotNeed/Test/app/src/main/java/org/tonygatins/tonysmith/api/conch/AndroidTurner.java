package org.tonygatins.tonysmith.api.conch;

import android.graphics.Bitmap;
import org.tonygatins.tonysmith.api.conch.resource.Paper;

/**
 * 本地资源转换。
 * @author Tony Chen Smith
 */
final class AndroidTurner
{
	//禁用构造方法。
	private AndroidTurner(){}
	
	//获得本地纸张对象。
	protected static Bitmap paperToBitmap(Paper paper)
	{
		return (Bitmap)paper.getNativePaper();
	}
}
