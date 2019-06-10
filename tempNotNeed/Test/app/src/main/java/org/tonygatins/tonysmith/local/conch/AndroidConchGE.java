package org.tonygatins.tonysmith.local.conch;

import org.tonygatins.tonysmith.api.conch.TGConchGE;
import android.graphics.Canvas;
import android.graphics.Bitmap;

public final class AndroidConchGE extends TGConchGE.LocalConchGE
{
	private final Canvas background;
	
	private final Bitmap backgroundMap;
	
	public AndroidConchGE(String key,int backgroundWidth,int backgroundHeight)
	{
		if(key.equals("Conch GE"))
		{
			backgroundMap=Bitmap.createBitmap(backgroundWidth,backgroundHeight,Bitmap.Config.ARGB_8888);
			background=new Canvas(backgroundMap);
		}
		else
		{
			throw new IllegalAccessError("Conch GE API isn't allowed.");
		}
	}
	
	protected void test()
	{
		Canvas canvas = new Canvas();
		
	}

	@Override
	protected void nativeDrawARGB()
	{
		// TODO: Implement this method
	}
}
