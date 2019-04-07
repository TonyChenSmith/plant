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
import java.util.Objects;

/**
 * 背景用的图片，在这里做出一定的修改。
 * @author Tony Chen Smith
 */
public class BackgroundDrawable extends Drawable
{
	//传入的bitmap
	private final Bitmap resource;
	private final Paint pen=new Paint();

	/**
	 * 用bitmap构造一个对象。
	 * @param resource bitmap对象。
	 */
	public BackgroundDrawable(Bitmap resource)
	{
		//出错报告
		this.resource = Objects.requireNonNull(resource);
		//图像抖动
		pen.setDither(true);
		//不抗锯齿
		pen.setAntiAlias(false);
		//透明度满
		pen.setAlpha(255);
	}

	/**
	 * 绘图过程。
	 * @param canvas 系统给的画布对象。
	 */
	@Override
	public void draw(Canvas canvas)
	{

		//realSize实际大小矩形对象
		final Rect realSize=getBounds();
		final int[] envSize=new int[]{realSize.width(),realSize.height()};
		
		//如果背景图被回收了
		if(resource.isRecycled())
		{
			pen.reset();
			pen.setColor(Color.WHITE);
			canvas.drawPaint(pen);
			return;
		}
		else
		{
			resource.prepareToDraw();
		}
		
		//图片的大小
		final int[] resSize=new int[]{resource.getWidth(),resource.getHeight()};
		
		//source源矩形，limit限制矩形
		final Rect source=new Rect();
		final Rect limit=new Rect();
		
		//图片宽于提供的环境宽
		if(resSize[0]>=envSize[0])
		{
			//图片高于提供的环境高
			if(resSize[1]>=envSize[1])
			{
				source.set(0,0,envSize[0],envSize[1]);
				limit.set(0,0,envSize[0],envSize[1]);
				canvas.drawBitmap(resource,source,limit,pen);
				return;
			}
			else //图片高低于环境高
			{
				int heightTop=0,heightBottom=resSize[1];
				source.set(0,0,envSize[0],resSize[1]);
				while(true)
				{
					limit.set(0,heightTop,envSize[0],heightBottom);
					canvas.drawBitmap(resource,source,limit,pen);
					
					if(heightBottom==envSize[1])
					{
						return;
					}
					
					//修改偏移
					heightTop=heightTop+resSize[1];
					heightBottom=heightBottom+resSize[1];
					
					if(heightBottom>envSize[1])
					{
						source.set(0,0,envSize[0],resSize[1]-(heightBottom-envSize[1]));
						heightBottom=envSize[1];
					}
				}
			}
		}
		else //图片宽低于提供的环境宽
		{
			//图片高于提供的环境高
			if(resSize[1]>=envSize[1])
			{
				int widthLeft=0,widthRight=resSize[1];
				source.set(0,0,resSize[0],envSize[1]);
				while(true)
				{
					limit.set(widthLeft,0,widthRight,envSize[1]);
					canvas.drawBitmap(resource,source,limit,pen);

					if(widthRight==envSize[0])
					{
						return;
					}

					//修改偏移
					widthLeft=widthLeft+resSize[0];
					widthRight=widthRight+resSize[0];

					if(widthRight>envSize[0])
					{
						source.set(0,0,resSize[0]-(widthRight-envSize[0]),envSize[1]);
						widthRight=envSize[0];
					}
				}
			}
			else //图片高低于环境高
			{
				int heightTop=0,heightBottom=resSize[1];
				int markY=resSize[1];
				source.set(0,0,resSize[0],resSize[1]);
				while(true)
				{
					int widthLeft=0,widthRight=resSize[1];
					while(true)
					{
						limit.set(widthLeft,heightTop,widthRight,heightBottom);
						canvas.drawBitmap(resource,source,limit,pen);

						if(widthRight==envSize[0])
						{
							break;
						}

						//修改偏移
						widthLeft=widthLeft+resSize[0];
						widthRight=widthRight+resSize[0];

						if(widthRight>envSize[0])
						{
							source.set(0,0,resSize[0]-(widthRight-envSize[0]),markY);
							widthRight=envSize[0];
						}
					}
					
					if(heightBottom==envSize[1])
					{
						return;
					}

					//修改偏移
					heightTop=heightTop+resSize[1];
					heightBottom=heightBottom+resSize[1];

					if(heightBottom>envSize[1])
					{
						source.set(0,0,resSize[0],markY=resSize[1]-(heightBottom-envSize[1]));
						heightBottom=envSize[1];
					}
				}
			}
		}
	}

	/**
	 * 特定用途，不需要实现。
	 * @param p1 透明度大小。
	 */
	@Override
	public void setAlpha(int p1)
	{
		// TODO: Implement this method
	}

	/**
	 * 特殊用途，不需要实现。
	 * @param p1 色彩对象。
	 */
	@Override
	public void setColorFilter(ColorFilter p1)
	{
		// TODO: Implement this method
	}

	/**
	 * 返回透明度，在API Q弃用。
	 * @return 一个PixelFormat枚举对象。
	 */
	@Override
	public int getOpacity()
	{
		// TODO: Implement this method
		return PixelFormat.OPAQUE;
	}

	/**
	 * 回收bitmap。
	 */
	public void recycle()
	{
		resource.recycle();
	}

	/**
	 * 子类获得bitmap的方法。
	 * @return 初始化时得到的bitmap对象。
	 */
	protected Bitmap get()
	{
		return resource;
	}
}
