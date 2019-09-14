package org.tonygatins.tonysmith.conch.resource;

import android.graphics.Typeface;
import java.util.HashMap;
import java.util.Map;
import android.os.Build;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;

/**
 * 资源：字体。
 * 由于实现问题，这里不会解耦本地代码，搬运至其他平台时需要重写。
 * 软加载字体，不需要时只保留路径。
 */
public final class ConchFont
{
	//字体资源对象
	private Typeface resource;
	
	//字体家族
	private static final Map<String,ConchFont> FONTS_FAMILY=new HashMap<>();
	
	//字体文件路径
	private static final Map<String,String> FONTS_PATHS=new HashMap<>();
	
	//是否为系统自带
	private boolean isSystemInclude;
	
	//下面耦合性很高
	
	private static final String FONTS_DIR="/system/fonts";
	
	//获得安卓配置文件地址
	private static String[] getSystemFontsConfigFileName()
	{
		if(Build.VERSION.SDK_INT<=19)
		{
			return new String[]{"/system/etc/system_fonts.xml","/system/etc/fallback_fonts.xml"};
		}
		else if(Build.VERSION.SDK_INT<=23)
		{
			return new String[]{"/system/etc/system_fonts.xml","/system/etc/fallback_fonts.xml","/system/etc/fonts.xml"};
		}
		else
		{
			return new String[]{"/system/etc/fonts.xml"};
		}
	}
	
	//解析配置文件，key为以下格式：(name)？-style-weight
	//weight为数字，如果有别名也会记录，没有样式的话为Nomarl，sans-serif会改为SansSerif，variant在style最后，用_联接，没有则为Normal
	private static void parseSystemFontsConfig()
	{
		String configs[]=getSystemFontsConfigFileName();
		
		switch(configs.length)
		{
			case 1:
				break;
			case 2:
				parseSystemFontsConfig19(configs[0]);
				parseSystemFontsConfig19(configs[1]);
				break;
			case 3:
				parseSystemFontsConfig19(configs[0]);
				parseSystemFontsConfig19(configs[1]);
				break;
		}
	}
	
	//旧设备配置文件方法
	private static void parseSystemFontsConfig19(String configPath)
	{
		try
		{
			XMLReader reader19 = XMLReaderFactory.createXMLReader();
			reader19.setContentHandler(new FontsConfig19());
		}
		catch(SAXException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	//解析接口 19实现
	private static class FontsConfig19 extends DefaultHandler
	{
		protected FontsConfig19(){}
	}
}
