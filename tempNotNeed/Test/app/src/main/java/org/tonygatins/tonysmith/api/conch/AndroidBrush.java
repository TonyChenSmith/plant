/**
 * MIT License
 *
 * Copyright (c) 2019 Tony Chen Smith
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
package org.tonygatins.tonysmith.api.conch;

import android.graphics.Paint;
import org.tonygatins.tonysmith.api.conch.resource.Brush;

/**
 * 安卓底层实现:画笔。
 * @author Tony Chen Smith
 */
final class AndroidBrush extends Brush<Paint>
{
	//本地的画笔对象。
	private final Paint nativeBrush;
	
	/**
	 * 构造一个画笔。
	 * (给主句柄类使用)
	 */
	protected AndroidBrush()
	{
		this.nativeBrush=new Paint();
	}
	
	/**
	 * 获得本地画笔对象。
	 * @return 唯一本地画笔对象。
	 */
	@Override
	protected Paint getBrush()
	{
		// TODO: Implement this method
		return nativeBrush;
	}
	
	void test(){
		
	}

	/**
	 * 设置笔刷。
	 * @param brush 笔刷对象。
	 */
	@Override
	public void set(Brush brush)
	{
		// TODO: Implement this method
		nativeBrush.set(((AndroidBrush)brush).nativeBrush);
	}
}
