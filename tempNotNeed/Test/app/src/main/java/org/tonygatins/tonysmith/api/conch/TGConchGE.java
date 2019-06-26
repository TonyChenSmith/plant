package org.tonygatins.tonysmith.api.conch;

import org.tonygatins.tonysmith.api.conch.resource.Brush;
import org.tonygatins.tonysmith.api.conch.resource.Color;

/**
 * conch 海螺。
 * Tony Gatins Conch Graphics Engine API.
 * 海螺引擎的总核心类。具体的底层实现与系统有关。
 * 核心类。
 * 设计初衷是Tony Gatins系统的底层绘图api。
 * @author Tony Chen Smith
 */
public final class TGConchGE
{
	//私有构造方法。
	private TGConchGE(){}

	/**
	 * 本地句柄类。
	 * 提供所有本地的操作方法。
	 * @author Tony Chen Smith
	 */
	public static abstract class LocalConchGE
	{
		//单色绘制。
		
		/**
		 * 本地绘制画笔。
		 * @param brush 画笔对象。
		 */
		protected abstract void nativeDrawBrush(Brush brush);
		
		/**
		 * 本地绘制颜料。
		 * @param color 颜料对象。
		 */
		protected abstract void nativeDrawColor(Color color);

		//点线绘制
		
		/**
		 * 本地绘制一个点。
		 * @param x 横坐标。
		 * @param y 纵坐标。
		 * @param brush 画笔对象。
		 */
		protected abstract void nativeDrawPoint(float x,float y,Brush brush);
	}
}
