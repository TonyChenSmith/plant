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

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import org.tonygatins.tonysmith.xjikll.plant.graphics.drawable.BackgroundDrawable;
import org.tonygatins.tonysmith.xjikll.plant.graphics.drawable.FieldBackgroundDrawable;
import android.graphics.BitmapFactory;

public class MainActivity extends AppCompatActivity
{
	private BackgroundDrawable background;
	
	private FieldBackgroundDrawable fieldBackground;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		background=new BackgroundDrawable(BitmapFactory.decodeResource(getResources(),R.drawable.plant_background));
		findViewById(R.id.plantFieldBlock).setBackground(background);
		
		fieldBackground=new FieldBackgroundDrawable(BitmapFactory.decodeResource(getResources(),R.drawable.field));
		
		findViewById(R.id.plantField1).setBackground(new FieldBackgroundDrawable(fieldBackground));
		findViewById(R.id.plantField2).setBackground(new FieldBackgroundDrawable(fieldBackground));
		findViewById(R.id.plantField3).setBackground(new FieldBackgroundDrawable(fieldBackground));
		findViewById(R.id.plantField4).setBackground(new FieldBackgroundDrawable(fieldBackground));
		
		findViewById(R.id.plantField5).setBackground(new FieldBackgroundDrawable(fieldBackground));
		findViewById(R.id.plantField6).setBackground(new FieldBackgroundDrawable(fieldBackground));
		findViewById(R.id.plantField7).setBackground(new FieldBackgroundDrawable(fieldBackground));
		findViewById(R.id.plantField8).setBackground(new FieldBackgroundDrawable(fieldBackground));
		
		findViewById(R.id.plantField9).setBackground(new FieldBackgroundDrawable(fieldBackground));
		findViewById(R.id.plantField10).setBackground(new FieldBackgroundDrawable(fieldBackground));
		findViewById(R.id.plantField11).setBackground(new FieldBackgroundDrawable(fieldBackground));
		findViewById(R.id.plantField12).setBackground(new FieldBackgroundDrawable(fieldBackground));

		findViewById(R.id.plantField13).setBackground(new FieldBackgroundDrawable(fieldBackground));
		findViewById(R.id.plantField14).setBackground(new FieldBackgroundDrawable(fieldBackground));
		findViewById(R.id.plantField15).setBackground(new FieldBackgroundDrawable(fieldBackground));
		findViewById(R.id.plantField16).setBackground(new FieldBackgroundDrawable(fieldBackground));
	}

	@Override
	protected void onDestroy()
	{
		// TODO: Implement this method
		super.onDestroy();
		background.recycle();
		fieldBackground.recycle();
	}

	private class Events implements OnClickListener
	{

		@Override
		public void onClick(View p1)
		{
			// TODO: Implement this method
		}

		protected Events()
		{
		}
	}
}
