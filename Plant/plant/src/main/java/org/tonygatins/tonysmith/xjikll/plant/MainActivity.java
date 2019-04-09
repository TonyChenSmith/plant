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
package org.tonygatins.tonysmith.xjikll.plant;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import org.tonygatins.tonysmith.xjikll.plant.graphics.drawable.BackgroundDrawable;
import org.tonygatins.tonysmith.xjikll.plant.graphics.drawable.FieldBackgroundDrawable;
import org.tonygatins.tonysmith.xjikll.plant.process.TimingRefresher;

public class MainActivity extends AppCompatActivity
{
	private BackgroundDrawable background;

	private FieldBackgroundDrawable field;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		TimingRefresher.initTimingRefreaher(this);
        setContentView(R.layout.main);

		background = new BackgroundDrawable(BitmapFactory.decodeResource(getResources(),R.drawable.plant_background));
		LinearLayout mainLayout=(LinearLayout)findViewById(R.id.mainLinearLayout);
		mainLayout.setBackground(background);

		Events handler = new Events(mainLayout,getLayoutInflater().inflate(R.layout.control,mainLayout,false));

		mainLayout.setOnClickListener(handler);

		field = new FieldBackgroundDrawable(BitmapFactory.decodeResource(getResources(),R.drawable.field));
		findViewById(R.id.plantField1).setBackground(field);
		findViewById(R.id.plantField2).setBackground(new FieldBackgroundDrawable(field));
		findViewById(R.id.plantField3).setBackground(new FieldBackgroundDrawable(field));
		findViewById(R.id.plantField4).setBackground(new FieldBackgroundDrawable(field));
		findViewById(R.id.plantField5).setBackground(new FieldBackgroundDrawable(field));
		findViewById(R.id.plantField6).setBackground(new FieldBackgroundDrawable(field));

		findViewById(R.id.plantField1).setOnClickListener(handler);
		findViewById(R.id.plantField2).setOnClickListener(handler);
		findViewById(R.id.plantField3).setOnClickListener(handler);
		findViewById(R.id.plantField4).setOnClickListener(handler);
		findViewById(R.id.plantField5).setOnClickListener(handler);
		findViewById(R.id.plantField6).setOnClickListener(handler);
	}

	@Override
	protected void onDestroy()
	{
		// TODO: Implement this method
		super.onDestroy();
		TimingRefresher.stop();
		background.recycle();
		field.recycle();
	}

	private class Events implements OnClickListener
	{

		@Override
		public void onClick(View p1)
		{
			// TODO: Implement this method
			switch(p1.getId())
			{
				case R.id.plantField1:
				case R.id.plantField2:
				case R.id.plantField3:
				case R.id.plantField4:
				case R.id.plantField5:
				case R.id.plantField6:
					mainLayout.removeView(control);
					break;
				default:
					mainLayout.removeView(control);
					mainLayout.addView(control);
					break;
			}
		}

		private final LinearLayout mainLayout;

		private final View control;

		protected Events(LinearLayout mainLayout,View control)
		{
			this.mainLayout = mainLayout;
			this.control = control;
		}
	}
}
