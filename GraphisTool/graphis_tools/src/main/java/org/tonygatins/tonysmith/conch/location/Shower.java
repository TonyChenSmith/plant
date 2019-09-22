package org.tonygatins.tonysmith.conch.location;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;

/**
 * 主界面。显示引擎画面。
 * @author Tony Chen Smith.
 */
public class Shower extends AppCompatActivity
{
	//读取请求码
	private static final int REQUEDT_READING = 0;
	
	//创建界面
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		setContentView(new ConchSurface(this));
		
		//动态权限
		if(Build.VERSION.SDK_INT>=23)
		{
			int checkRead=ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE);
			if(checkRead!=PackageManager.PERMISSION_GRANTED)
			{
				ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEDT_READING);
			}
		}
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

	//许可请求回调
	@Override
	public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults)
	{
		// TODO: Implement this method
		if(requestCode==REQUEDT_READING)
		{
			if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
			{
				return;
			}
			else
			{
				Toast.makeText(this,"Can't read sd card,finish running.",Toast.LENGTH_SHORT).show();
				finish();
				return;
			}
		}
		super.onRequestPermissionsResult(requestCode,permissions,grantResults);
	}
}
