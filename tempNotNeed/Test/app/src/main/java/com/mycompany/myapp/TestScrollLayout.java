package com.mycompany.myapp;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/*
 * 测试用的滑动组件。
 * 均宽 均长。
 */
public final class TestScrollLayout extends ViewGroup
{
	private int columnCount;

	private int rowCount;

	private int gridWidth=0;

	private int gridHeight=0;

	public TestScrollLayout(Context context,AttributeSet attrs)
	{
		super(context,attrs);
		
		TypedArray typearray=context.obtainStyledAttributes(attrs,R.styleable.scrollable_gridlayout);
		final int attrNumber=typearray.getIndexCount();
		for(int itr=0;itr < attrNumber;itr++)
		{
			//0为不受限
			switch(typearray.getIndex(itr))
			{
				case R.styleable.scrollable_gridlayout_columnCount:
					columnCount = typearray.getInt(R.styleable.scrollable_gridlayout_columnCount,0);
					if(columnCount < 0)
					{
						columnCount = 0;
					}
					break;
				case R.styleable.scrollable_gridlayout_rowCount:
					rowCount = typearray.getInt(R.styleable.scrollable_gridlayout_rowCount,0);
					if(rowCount < 0)
					{
						rowCount = 0;
					}
					break;
			}
		}
	}

	public TestScrollLayout(Context context)
	{
		super(context);
		rowCount = 0;
		columnCount = 0;
	}

