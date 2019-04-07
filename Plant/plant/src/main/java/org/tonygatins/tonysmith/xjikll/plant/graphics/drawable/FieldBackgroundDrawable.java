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
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/**
 * 田块按钮的背景实现。
 * @author Tony Chen Smith
 */
public final class FieldBackgroundDrawable extends BackgroundDrawable
{
	//图片的大小
	private final Rect src;

	private final Paint pen = new Paint();

	/**
	 * 用一个bitmap构造对象。
	 * @param resource bitmap对象。
	 */
	public FieldBackgroundDrawable(Bitmap resource)
	{
		super(resource);
		src = new Rect(0,0,get().getWidth(),get().getHeight());
		pen.reset();
		pen.setAlpha(255);
		pen.setAntiAlias(true);
		pen.setColor(Color.BLACK);
	}

	/**
	 * 获得背景图片的大小。
	 * @return 代表图片大小的矩形对象。
	 */
	public Rect getRect()
	{
		return src;
	}

	/**
	 * 特殊的绘制背景方法。
	 * @param canvas 系统提供的画布对象。
	 */
	@Override
	public void draw(Canvas canvas)
	{
		// TODO: Implement this method
		if(getBounds().centerX() == src.centerX() + 1 && getBounds().centerY() == src.centerY() + 1)
		{
			canvas.drawPaint(pen);
			canvas.drawBitmap(get(),null,new Rect(1,1,getBounds().width() - 1,getBounds().height() - 1),null);
		}
		else
		{
			pen.reset();
			pen.setAlpha(255);
			pen.setColor(Color.WHITE);
			canvas.drawPaint(pen);
			return;
		}
	}

	/**
	 * 状态改变时的处理方法。
	 * @param state 代表状态id的数组对象。
	 * @return 是否改变了状态(只有true)。
	 */
	@Override
	protected boolean onStateChange(int[] state)
	{
		// TODO: Implement this method
		for(int localState : state)
		{
			switch(localState)
			{
				case android.R.attr.state_pressed:
					pen.reset();
					pen.setAlpha(255);
					pen.setColor(Color.WHITE);
					return true;
				default:
					pen.reset();
					pen.setAlpha(255);
					pen.setColor(Color.BLACK);
					return true;
			}
		}
		return false;
	}

}
