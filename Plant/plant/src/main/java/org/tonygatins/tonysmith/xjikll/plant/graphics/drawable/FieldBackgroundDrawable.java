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

/**
 * 田块按钮的背景实现。
 * @author Tony Chen Smith
 */
public final class FieldBackgroundDrawable extends BackgroundDrawable
{
	//图片的大小
	private final Rect src;
	
	/**
	 * 用一个bitmap构造对象。
	 */
	public FieldBackgroundDrawable(Bitmap resource)
	{
		super(resource);
		src=new Rect(0,0,get().getWidth(),get().getHeight());
	}
	
	/**
	 * 获得背景图片的大小。
	 */
	public Rect getRect()
	{
		return src;
	}
	
	/**
	 * 特殊的绘制背景方法。
	 */
	@Override
	public void draw(Canvas canvas)
	{
		// TODO: Implement this method
		if(getBounds().centerX()==src.centerX()&&getBounds().centerY()==src.centerY())
		{
			canvas.drawBitmap(get(),null,getBounds(),null);
		}
		else
		{
			Paint pen=new Paint();
			pen.setColor(Color.WHITE);
			canvas.drawPaint(pen);
			return;
		}
	}
}