	/*
	 * 布局方法。
	 */
	@Override
	protected void onLayout(boolean changed,int left,int top,int right,int bottom)
	{
		// TODO: Implement this method
		final int childrenNumber=getChildCount();

		//位置初始化
		final boolean[][] site=new boolean[columnCount][rowCount];
		for(int itr=0;itr < columnCount;itr++)
		{
			for(int itr1=0;itr1 < rowCount;itr1++)
			{
				site[itr][itr1] = false;
			}
		}

		for(int itr=0;itr < childrenNumber;itr++)
		{
			View child=getChildAt(itr);
			if(child.getVisibility() != View.GONE && checkLayoutParams(child.getLayoutParams()))
			{
				LayoutParams params=(LayoutParams)child.getLayoutParams();
				//x为c y为r
				int x=-1,y=-1;
				if((params.column <= columnCount && params.column > 0) && (params.row > 0 && params.row <= rowCount))
				{
					x = params.column - 1;
					y = params.row - 1;
					if(site[x][y])
					{
						x = -1;
						y = -1;
					}
					else
					{
						site[x][y] = true;
					}
				}
				else if(params.column <= columnCount && params.column > 0)
				{
					x = params.column - 1;
					for(int itr1=0;itr1 < rowCount;itr1++)
					{
						if(!site[x][itr1])
						{
							y = itr1;
							site[x][y] = true;
							break;
						}
					}

					if(y == -1)
					{
						x = -1;
					}
				}
				else if(params.row > 0 && params.row <= rowCount)
				{
					y = params.row - 1;
					for(int itr1=0;itr1 < columnCount;itr1++)
					{
						if(!site[itr1][y])
						{
							x = itr1;
							site[x][y] = true;
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

				if(x == -1)
				{
					boolean isStop=false;
					if(columnCount >= rowCount)
					{
						for(int itr1 = 0;itr1 < columnCount;itr1++)
						{
							if(isStop)
							{
								break;
							}
							for(int itr2=0;itr2 < rowCount;itr2++)
							{
								if(!site[itr1][itr2])
								{
									x = itr1;
									y = itr2;
									site[x][y] = true;
									isStop = true;
									break;
								}
							}
						}
					}
					else
					{
						for(int itr1 = 0;itr1 < rowCount;itr1++)
						{
							if(isStop)
							{
								break;
							}
							for(int itr2=0;itr2 < columnCount;itr2++)
							{
								if(!site[itr1][itr2])
								{
									y = itr1;
									x = itr2;
									site[x][y] = true;
									isStop = true;
									break;
								}
							}
						}
					}
				}

				final int columnLength=x * gridWidth;
				final int rowLength=y * gridHeight;
				//Offset 偏移量
				final int columnOffset=(gridWidth - viewWidth) / 2;
				final int rowOffset=(gridHeight - viewHeight) / 2;
				child.layout(columnLength + columnOffset,rowLength + rowOffset,columnLength + columnOffset + viewWidth,rowLength + rowOffset + viewHeight);
			}
		}
	}

	public void setRow(int row)
	{
		if(row < 0)
		{
			row = 0;
		}
		rowCount = row;
	}

	public void setColumn(int colunmn)
	{
		if(colunmn < 0)
		{
			colunmn = 0;
		}
		columnCount = colunmn;
	}

	public int getRow()
	{
		return rowCount;
	}

	public int getColunmn()
	{
		return columnCount;
	}

	/*
	 * 测量方法
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec)
	{
		// TODO: Implement this method
		super.onMeasure(widthMeasureSpec,heightMeasureSpec);
		final int widthSize=MeasureSpec.getSize(widthMeasureSpec);
		final int heightSize=MeasureSpec.getSize(heightMeasureSpec);

		int maxChildWdith=0,maxChildHeight=0;

		final int childrenNumber=getChildCount();

		//测量子view
		for(int itr=0;itr < childrenNumber;itr++)
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

		/*
		 * 矩阵,长度计算
		 */
		if(columnCount * rowCount < getChildCount())
		{
			if(columnCount >= rowCount)
			{
				while(true)
				{
					columnCount++;
					if(columnCount * rowCount >= getChildCount())
					{
						break;
					}
					columnCount--;
					rowCount++;
					if(columnCount * rowCount >= getChildCount())
					{
						break;
					}
					columnCount++;
					if(columnCount * rowCount >= getChildCount())
					{
						break;
					}
				}
			}
			else
			{
				while(true)
				{
					rowCount++;
					if(columnCount * rowCount >= getChildCount())
					{
						break;
					}
					rowCount--;
					columnCount++;
					if(columnCount * rowCount >= getChildCount())
					{
						break;
					}
					rowCount++;
					if(columnCount * rowCount >= getChildCount())
					{
						break;
					}
				}
			}
		}

		gridWidth = maxChildWdith;
		gridHeight = maxChildHeight;
	}

	/**
	 * 布局参数类
	 */
	public static class LayoutParams extends ViewGroup.MarginLayoutParams
	{
		public int column=0;

		public int row=0;

		public LayoutParams(ViewGroup.LayoutParams source)
		{
			super(source);
		}

		public LayoutParams(ViewGroup.MarginLayoutParams source)
		{
			super(source);
		}

		//self
		public LayoutParams(LayoutParams source)
		{
            super(source);
			this.column = source.column;
			this.row = source.row;
        }

		//代码式构造
		public LayoutParams(int width,int height,int column,int row)
		{
			super(width,height);
            if(width == LayoutParams.MATCH_PARENT)
			{
				super.width = LayoutParams.WRAP_CONTENT;
			}
			if(height == LayoutParams.MATCH_PARENT)
			{
				super.height = LayoutParams.WRAP_CONTENT;
			}

			if(row < 0)
			{
				row = 0;
			}
			this.row = row;

			if(column < 0)
			{
				column = 0;
			}
			this.column = column;
        }

		public LayoutParams(Context c,AttributeSet attrs)
		{
            super(c,attrs);

            TypedArray a = c.obtainStyledAttributes(attrs,R.styleable.scrollable_gridlayout);
			column = a.getInt(R.styleable.scrollable_gridlayout_columnLayout,0);
			if(column < 0)
			{
				column = 0;
			}
			row = a.getInt(R.styleable.scrollable_gridlayout_rowLayout,0);
			if(row < 0)
			{
				row = 0;
			}
            a.recycle();
        }
	}

	@Override
	public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs)
	{
		// TODO: Implement this method
		return new LayoutParams(getContext(),attrs);
	}

	@Override
	protected boolean checkLayoutParams(ViewGroup.LayoutParams p)
	{
		// TODO: Implement this method
		return p instanceof LayoutParams ? true: false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		// TODO: Implement this method
		if(event.getAction() == MotionEvent.ACTION_DOWN)
		{
			Log.i("Down","TSGL");
			return true;
		}
		if(event.getAction() == MotionEvent.ACTION_MOVE)
		{
			Log.i("Move","TSGL");
			return true;
		}
		else
		{
			Log.i("Unkown","TSGL");
			return true;
		}
	}
}
