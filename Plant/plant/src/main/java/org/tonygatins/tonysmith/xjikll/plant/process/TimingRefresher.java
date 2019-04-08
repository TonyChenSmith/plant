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
package org.tonygatins.tonysmith.xjikll.plant.process;

import android.app.Activity;
import android.view.View;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.tonygatins.tonysmith.xjikll.plant.graphics.drawable.FieldBackgroundDrawable;
import java.util.concurrent.ScheduledFuture;

/**
 * 定时刷新器。不允许扩展。
 * @author Tony Chen Smith
 */
public final class TimingRefresher
{
	//设置线程池，线程数量3+cpu数
	private static final ScheduledExecutorService THREAD_POOL=Executors.newScheduledThreadPool(3 + Runtime.getRuntime().availableProcessors());

	//禁用构造方法
	private TimingRefresher()
	{}

	//ui句柄
	private static Activity context;

	/**
	 * 初始化定时刷新器。
	 * @param context UI线程句柄。
	 */
	public static void initTimingRefreaher(Activity context)
	{
		TimingRefresher.context = Objects.requireNonNull(context,"UI TimingRefresher can't initiate.");
	}

	/**
	 * 提交刷新任务。
	 * @param 需要刷新的视图对象。
	 */
	public static ScheduledFuture submitInvalidate(final View view)
	{
		Objects.requireNonNull(context,"UI TimingRefresher isn't initiated.");
		return THREAD_POOL.schedule(new Runnable(){
			@Override
			public void run()
			{
				// TODO: Implement this method
				context.runOnUiThread(new Runnable(){
					@Override
					public void run()
					{
						// TODO: Implement this method
						view.invalidate();
					}
				});
			}
		},1,TimeUnit.SECONDS);
	}

	/**
	 * 停止线程池。
	 */
	public static void stop()
	{
		THREAD_POOL.shutdown();
	}
}
