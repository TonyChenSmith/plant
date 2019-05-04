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
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.mycompany.myapp.R;
import android.view.MotionEvent;
import android.support.v4.view.ViewConfigurationCompat;
import android.view.ViewConfiguration;

/**
 * 新的布局管理器，管理田块视图，可以拖动。
 * @author Tony Chen Smith
 */
public final class FieldBlockLayout extends ViewGroup
{
	//成员数据域
	
	/* 表：***** column(x)
	 *	  *
	 *	  *
	 *	  *
	 *	  *row(y)
	 */
	
	//柱数
	private int fieldColumnCount = 0;
	
	//行数
	private int fieldRowCount = 0;
	
	//单元格长度
	private int unitWidth = 0;
	
	//单元格高度
	private int unitHeight = 0;
	
	//位置记录,1维柱，2维行
	private boolean[][] viewLocationTable=null;
	
	//最小的拖动位移像素
	private final int mixTouchSlop;
	
	//点下去的位置，0为x(c)，1为y(r)
	private final float[] downLocation=new float[2];
	
	//最后位移位置
	private final float[] lastMoveLocation=new float[2];
	
	//移动时的位置
	private final float[] moveLocation=new float[2];
	
	//边界位置，0l 1t 2r 3b
	private final int[] border=new int[4];
	
	//构造方法
	
