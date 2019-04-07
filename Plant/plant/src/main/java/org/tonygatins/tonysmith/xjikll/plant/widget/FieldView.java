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
package org.tonygatins.tonysmith.xjikll.plant.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import org.tonygatins.tonysmith.xjikll.plant.graphics.drawable.FieldBackgroundDrawable;
import android.view.View.OnClickListener;

/**
 * 田块的视图实现。
 * @author Tony Chen Smith
 */
public final class FieldView extends View
{
	//图片的大小
	private final Rect src=new Rect();

	/**
	 * 构造一个田视图。没有额外属性。
	 * @param context 系统提供的context。
	 * @param attrs 系统给的属性列表。
	 */
	public FieldView(Context context,AttributeSet attrs)
	{
		super(context,attrs);
	}

	/**
	 * 重写测定方法。
	 * @param widthMeasureSpec 宽度测量值。
	 * @param heightMeasureSpec 高度测量值。
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec)
	{
		// TODO: Implement this method
		//长宽的模式
		int widthMode=MeasureSpec.getMode(widthMeasureSpec),heightMode=MeasureSpec.getMode(heightMeasureSpec);

		//长宽的外界大小
		int envWidth=MeasureSpec.getSize(widthMeasureSpec),envHeight=MeasureSpec.getSize(heightMeasureSpec);

		//结果长宽
		int resultWidth,resultHeight;

		if(widthMode == MeasureSpec.EXACTLY)
		{
			resultWidth = envWidth;
		}
		else
		{
			resultWidth = src.width()+2;
		}

		if(heightMode == MeasureSpec.EXACTLY)
		{
			resultHeight = envHeight;
		}
		else
		{
			resultHeight = src.height()+2;
		}

		//设置高宽
		setMeasuredDimension(resultWidth,resultHeight);
	}

	/**
	 * 绘制方法（现在因为没有植株不进行其他操作）
	 * @param 系统给的画布对象。
	 */
	@Override
	protected void onDraw(Canvas canvas)
	{
		// TODO: Implement this method
		super.onDraw(canvas);
	}

	/**
	 * 特殊的设置背景的方法。
	 * @param background 背景图片的对象。
	 */
	@Override
	public void setBackground(Drawable background)
	{
		// TODO: Implement this method
		if(background instanceof FieldBackgroundDrawable)
		{
			src.set(((FieldBackgroundDrawable)background).getRect());
		}
		super.setBackground(background);
	}

	private boolean isContinue=true;
	private Thread waiter=new Thread(new Runnable(){

		@Override
		public void run()
		{
			// TODO: Implement this method
			try
			{
				Thread.sleep(5000);
			}
			catch(InterruptedException e)
			{}
			finally
			{
				isContinue=true;
			}
		}
	});
	
	/**
	 * 重写状态获得方法。
	 */
	@Override
	protected void drawableStateChanged()
	{
		// TODO: Implement this method
		super.drawableStateChanged();
		if(!waiter.isAlive()&&isContinue&&getBackground().setState(getDrawableState()))
		{
			isContinue=false;
			waiter.start();
			invalidate();
		}
	}
}
