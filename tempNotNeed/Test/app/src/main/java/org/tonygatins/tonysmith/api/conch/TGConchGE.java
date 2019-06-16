package org.tonygatins.tonysmith.api.conch;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.tonygatins.tonysmith.api.conch.resource.Brush;

/**
 * conch 海螺。
 * Tony Gatins Conch Graphics Engine API.
 * 海螺引擎的总核心类。具体的底层实现与系统有关。
 * 主句柄类。
 * 设计初衷是Tony Gatins系统的底层绘图api。
 * @author Tony Chen Smith
 */
public final class TGConchGE
{
	private final LocalConchGE handler;
	
	private TGConchGE(LocalConchGE handler)
	{
		this.handler=handler;
	}
	
	/**
	 * 本地句柄类。
	 * 提供所有本地的操作方法。
	 * @author Tony Chen Smith
	 */
	public static abstract class LocalConchGE
	{
		protected abstract void nativeDrawRGB(int red,int green,int blue);
		protected abstract void nativeDrawARGB(int alpha,int red,int green,int blue);
		protected abstract void nativeDrawBrush(Brush brush);
		
		protected abstract void nativeDrawPoint(float x,float y,Brush brush);
	}
}
