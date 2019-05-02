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
import android.view.ViewGroup;
import com.mycompany.myapp.R;

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
	
	//构造方法
	
	/**
	 * 初始化属性及自定义属性。
	 * @param context 系统给的内容对象。
	 * @param attrs 系统给的属性集对象。
	 */
	public FieldBlockLayout(Context context,AttributeSet attrs)
	{
		//调用原来父类的构造方法
		super(context,attrs);

		//检查自定义参数
		TypedArray subAttrSet=context.obtainStyledAttributes(attrs,R.styleable.fieldBlockLayout);
		final int setMemberCount=subAttrSet.getIndexCount();
		for(int itr=0;itr < setMemberCount;itr++)
		{
			//0为现场测量
			switch(subAttrSet.getIndex(itr))
			{
				case R.styleable.fieldBlockLayout_fieldColumnCount:
					fieldColumnCount=subAttrSet.getInt(R.styleable.fieldBlockLayout_fieldColumnCount,0);
					if(fieldColumnCount<0)
					{
						fieldColumnCount=0;
					}
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
		
	}

	@Override
	protected void onLayout(boolean p1,int p2,int p3,int p4,int p5)
	{
		// TODO: Implement this method
	}
}
