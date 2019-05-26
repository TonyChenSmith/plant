/**
 * MIT License
 *
 * Copyright (c) 2019 Tony Chen Smith
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.tonygatins.tonysmith.api;
import java.util.HashSet;
import java.util.Set;

/**
 * conch 海螺。
 * Tony Gatins Conch Graphics Engine API.
 * 海螺引擎的总核心类。具体的底层实现与系统有关。
 * 设计初衷是Tony Gatins系统的底层绘图api。
 * @author Tony Chen Smith
 */
public final class TGConchGE
{
	private final LocalGEDrawingHandler handler;
	
	private TGConchGE(LocalGEDrawingHandler handler)
	{
		this.handler=handler;
	}
	
	protected static abstract class LocalGEDrawingHandler
	{
		private final int width;
		
		private final int height;
		
		protected LocalGEDrawingHandler(int width,int height)
		{
			this.width=width;
			this.height=height;
		}
		
		//传递图像到屏幕
		public abstract void postGraphivs(LocalGEContext context);
		
		//包装一个本地的对象内容
		public abstract LocalGEContext createContext(Object localContext);
	}
	
	protected static abstract class LocalGEContext<T>
	{
		public static final int UNDEFINE = -1;
		
		private static final HashSet<Pair> ID_SET = new HashSet<>();
		
		private final T context;
		
		//种类id
		private final int id;
		
		protected LocalGEContext(T context,String idText)
		{
			this.context=context;
			for(Pair now : ID_SET)
			{
				if(now.idText.equals(idText))
				{
					id=now.id;
					return;
				}
			}
			
			id=UNDEFINE;
		}
		
		protected LocalGEContext()
		{
			context = null;
			id= UNDEFINE;
			initIDSet(ID_SET);
		}
		
		public final T getContext()
		{
			return context;
		}
		
		public final int getID()
		{
			return id;
		}
		
		protected abstract void initIDSet(Set<Pair> idSet);
		
		private class Pair
		{
			int id;
			String idText;

			@Override
			public boolean equals(Object obj)
			{
				// TODO: Implement this method
				if(obj instanceof Pair&&((Pair)obj).id==id)
				{
					return true;
				}
				else
				{
					return false;
				}
			}
		}
	}
}
