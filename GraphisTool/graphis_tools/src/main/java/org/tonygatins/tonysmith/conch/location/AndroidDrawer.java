package org.tonygatins.tonysmith.conch.location;

import android.graphics.Canvas;
import java.util.Objects;
import org.tonygatins.tonysmith.conch.tools.ConchDrawer;

/**
 * 画师本地实现。
 * @author Tony Chen Smith
 */
class AndroidDrawer extends ConchDrawer
{
	//操控的画布
	private Canvas handle;
	
	//用画布构建画师
	protected AndroidDrawer(Canvas canvas)
	{
		handle=Objects.requireNonNull(canvas,"Canvas object is null!");
	}
}