	/**
	 * 初始化基本属性。
	 * @param context 系统给的内容对象。
	 */
	public FieldBlockLayout(Context context)
	{
		super(context);
		mixTouchSlop=ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get(context));
		resetViewLocationTable();
	}
	
	/**
	 * 初始化属性及自定义属性。
	 * @param context 系统给的内容对象。
	 * @param attrs 系统给的属性集对象。
	 */
	public FieldBlockLayout(Context context,AttributeSet attrs)
	{
		//调用原来父类的构造方法
		super(context,attrs);
		
		mixTouchSlop=ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get(context));

		//检查自定义参数
		TypedArray subAttrSet=context.obtainStyledAttributes(attrs,R.styleable.fieldBlockLayout);
		final int setMemberCount=subAttrSet.getIndexCount();
		for(int itr=0;itr < setMemberCount;itr++)
		{
			//0为现场测量
			switch(subAttrSet.getIndex(itr))
			{
				case R.styleable.fieldBlockLayout_fieldColumnCount:
					setFieldColumnCount(subAttrSet.getInt(R.styleable.fieldBlockLayout_fieldColumnCount,0));
					break;
				case R.styleable.fieldBlockLayout_fieldRowCount:
					fieldRowCount=subAttrSet.getInt(R.styleable.fieldBlockLayout_fieldRowCount,0);
					if(fieldRowCount<0)
					{
						fieldRowCount=0;
					}
					break;
			}
		}
		subAttrSet.recycle();
		resetViewLocationTable();
	}
	
	//成员方法
	
	//数据域修改
	
	/**
	 * 设置田块布局的柱数。
	 * @param fieldColumnCount 田块布局柱数。
	 */
	public void setFieldColumnCount(int fieldColumnCount)
	{
		if(fieldColumnCount<0)
		{
			fieldColumnCount=0;
		}
		
		this.fieldColumnCount=fieldColumnCount;
		invalidate();
	}
	
	/**
	 * 获得田块布局的柱数。
	 * @return 一个田块布局柱数的整型值。
	 */
	public int getFieldColumnCount()
	{
		return fieldColumnCount;
	}
	
	/**
	 * 设置田块布局的行数。
	 * @param fieldRowCount 田块布局行数。
	 */
	public void setFieldRowCount(int fieldRowCount)
	{
		if(fieldRowCount<0)
		{
			fieldRowCount=0;
		}
		this.fieldRowCount=fieldRowCount;
		invalidate();
	}
	
	/**
	 * 获得田块布局的行数。
	 * @return 一个田块布局行数的整型值。
	 */
	public int getFieldRowCount()
	{
		return fieldRowCount;
	}
	
	//重置子视图位置表
	private void resetViewLocationTable()
	{
		//长度不变就返回
		if(viewLocationTable!=null&&viewLocationTable.length==fieldColumnCount&&viewLocationTable[0].length==fieldRowCount)
		{
			return;
		}
		else
		{
			viewLocationTable=new boolean[fieldColumnCount][fieldRowCount];
			for(int x=0;x<fieldColumnCount;x++)
			{
				for(int y=0;y<fieldRowCount;y++)
				{
					viewLocationTable[x][y]=false;
				}
			}
		}
	}
	
	//重载实现
	
	/**
	 * 重载实现测量时方法。
	 * @param widthMeasureSpec 测量宽度空间。
	 * @param heightMeasureSpec 测量长度空间。
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec)
	{
		// TODO: Implement this method
		
		//自身大小测量
		super.onMeasure(widthMeasureSpec,heightMeasureSpec);
		
		final int widthSize=MeasureSpec.getSize(widthMeasureSpec);
		final int heightSize=MeasureSpec.getSize(heightMeasureSpec);

		int maxChildWdith=0,maxChildHeight=0;

		final int childrenCount=getChildCount();

		//测量子view,第一层循环。
		for(int itr=0;itr < childrenCount;itr++)
		{
			View child=getChildAt(itr);
			if(child.getVisibility() != View.GONE)
			{
				ViewGroup.LayoutParams params=child.getLayoutParams();
				int childWdithSpec=0,childHeightSpec=0;
				switch(params.width)
				{
					case LayoutParams.WRAP_CONTENT:
						childWdithSpec = MeasureSpec.makeMeasureSpec(widthSize,MeasureSpec.AT_MOST);
						break;
					case LayoutParams.MATCH_PARENT:
						childWdithSpec = MeasureSpec.makeMeasureSpec(widthSize,MeasureSpec.AT_MOST);
						params.width = LayoutParams.WRAP_CONTENT;
						break;
					default:
						childWdithSpec = MeasureSpec.makeMeasureSpec(params.width,MeasureSpec.EXACTLY);
						break;
				}
				switch(params.height)
				{
					case LayoutParams.WRAP_CONTENT:
						childHeightSpec = MeasureSpec.makeMeasureSpec(heightSize,MeasureSpec.AT_MOST);
						break;
					case LayoutParams.MATCH_PARENT:
						childHeightSpec = MeasureSpec.makeMeasureSpec(heightSize,MeasureSpec.AT_MOST);
						params.height = LayoutParams.WRAP_CONTENT;
						break;
					default:
						childHeightSpec = MeasureSpec.makeMeasureSpec(params.height,MeasureSpec.EXACTLY);
						break;
				}

				measureChild(child,childWdithSpec,childHeightSpec);

				if(params instanceof MarginLayoutParams)
				{
					int marginTop=((MarginLayoutParams)params).topMargin;
					int marginBottom=((MarginLayoutParams)params).bottomMargin;
					int marginLeft=((MarginLayoutParams)params).leftMargin;
					int marginRight=((MarginLayoutParams)params).rightMargin;

					maxChildWdith = Math.max(child.getMeasuredWidth() + marginLeft + marginRight,maxChildWdith);
					maxChildHeight = Math.max(child.getMeasuredHeight() + marginTop + marginBottom,maxChildHeight);
				}
				else
				{
					maxChildWdith = Math.max(child.getMeasuredWidth(),maxChildWdith);
					maxChildHeight = Math.max(child.getMeasuredHeight(),maxChildHeight);
				}
			}
		}

		//矩阵,长度计算,一层循环
		if(fieldColumnCount * fieldRowCount < getChildCount())
		{
			if(fieldColumnCount >= fieldRowCount)
			{
				while(true)
				{
					fieldColumnCount++;
					if(fieldColumnCount * fieldRowCount >= getChildCount())
					{
						break;
					}
					fieldColumnCount--;
					fieldRowCount++;
					if(fieldColumnCount * fieldRowCount >= getChildCount())
					{
						break;
					}
					fieldColumnCount++;
					if(fieldColumnCount * fieldRowCount >= getChildCount())
					{
						break;
					}
				}
			}
			else
			{
				while(true)
				{
					fieldRowCount++;
					if(fieldColumnCount * fieldRowCount >= getChildCount())
					{
						break;
					}
					fieldRowCount--;
					fieldColumnCount++;
					if(fieldColumnCount * fieldRowCount >= getChildCount())
					{
						break;
					}
					fieldRowCount++;
					if(fieldColumnCount * fieldRowCount >= getChildCount())
					{
						break;
					}
				}
			}
		}

		unitWidth = maxChildWdith;
		unitHeight = maxChildHeight;
	}
	
	/**
	 * 重写布局时方法。
	 * @param changed 改变时为true。
	 * @param left 左部边长。
	 * @param top 顶部边长。
	 * @param right 右部边长。
	 * @param bottom 底部边长。
	 */
	@Override
	protected void onLayout(boolean changed,int left,int top,int right,int bottom)
	{
		if(changed)
		{
			// TODO: Implement this method
			final int childrenCount=getChildCount();

			//子视图位置表重置
			resetViewLocationTable();
			
			//一层循环
			for(int itr=0;itr < childrenCount;itr++)
			{
				View child=getChildAt(itr);
				if(child.getVisibility() != View.GONE && checkLayoutParams(child.getLayoutParams()))
				{
					LayoutParams params=(LayoutParams)child.getLayoutParams();
					//x为c y为r
					//-1为未定位
					int x=-1,y=-1;
					if((params.fieldColumnLay <= fieldColumnCount && params.fieldColumnLay > 0) && (params.fieldRowLay > 0 && params.fieldRowLay <= fieldRowCount))
					{
						x = params.fieldColumnLay - 1;
						y = params.fieldRowLay - 1;
						if(viewLocationTable[x][y])
						{
							x = -1;
							y = -1;
						}
						else
						{
							viewLocationTable[x][y] = true;
						}
					}
					else if(params.fieldColumnLay <= fieldColumnCount && params.fieldColumnLay > 0)
					{
						x = params.fieldColumnLay - 1;

						//二层循环
						for(int itr1=0;itr1 < fieldRowCount;itr1++)
						{
							if(!viewLocationTable[x][itr1])
							{
								y = itr1;
								viewLocationTable[x][y] = true;
								break;
							}
						}

						if(y == -1)
						{
							x = -1;
						}
					}
					else if(params.fieldRowLay > 0 && params.fieldRowLay <= fieldRowCount)
					{
						y = params.fieldRowLay - 1;

						//二层循环
						for(int itr1=0;itr1 < fieldColumnCount;itr1++)
						{
							if(!viewLocationTable[itr1][y])
							{
								x = itr1;
								viewLocationTable[x][y] = true;
								break;
							}
						}

						if(x == -1)
						{
							y = -1;
						}
					}

					final int viewWidth=child.getMeasuredWidth();
					final int viewHeight=child.getMeasuredHeight();

					//自寻址方法
					if(x == -1)
					{
						boolean isStop=false;

						if(fieldColumnCount >= fieldRowCount)
						{
							//二层循环
							for(int itr1 = 0;itr1 < fieldColumnCount;itr1++)
							{
								if(isStop)
								{
									break;
								}

								//三层循环
								for(int itr2=0;itr2 < fieldRowCount;itr2++)
								{
									if(!viewLocationTable[itr1][itr2])
									{
										x = itr1;
										y = itr2;
										viewLocationTable[x][y] = true;
										isStop = true;
										break;
									}
								}
							}
						}
						else
						{
							//二层循环
							for(int itr1 = 0;itr1 < fieldRowCount;itr1++)
							{
								if(isStop)
								{
									break;
								}

								//三层循环
								for(int itr2=0;itr2 < fieldColumnCount;itr2++)
								{
									if(!viewLocationTable[itr1][itr2])
									{
										y = itr1;
										x = itr2;
										viewLocationTable[x][y] = true;
										isStop = true;
										break;
									}
								}
							}
						}
					}
					
					//单元格位置
					final int columnLength=x * unitWidth;
					final int rowLength=y * unitHeight;
					//Offset 偏移量位置
					final int columnOffset=(unitWidth - viewWidth) / 2;
					final int rowOffset=(unitHeight - viewHeight) / 2;
					child.layout(left + columnLength + columnOffset,top + rowLength + rowOffset,left + columnLength + columnOffset + viewWidth,top + rowLength + rowOffset + viewHeight);
				}
			}
			border[0]=left;
			border[1]=top;
			border[2]=left+unitWidth*fieldColumnCount;
			border[3]=top+unitHeight*fieldRowCount;
		}
	}
	
	/**
	 * 重载构造布局参数对象方法。xml文件解析时调用。
	 * @param attrs 解析时得到的属性集对象。
	 * @return 分析构造出来的布局参数对象。
	 */
	@Override
	public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs)
	{
		// TODO: Implement this method
		return new LayoutParams(getContext(),attrs);
	}

	/**
	 * 重载检查布局参数类型的方法。
	 * @param params 待判断的布局参数对象。
	 * @return true为属于内部类的布局参数对象。false为不属于。
	 */
	@Override
	protected boolean checkLayoutParams(ViewGroup.LayoutParams params)
	{
		// TODO: Implement this method
		return params instanceof LayoutParams ? true: false;
	}
	
	/**
	 * 重写事件拦截方法。
	 * @param event 触摸事件对象。
	 * @return true代表事件拦截给本view处理。
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event)
	{
		// TODO: Implement this method
		switch(event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				downLocation[0]=event.getRawX();
				downLocation[1]=event.getRawY();
				lastMoveLocation[0]=downLocation[0];
				lastMoveLocation[1]=downLocation[1];
				break;
			case MotionEvent.ACTION_MOVE:
				moveLocation[0]=event.getRawX();
				moveLocation[1]=event.getRawY();
				lastMoveLocation[0]=moveLocation[0];
				lastMoveLocation[1]=moveLocation[1];
				float diffX=Math.abs(moveLocation[0]-downLocation[0]);
				float diffY=Math.abs(moveLocation[1]-downLocation[1]);
				if(diffX > mixTouchSlop || diffY > mixTouchSlop)
				{
					return true;
				}
				break;
		}
		return super.onInterceptTouchEvent(event);
	}


	/**
	 * 重写事件处理方法。
	 * @param event 触摸事件对象。
	 * @return true代表事件已处理。
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		// TODO: Implement this method
		switch(event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				super.onTouchEvent(event);
				return true;
			case MotionEvent.ACTION_MOVE:
				moveLocation[0]=event.getRawX();
				moveLocation[1]=event.getRawY();
				int scrolledX=(int)(lastMoveLocation[0]-moveLocation[0]);
				int scrolledY=(int)(lastMoveLocation[1]-moveLocation[1]);
				if(getScrollX() + scrolledX < border[0])
				{
					if(getScrollY() + scrolledY < border[1])
					{
						scrollTo(border[0],border[1]);
						return true;
					}
					else if(getScrollY() + scrolledY + getHeight() > border[3])
					{
						scrollTo(border[0],border[3]-getHeight());
						return true;
					}
					
					scrollTo(border[0],getScrollY());
					scrollBy(0,scrolledY);
					return true;
				}
				else if(getScrollX() + scrolledX + getWidth() > border[2])
				{
					if(getScrollY() + scrolledY < border[1])
					{
						scrollTo(border[2]-getWidth(),border[1]);
						return true;
					}
					else if(getScrollY() + scrolledY + getHeight() > border[3])
					{
						scrollTo(border[2]-getWidth(),border[3]-getHeight());
						return true;
					}

					scrollTo(border[2]-getWidth(),getScrollY());
					scrollBy(0,scrolledY);
					return true;
				}
				else if(getScrollY() + scrolledY < border[1])
				{
					scrollTo(getScrollX(),border[1]);
					scrollBy(scrolledX,0);
					return true;
				}
				else if(getScrollY() + scrolledY + getHeight() > border[3])
				{
					scrollTo(getScrollX(),border[3]-getHeight());
					scrollBy(scrolledX,0);
					return true;
				}
				scrollBy(scrolledX,scrolledY);
				lastMoveLocation[0]=moveLocation[0];
				lastMoveLocation[1]=moveLocation[1];
				super.onTouchEvent(event);
				return true;
		}
		return super.onTouchEvent(event);
	}
	
	//静态内部类
	
	/**
	 * 继承于ViewGroup.MarginLayoutParams的布局参数类。
	 */
	public static class LayoutParams extends ViewGroup.MarginLayoutParams
	{
		/**
		 * 子视图所位于的田块布局的柱数。0为系统安排。
		 */
		public int fieldColumnLay=0;

		/**
		 * 子视图所位于的田块布局的行数。0为系统安排。
		 */
		public int fieldRowLay=0;

		/**
		 * 用一个ViewGroup.LayoutParams的对象来构造布局对象。
		 * @param source 原来ViewGroup.LayoutParams的布局对象。
		 */
		public LayoutParams(ViewGroup.LayoutParams source)
		{
			super(source);
		}

		/**
		 * 用一个ViewGroup.MarginLayoutParams的对象来构造布局对象。
		 * @param source 原来ViewGroup.MarginLayoutParams的对象。
		 */
		public LayoutParams(ViewGroup.MarginLayoutParams source)
		{
			super(source);
		}

		/**
		 * 用自身类的对象再构造一个布局对象。（拷贝）
		 * @param source 原来的对象。
		 */
		public LayoutParams(LayoutParams source)
		{
            super(source);
			this.fieldColumnLay=source.fieldColumnLay;
			this.fieldRowLay=source.fieldRowLay;
        }

		/**
		 * 用各种属性值构造布局对象。
		 * @param width 视图宽度。
		 * @param height 视图高度。
		 * @param fieldColumnLay 田块布局柱数。
		 * @param fieldRowLay 田块布局行数。
		 */
		public LayoutParams(int width,int height,int fieldColumnLay,int fieldRowLay)
		{
			super(width,height);
			
			//不允许match_parent
            if(width == LayoutParams.MATCH_PARENT)
			{
				this.width = LayoutParams.WRAP_CONTENT;
			}
			
			if(height == LayoutParams.MATCH_PARENT)
			{
				this.height = LayoutParams.WRAP_CONTENT;
			}

			//0为系统测量
			if(fieldRowLay < 0)
			{
				fieldRowLay = 0;
			}
			this.fieldRowLay = fieldRowLay;

			if(fieldColumnLay < 0)
			{
				fieldColumnLay = 0;
			}
			this.fieldColumnLay = fieldColumnLay;
        }

		/**
		 * 用内容对象和属性集对象构造布局对象。
		 * @param context 系统提供的内容对象。
		 * @param attrs 系统提供的属性集对象。
		 */
		public LayoutParams(Context context,AttributeSet attrs)
		{
            super(context,attrs);

            TypedArray subAttrSet = context.obtainStyledAttributes(attrs,R.styleable.fieldBlockLayoutParams);
			fieldColumnLay = subAttrSet.getInt(R.styleable.fieldBlockLayoutParams_fieldColumnLay,0);
			//0为系统测量
			if(fieldColumnLay < 0)
			{
				fieldColumnLay = 0;
			}
			fieldRowLay = subAttrSet.getInt(R.styleable.scrollable_gridlayout_rowLayout,0);
			if(fieldRowLay < 0)
			{
				fieldRowLay = 0;
			}
            subAttrSet.recycle();
        }
	}
}
