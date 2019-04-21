package com.mycompany.myapp;

import android.content.Context;
import android.view.ViewGroup;
import android.util.AttributeSet;

/*
 * 测试用的滑动组件。
 */
public final class TestScrollLayout extends ViewGroup
{
	public TestScrollLayout(Context context,AttributeSet attrs)
	{
		super(context,attrs);
	}
	
	public TestScrollLayout(Context context)
	{
		super(context);
	}

	/*
	 * 布局方法。
	 */
	@Override
	protected void onLayout(boolean p1,int p2,int p3,int p4,int p5)
	{
		// TODO: Implement this method
	}
	
}
