/**
 * MIT License
 *
 * Copyright (c) 2019 Tony Chen Smith and xjikll
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
package org.tonygatins.tonysmith.xjikll.plant.graphics.drawable;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/**
 * 背景用的图片，在这里做出一定的修改。
 * @author Tony Chen Smith
 */
public class BackgroundDrawable extends Drawable
{
	//传入的bitmap
	private final Bitmap resource;
	
	/**
	 * 用bitmap构造一个对象。
	 */
	public BackgroundDrawable(Bitmap resource)
	{
		this.resource=resource;
	}

	/**
	 * 绘图过程。
	 */
	@Override
	public void draw(Canvas canvas)
	{
		//曾用过程
		/*Rect env=getBounds();
		
		//环境长宽
		int envWidth=env.width(),envHeight=env.height();
		
		// TODO: Implement this method
		if(resource.isRecycled())
		{
			Paint pen=new Paint();
			pen.setColor(Color.WHITE);
			canvas.drawPaint(pen);
			return;
		}
		else
		{
			resource.prepareToDraw();
		}
		
		//图片的大小
		int resWidth=resource.getWidth(),resHeight=resource.getHeight();
		
		//界面过大时标记的位置。
		int resultY=0,resultX=0,endX = resWidth,endY=resHeight;
		
		//记录变化量
		int mark = 0;
		
		//printer打印限制，source截图限制
		Rect printer=new Rect(),source=new Rect(resultX,resultY,endX,endY);
		
		if(envWidth<=resWidth)
		{
			if(envHeight<=resHeight)
			{
				canvas.drawBitmap(resource,env,env,null);
				return;
			}
			else
			{
				do
				{
					printer.set(resultX,resultY,envWidth,endY);
					canvas.drawBitmap(resource,source,printer,null);
					if(endY==envHeight)
					{
						break;
					}
					resultY=resultY+resHeight;
					endY=endY+resHeight;
					if(endY>envHeight) {
						source.set(0,0,envWidth,envHeight-endY+resHeight);
						endY=envHeight;
					}
				} while(true);
				return;
			}
		}
		else
		{
			if(envHeight<=resHeight)
			{
				do
				{
					printer.set(resultX,resultY,endX,envHeight);
					canvas.drawBitmap(resource,source,printer,null);
					if(endX==envWidth)
					{
						break;
					}
					resultX=resultX+resWidth;
					endX=endX+resWidth;
					if(endX>envWidth) {
						source.set(0,0,envWidth-endX+resWidth,envHeight);
						endX=envWidth;
					}
				} while(true);
				return;
			}
			else
			{
				mark=resHeight;
				do
				{
					do
					{
						printer.set(resultX,resultY,endX,endY);
						canvas.drawBitmap(resource,source,printer,null);
						if(endX==envWidth)
						{
							break;
						}
						resultX=resultX+resWidth;
						endX=endX+resWidth;
						if(endX>envWidth) {
							source.set(0,0,envWidth-endX+resWidth,mark);
							endX=envWidth;
						}
					} while(true);
					
					if(endY==envHeight)
					{
						break;
					}
					
					resultX=0;
					endX=resWidth;
					source.set(0,0,resWidth,resHeight);
					
					resultY=resultY+resHeight;
					endY=endY+resHeight;
					if(endY>envHeight) {
						source.set(0,0,envWidth,mark=envHeight-endY+resHeight);
						endY=envHeight;
					}
				} while(true);
				return;
			}
		}
		
		*/
		
		//realSize实际大小矩形
		final Rect realSize=getBounds();
		
		final int[] envSize=new int[]{realSize.width(),realSize.height()};
	}

	/**
	 * 特定用途，不需要实现。
	 */
	@Override
	public void setAlpha(int p1)
	{
		// TODO: Implement this method
	}

	/**
	 * 特殊用途，不需要实现。
	 */
	@Override
	public void setColorFilter(ColorFilter p1)
	{
		// TODO: Implement this method
	}

	/**
	 * 返回透明度，在API Q弃用。
	 */
	@Override
	public int getOpacity()
	{
		// TODO: Implement this method
		return PixelFormat.OPAQUE;
	}
	
	/**
	 * 回收bitmap
	 */
	public void recycle()
	{
		resource.recycle();
	}
	
	/**
	 * 子类获得bitmap的方法。
	 */
	protected Bitmap get()
	{
		return resource;
	}
}
