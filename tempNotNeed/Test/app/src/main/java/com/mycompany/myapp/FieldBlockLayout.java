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
package com.mycompany.myapp;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * 新的布局管理器，管理田块。
 * @author Tony Chen Smith
 */
public final class FieldBlockLayout extends ViewGroup
{
	/**
	 * 初始化属性。
	 * @param context 系统给的内容对象。
	 * @param attrs 系统给的属性集对象。
	 */
	public FieldBlockLayout(Context context,AttributeSet attrs)
	{
		super(context,attrs);
	}
	
	//最后ACTION_MOVE位移坐标。
	private final float[] last = new float[2];
	
	//按下时的坐标。
	private final float[] down = new float[2];
	
	//位移距离。
	private final float[] move = new float[2];

	//边界大小。
	private final int[][] border = new int[2][2];
	
	/**
	 * 重写触摸事件。
	 * @param event 事件位移对象。
	 * @return true为已解决。
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		// TODO: Implement this method
		switch(event.getAction())
		{
			//移动
			case MotionEvent.ACTION_MOVE:
				move[0]=event.getRawX();
				move[1]=event.getRawY();
				
				//获得偏移值
				int scrolledX=(int)(last[0]-move[0]);
				int scrolledY=(int)(last[1]-move[1]);
				
				if(getScrollX()+scrolledX<border[0][0])
				{
					if(getScrollY()+scrolledY<border[0][1])
					{
						scrollTo(border[0][0],border[0][1]);
						return true;
					}
					else if(getScrollY()+getHeight()+scrolledY>border[1][1])
					{
						scrollTo(border[0][0],border[1][1]-getHeight());
						return true;
					}
					
					scrollTo(border[0][0],0);
					scrollBy(0,scrolledY);
					last[1]=move[1];
					
					return super.onTouchEvent(event);
				}
				else if(getScrollX()+getHeight()+scrolledX>border[1][0])
				{
					if(getScrollY()+scrolledY<border[0][1])
					{
						scrollTo(border[1][0]-getWidth(),border[0][1]);
						return true;
					}
					else if(getScrollY()+getHeight()+scrolledY>border[1][1])
					{
						scrollTo(border[1][0],border[1][1]);
						return true;
					}

					scrollTo(border[1][0],0);
					scrollBy(0,scrolledY);
					last[1]=move[1];
					
					return super.onTouchEvent(event);
				}
				
				scrollBy(scrolledX,scrolledY);
				last[0]=move[0];
				last[1]=move[1];
				
				return super.onTouchEvent(event);
		}
		return super.onTouchEvent(event);
	}

	/**
	 * 重写中断触摸事件方法。
	 * @param ev 事件位移对象。
	 * @return true为拦截。传送至触摸监听器。
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev)
	{
		// TODO: Implement this method
		switch(ev.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				down[0]=ev.getRawX();
				down[1]=ev.getRawY();
				last[0]=down[0];
				last[1]=down[1];
				break;
			case MotionEvent.ACTION_MOVE:
				move[0]=ev.getRawX();
				move[1]=ev.getRawY();
				float distanceX=Math.abs(move[0]-down[0]);
				float distanceY=Math.abs(move[1]-down[1]);
				Log.i("Value",Float.toString(distanceX)+"+"+Float.toString(distanceY));
				last[0]=move[0];
				last[1]=move[1];
				
				if(distanceX > 5 || distanceY > 5)
				{
					return true;
				}
				break;
		}
		
		return super.onInterceptTouchEvent(ev);
	}

	/**
	 * 重写布局方法，测量边界。
	 * @param changed 是否改变。true为已改变。
	 * @param left 左部的坐标。
	 * @param top 顶部的坐标。
	 * @param right 右部的坐标。
	 * @param bottom 底部的坐标。
	 */
	@Override
	protected void onLayout(boolean changed,int left,int top,int right,int bottom)
	{
		// TODO: Implement this method
		
		border[0][0]=getChildAt(0).getLeft();
		border[0][1]=getChildAt(0).getTop();
		
		border[1][0]=getChildAt(getChildCount()-1).getRight();
		border[1][1]=getChildAt(getChildCount()-1).getBottom();
	}
	
	
}
