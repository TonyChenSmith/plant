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
import java.util.Objects;
import java.util.Set;
import org.tonygatins.tonysmith.api.TGConchGE.LocalGEResource.Pair;

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
		public abstract void postGraphivs(LocalGEHandler context);
		
		//包装一个本地的对象内容
		public abstract LocalGEResource createResourceContext(Object localResourceContext);
		
		//设置底层绘制板(非显示板)
		public abstract void setBackCanvasHandler(LocalGEHandler canvas);
		
		
	}
	
	//资源包装类
	protected static abstract class LocalGEResource<T>
	{
		private static final int UNDEFINE = -1;
		
		private static final HashSet<Pair> ID_SET = new HashSet<>();
		
		private final T context;
		
		protected static boolean isInit =false;
		
		//种类id
		private final int id;
		
		protected LocalGEResource(T context,String idText)
		{
			if(!isInit)
			{
				initIDSet(ID_SET);
				isInit=true;
			}
			
			//暂时英文
			this.context=Objects.requireNonNull(context,"Conch Engine can't use an empty resource.Pleace report the exception to developers.");
			
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
		
		public final T getContext()
		{
			return context;
		}
		
		public final int getID()
		{
			return id;
		}
		
		protected abstract void initIDSet(Set<Pair> idSet);
		
		protected class Pair
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
	
	//句柄包装
	protected static abstract class LocalGEHandler<T> extends LocalGEResource<T>
	{
		private static boolean isAdded = false;
		
		private static boolean initState;
		
		{
			initState=isInit;
			
			if(!isAdded)
			{
				isInit=false;
				isAdded=true;
			}
		}
		
		protected LocalGEHandler(T context)
		{
			super(context,"Conch GE Handler");
			isInit=initState;
		}

		@Override
		protected final void initIDSet(Set<TGConchGE.LocalGEResource<T>.Pair> idSet)
		{
			// TODO: Implement this method
			Pair pair = new Pair();
			pair.idText="Conch GE Handler";
			pair.id=-2;
			idSet.add(pair);
		}
	}
}
