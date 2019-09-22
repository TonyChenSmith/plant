package org.tonygatins.tonysmith.conch.resource;

import android.graphics.Typeface;
import android.os.Build;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.Attributes;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import org.xmlpull.v1.sax2.Driver;

/**
 * 资源：字体。
 * 由于实现问题，这里不会解耦本地代码，搬运至其他平台时需要重写。
 * 软加载字体，不需要时只保留路径。需要回收
 */
public final class ConchFont
{
	//字体资源对象
	private final Typeface resource;
	
	//字体家族
	private static final Map<String,ConchFont> FONTS_FAMILY=new HashMap<>();
	
	//字体文件路径
	private static final Map<String,String[]> FONTS_PATHS=new HashMap<>();
	
	//构造一个字体对象。
	private ConchFont(String key)
	{
		resource=Typeface.createFromFile(FONTS_PATHS.get(key)[0]);
		FONTS_FAMILY.put(key,this);
	}
	
	//下面耦合性很高
	
	//字体目录
	private final static String FONTS_DIR="/system/fonts/";
	
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
	
	//解析配置文件，key为以下格式：name-style-variant-weight
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
			//驱动程序
			XMLReader reader19 = XMLReaderFactory.createXMLReader("org.xmlpull.v1.sax2.Driver");
			reader19.setContentHandler(new FontsConfig19());
			reader19.parse(new InputSource(configPath));
		}
		catch(SAXException e)
		{
			throw new RuntimeException(e);
		}
		catch(IOException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	//解析接口 19实现
	private static class FontsConfig19 extends DefaultHandler
	{
		//元素内容
		private LinkedList<NamePair> names=new LinkedList<>();
		private LinkedList<String> preArray=new LinkedList<>();
		private String catchString;
		
		private boolean isPrepare = false,isFile =false,isName =false;
		
		//key四元素。
		private char variant,style;
		private byte weight;
		private String name;
		
		protected FontsConfig19(){}

		//开始分析元素
		@Override
		public void startElement(String uri,String localName,String qName,Attributes attributes)
		{
			// TODO: Implement this method
			switch(localName)
			{
				case "family":
					isPrepare=true;
					break;
				case "name":
					isName=true;
					break;
				case "file":
					isFile=true;
					variant=FontKey.getFontVariant19(attributes);
					break;
			}
		}
		
		//截取字符串
		@Override
		public void characters(char[] ch,int start,int length)
		{
			// TODO: Implement this method
			if(isFile||isName)
			{
				catchString=new String(ch,start,length);
			}
		}

		//结束分析元素
		@Override
		public void endElement(String uri,String localName,String qName)
		{
			// TODO: Implement this method
			switch(localName)
			{
				case "family":
					isPrepare=false;
					names.clear();
					break;
				case "name":
					isName=false;
					NamePair pair=new NamePair();
					pair.weight=FontKey.getFontWeight19(catchString);
					pair.name=FontKey.turnBigHumpName19(catchString,false);
					names.add(pair);
					break;
				case "file":
					isFile=false;
					
					preArray.add(FONTS_DIR.concat(catchString));
					
					//文件名键
					style=FontKey.getFontStyle19(catchString);
					byte tmpWeight=FontKey.getFontWeight19(catchString);
					if(tmpWeight==-1)
					{
						weight=4;
					}
					else
					{
						weight=tmpWeight;
					}
					name=FontKey.turnBigHumpName19(catchString,true);
					preArray.add(FontKey.makeKey(name,style,variant,weight));
					
					weight=tmpWeight;
					
					//别名
					for(NamePair everyName:names)
					{
						everyName.setWeight(weight);
						preArray.add(FontKey.makeKey(everyName.name,style,variant,everyName.weight));
					}
					
					//全注册
					String[] keySet=new String[preArray.size()];
					preArray.toArray(keySet);
					for(int index=1;index<keySet.length;index++)
					{
						FONTS_PATHS.put(keySet[index],keySet);
					}
					preArray.clear();
					break;
			}
		}
		
		private class NamePair
		{
			String name;
			byte weight=-1;
			
			protected void setWeight(byte tmpWeight)
			{
				if(tmpWeight==weight&&tmpWeight!=-1)
				{
					return;
				}
				else if(tmpWeight==-1)
				{
					if(weight!=-1)
					{
						return;
					}
					else
					{
						weight=4;
					}
				}
				else
				{
					weight=tmpWeight;
				}
			}
		}
	}
	
	static
	{
		parseSystemFontsConfig();
	}
}
