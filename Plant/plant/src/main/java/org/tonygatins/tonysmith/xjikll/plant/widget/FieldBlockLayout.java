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
import android.widget.GridLayout;
import android.util.AttributeSet;
import android.widget.Scroller;
import android.view.MotionEvent;

/**
 * 新的布局管理器，管理田块。
 * @author Tony Chen Smith
 */
public final class FieldBlockLayout extends GridLayout
{
	/**
	 * 初始化属性。
	 * @param context 系统给的内容对象。
	 * @param attrs 系统给的属性集对象。
	 */
	public FieldBlockLayout(Context context,AttributeSet attrs)
	{
		super(context,attrs);
		scroller=new Scroller(context);
	}

	//滑动器对象
	private final Scroller scroller;

	/**
	 * 重写触摸事件。
	 * @param event 事件位移对象。
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		// TODO: Implement this method
		return super.onTouchEvent(event);
	}
}
