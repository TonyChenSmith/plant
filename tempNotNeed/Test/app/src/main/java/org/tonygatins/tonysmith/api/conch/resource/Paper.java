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
package org.tonygatins.tonysmith.api.conch.resource;

/**
 * 引擎绘制资源：纸（绘制颜色的载体）。
 * @author Tony Chen Smith
 */
public abstract class Paper<T>
{
	/**
	 * 设置纸。
	 * @param width 纸的宽度。
	 * @param height 纸的高度。
	 * @return true为成功设置，false为设置失败。
	 */
	public abstract boolean setPaper(int width,int height);
	
	/**
	 * 设置纸。该方法性质为设置大小深拷贝。
	 * @param parent 父辈纸资源对象。
	 * @return true为成功设置，false为设置失败。
	 */
	public abstract boolean setPaper(Paper parent);
	
	/**
	 * 获得纸（本地资源对象）。
	 * @return 本地的资源对象。
	 */
	protected abstract T getPaper();
	
	/**
	 * 获得纸张宽度。
	 * @return 纸张的宽度。
	 */
	public abstract int getPaperWidth();
	
	/**
	 * 获得纸张的长度（高度）。
	 * @return 纸张的长度(高度）。
	 */
	public abstract int getPaperHeight();
}
