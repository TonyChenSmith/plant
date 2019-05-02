package com.mycompany.myapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.MotionEvent;
import android.widget.TextView;
import android.view.ViewGroup;

public class MainActivity extends Activity
{
	TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		text=(TextView)findViewById(R.id.mainTextView1);
    }

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		// TODO: Implement this method
		if(event.getAction()==MotionEvent.ACTION_DOWN)
		{
			text.setText("Down");
			return super.onTouchEvent(event);
		}
		if(event.getAction()==MotionEvent.ACTION_MOVE)
		{
			text.setText("Move");
			//findViewById(R.id.mainLinearLayout1).onTouchEvent(event);
			return super.onTouchEvent(event);
		}
		else
		{
			text.setText("Unkown");
			return super.onTouchEvent(event);
		}
	}
}
