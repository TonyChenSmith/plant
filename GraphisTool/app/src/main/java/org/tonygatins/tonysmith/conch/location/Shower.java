package org.tonygatins.tonysmith.conch.location;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import org.tonygatins.tonysmith.conch.R;
import android.view.KeyEvent;

/**
 * 主界面。显示引擎画面。
 * @author Tony Chen Smith.
 */
public class Shower extends AppCompatActivity
{
	//创建界面
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		setContentView(new ConchSurface(this));
    }

	//设置退出按钮
	@Override
	public boolean onKeyDown(int keyCode,KeyEvent event)
	{
		// TODO: Implement this method
		if(keyCode == KeyEvent.KEYCODE_BACK)
		{
			finish();
			return true;
		}
		return super.onKeyDown(keyCode,event);
	}
}
