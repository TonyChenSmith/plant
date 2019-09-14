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
	private static final Map<String,String> FONTS_PATHS=new HashMap<>();
	
	//构造一个字体对象。
	private ConchFont(String key)
	{
		resource=Typeface.createFromFile(FONTS_PATHS.get(key));
		FONTS_FAMILY.put(key,this);
	}
	
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
		private List<String> alias = new LinkedList<>();
		private List<String> fileName = new LinkedList<>();
		private List<String> fixedAlias = new LinkedList<>();
		private List<String> fixedKey = new LinkedList<>();
		private String variant="";
		
		//是否到字体家族，名字集，文件集。
		private boolean isStartFontFamily=false,isStartNameSet=false,isStartFileSet=false;
		
		protected FontsConfig19()
		{}

		//开始分析元素
		@Override
		public void startElement(String uri,String localName,String qName,Attributes attributes)
		{
			// TODO: Implement this method
			if(localName.equals("family"))
			{
				isStartFontFamily=true;
			}
			
			if(localName.equals("name")&&isStartFontFamily)
			{
				isStartNameSet=true;
			}
			
			if(localName.equals("file")&&isStartFontFamily)
			{
				isStartFileSet=true;
				if(attributes.getLength()!=0)
				{
					variant=attributes.getValue(0).concat("_");
				}
			}
		}
		
		//截取字符串
		@Override
		public void characters(char[] ch,int start,int length)
		{
			// TODO: Implement this method
			if(isStartNameSet)
			{
				alias.add(new String(ch,start,length));
			}
			
			if(isStartFileSet)
			{
				fileName.add(variant.concat(new String(ch,start,length)));
				variant="";
			}
		}

		//结束分析元素
		@Override
		public void endElement(String uri,String localName,String qName)
		{
			// TODO: Implement this method
			if(localName.equals("family"))
			{
				isStartFontFamily=false;
				
				//别名处理
				if(alias.size()!=0)
				{
					for(String simple : alias)
					{
						int index=simple.indexOf("sans-serif");
						if(index>=0)
						{
							fixedAlias.add("SansSerif");
							continue;
						}
						
						int index1 = 0;
						boolean isStart = false;
						StringBuilder newAilas = new StringBuilder(simple.length());
						char temp[]=simple.toCharArray();
						for(int tempItr=0;tempItr<temp.length;tempItr++)
						{
							if(Character.isLetter(temp[tempItr]))
							{
								if(!isStart)
								{
									isStart=!isStart;
									index1=tempItr;
								}
								
								if(tempItr==temp.length-1)
								{
									if(Character.isLowerCase(temp[index1]))
									{
										temp[index1]=(char)(temp[index1]-32);
									}
									newAilas.append(new String(temp,index1,tempItr-index1+1));
									isStart=!isStart;
								}
							}
							
							if(Character.isSpace(temp[tempItr])&&isStart)
							{
								if(Character.isLowerCase(temp[index1]))
								{
									temp[index1]=(char)(temp[index1]-32);
								}
								newAilas.append(new String(temp,index1,tempItr-index1));
								isStart=!isStart;
							}
						}
					}
				}
			}

			if(localName.equals("name")&&isStartFontFamily)
			{
				isStartNameSet=false;
			}

			if(localName.equals("file")&&isStartFontFamily)
			{
				isStartFileSet=false;
			}
		}
	}
}
