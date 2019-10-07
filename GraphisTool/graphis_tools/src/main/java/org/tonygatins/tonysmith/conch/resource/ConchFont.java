package org.tonygatins.tonysmith.conch.resource;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Build;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * 资源：字体。
 * 由于实现问题，这里不会解耦本地代码，搬运至其他平台时需要重写。
 * 软加载字体，不需要时只保留路径。该平台不需要回收。
 * @author Tony Chen Smith
 */
public final class ConchFont
{
	//字体资源对象
	private final Typeface resource;
	
	//字体四属性
	private final String[] names;
	private final ConchFontAttribute.Style fontStyle;
	private final ConchFontAttribute.Weight fontWeight;
	private final ConchFontAttribute.Variant fontVariant;
	
	//字体家族
	private static final Map<String,ConchFont> FONTS_FAMILY=new HashMap<>();
	
	//字体文件路径
	private static final Map<String,String[]> FONTS_PATHS=new HashMap<>();
	
	//通过键构造一个字体对象。
	private ConchFont(String key)
	{
		String[] keyArray=FONTS_PATHS.get(key);
		resource=Typeface.createFromFile(keyArray[0]);
		
		//属性拆分部分
		Pattern pattern=Pattern.compile("([A-Za-z]+)-([IN])-([CIN])-([1-9])");
		names=new String[keyArray.length-1];
		
		Matcher mainMatcher=pattern.matcher(key);
		mainMatcher.find();
		
		fontStyle=FontKey.toStyleObject(mainMatcher.group(2));
		fontVariant=FontKey.toVariantObject(mainMatcher.group(3));
		fontWeight=FontKey.toWeightObject(mainMatcher.group(4));
		
		for(int itr=1;itr<keyArray.length;itr++)
		{
			FONTS_FAMILY.put(keyArray[itr],this);
			Matcher subMatcher=pattern.matcher(keyArray[itr]);
			subMatcher.find();
			names[itr-1]=subMatcher.group(1);
		}
		
		Arrays.sort(names);
	}
	
	//文件创建
	private ConchFont(File file,String key)
	{
		resource=Typeface.createFromFile(file);
		
		Pattern pattern=Pattern.compile("([A-Za-z]+)-([IN])-([CIN])-([1-9])");
		names=new String[1];

		Matcher mainMatcher=pattern.matcher(key);
		mainMatcher.find();

		names[0]=mainMatcher.group(1);
		fontStyle=FontKey.toStyleObject(mainMatcher.group(2));
		fontVariant=FontKey.toVariantObject(mainMatcher.group(3));
		fontWeight=FontKey.toWeightObject(mainMatcher.group(4));
		FONTS_FAMILY.put(key,this);
	}
	
	//安卓资源文件创建
	private ConchFont(AssetManager am,String path,String key)
	{
		resource=Typeface.createFromAsset(am,path);

		Pattern pattern=Pattern.compile("([A-Za-z]+)-([IN])-([CIN])-([1-9])");
		names=new String[1];

		Matcher mainMatcher=pattern.matcher(key);
		mainMatcher.find();

		names[0]=mainMatcher.group(1);
		fontStyle=FontKey.toStyleObject(mainMatcher.group(2));
		fontVariant=FontKey.toVariantObject(mainMatcher.group(3));
		fontWeight=FontKey.toWeightObject(mainMatcher.group(4));
		
		FONTS_FAMILY.put(key,this);
	}
	
	//获得字体风格
	public ConchFontAttribute.Style getStyle()
	{
		return fontStyle;
	}
	
	//获得字体权重
	public ConchFontAttribute.Weight getWeight()
	{
		return fontWeight;
	}
	
	//获得字体形式
	public ConchFontAttribute.Variant getVariant()
	{
		return fontVariant;
	}
	
	//获得字体所有名字
	public String[] getAllName()
	{
		String[] result=new String[names.length];
		for(int itr=0;itr<names.length;itr++)
		{
			result[itr]=names[itr];
		}
		return result;
	}
	
	//回收自身(空)
	public void recycle()
	{
	}
	
	//返回第一个名字
	private String getFirstName()
	{
		return names[0];
	}
	
	//静态
	
	//回收所有字体资源
	public static void recycleAll()
	{
		FONTS_FAMILY.clear();
	}
	
	//销毁类
	public static void removeClass()
	{
		recycleAll();
		FONTS_PATHS.clear();
	}
	
	//检查是否销毁
	public static boolean isRemoved()
	{
		return FONTS_PATHS.isEmpty();
	}
	
	//通过字体名字构造字体。
	public static ConchFont create(String name)
	{
		return create(name,null,null,null);
	}

	//通过字体名字和权重构造字体
	public static ConchFont create(String name,ConchFontAttribute.Weight weight)
	{
		return create(name,null,null,weight);
	}
	
	//通过字体名字和形式构造字体。
	public static ConchFont create(String name,ConchFontAttribute.Variant variant)
	{
		return create(name,null,variant,null);
	}

	//通过字体名字和风格构造字体
	public static ConchFont create(String name,ConchFontAttribute.Style style)
	{
		return create(name,style,null,null);
	}
	
	//通过字体名字，风格和权重构造字体
	public static ConchFont create(String name,ConchFontAttribute.Style style,ConchFontAttribute.Weight weight)
	{
		return create(name,style,null,weight);
	}
	
	//通过字体名字，风格，形式和权重构造字体。名字是conch风格。
	public static ConchFont create(String name,ConchFontAttribute.Style style,ConchFontAttribute.Variant variant,ConchFontAttribute.Weight weight)
	{
		if(name==null||name.isEmpty())
		{
			return null;
		}
		
		char styleCode=FontKey.toStyleCode(style);
		char variantCode=FontKey.toVariantCode(variant);
		byte weightCode=FontKey.toWeightCode(weight);
		
		String key=FontKey.makeKey(name,styleCode,variantCode,weightCode);
		
		if(FONTS_FAMILY.get(key)!=null)
		{
			return FONTS_FAMILY.get(key);
		}
		else
		{
			if(FONTS_PATHS.get(key)!=null)
			{
				return new ConchFont(key);
			}
			else
			{
				return null;
			}
		}
	}
	
	//通过字体文件，字体名字，风格，形式和权重构造字体。名字是conch风格。
	public static ConchFont create(String fontFile,String name,ConchFontAttribute.Style style,ConchFontAttribute.Variant variant,ConchFontAttribute.Weight weight)
	{
		if(name==null||name.isEmpty()||fontFile==null||fontFile.isEmpty())
		{
			return null;
		}

		char styleCode=FontKey.toStyleCode(style);
		char variantCode=FontKey.toVariantCode(variant);
		byte weightCode=FontKey.toWeightCode(weight);

		String key=FontKey.makeKey(name,styleCode,variantCode,weightCode);

		if(FONTS_FAMILY.get(key)!=null)
		{
			return FONTS_FAMILY.get(key);
		}
		else
		{
			if(FONTS_PATHS.get(key)!=null)
			{
				return new ConchFont(key);
			}
			else
			{
				return new ConchFont(new File(fontFile),key);
			}
		}
	}
	
	//通过资源字体文件，字体名字，风格，形式和权重构造字体。名字是conch风格。安卓专用
	public static ConchFont create(AssetManager am,String fontFile,String name,ConchFontAttribute.Style style,ConchFontAttribute.Variant variant,ConchFontAttribute.Weight weight)
	{
		if(name==null||name.isEmpty()||fontFile==null||fontFile.isEmpty()||am==null)
		{
			return null;
		}

		char styleCode=FontKey.toStyleCode(style);
		char variantCode=FontKey.toVariantCode(variant);
		byte weightCode=FontKey.toWeightCode(weight);

		String key=FontKey.makeKey(name,styleCode,variantCode,weightCode);

		if(FONTS_FAMILY.get(key)!=null)
		{
			return FONTS_FAMILY.get(key);
		}
		else
		{
			if(FONTS_PATHS.get(key)!=null)
			{
				return new ConchFont(key);
			}
			else
			{
				return new ConchFont(am,fontFile,key);
			}
		}
	}
	
	//通过父字体与一些参数构造
	public static ConchFont create(ConchFont parent,ConchFontAttribute.Style style,ConchFontAttribute.Variant variant,ConchFontAttribute.Weight weight)
	{
		return create(parent.getFirstName(),style,variant,weight);
	}
	
	public static ConchFont create(ConchFont parent,ConchFontAttribute.Style style,ConchFontAttribute.Weight weight)
	{
		return create(parent.getFirstName(),style,null,weight);
	}
	
	public static ConchFont create(ConchFont parent,ConchFontAttribute.Style style)
	{
		return create(parent.getFirstName(),style,null,null);
	}
	
	public static ConchFont create(ConchFont parent,ConchFontAttribute.Variant variant)
	{
		return create(parent.getFirstName(),null,variant,null);
	}
	
	public static ConchFont create(ConchFont parent,ConchFontAttribute.Weight weight)
	{
		return create(parent.getFirstName(),null,null,weight);
	}
	
	//下面耦合性很高
	
	//安卓用的返回方法
	public Typeface returnFontForAndroid()
	{
		return resource;
	}
	
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
				parseSystemFontsConfig23(configs[0]);
				break;
			case 2:
				parseSystemFontsConfig19(configs[0]);
				parseSystemFontsConfig19(configs[1]);
				break;
			case 3:
				parseSystemFontsConfig23(configs[2]);
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
		private List<NamePair> names=new LinkedList<>();
		private List<String> preArray=new LinkedList<>();
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
					
					//反复写措施，即确保别名不被覆盖（由于执行原因，已经能确保之前的信息包括后面的信息）
					String fileKey=FontKey.makeKey(name,style,variant,weight);
					if(FONTS_PATHS.get(fileKey)!=null)
					{
						preArray.clear();
						break;
					}
					preArray.add(fileKey);
					
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
	
	//19后的通用方法。
	private static void parseSystemFontsConfig23(String configPath)
	{
		try
		{
			//驱动程序
			XMLReader reader23 = XMLReaderFactory.createXMLReader("org.xmlpull.v1.sax2.Driver");
			reader23.setContentHandler(new FontsConfig23());
			reader23.parse(new InputSource(configPath));
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
	
	//解析接口 23实现
	private static class FontsConfig23 extends DefaultHandler
	{
		private Map<String,List<String>> perMap2 = new HashMap<>();
		
		private List<List<String>> familyList;
		
		private String scanStr;
		
		private boolean isFont = false;
		
		//key四元素。
		private char variant,style;
		private byte weight;
		private String familyName,fileName;
		
		protected FontsConfig23(){}

		//开始分析元素
		@Override
		public void startElement(String uri,String localName,String qName,Attributes attributes) throws SAXException
		{
			// TODO: Implement this method
			switch(localName)
			{
				case "family":
					variant=FontKey.getFontVariant23(attributes);
					familyList=new LinkedList<>();
					if(attributes!=null)
					{
						familyName=attributes.getValue("name");
					}
					break;
				case "font":
					weight=FontKey.getFontWeight23(attributes);
					if(weight == -1)
					{
						throw new SAXException("/system/etc/fonts.xml is broken.");
					}
					style=FontKey.getFontStyle23(attributes);
					isFont=true;
					break;
				case "alias":
					familyName=FontKey.turnBigHumpName23(FontKey.getAliasName23(attributes),false);
					fileName=FontKey.turnBigHumpName23(FontKey.getAliasPointer23(attributes),false);
					weight=FontKey.getFontWeight23(attributes);
					List<String> searchList=FontKey.searchFontKey(perMap2.keySet(),fileName,weight);
					for(String key : searchList)
					{
						perMap2.get(key).add(familyName.concat(key.substring(key.indexOf("-"))));
					}
					break;
			}
		}

		//截取字符串
		@Override
		public void characters(char[] ch,int start,int length)
		{
			// TODO: Implement this method
			if(isFont)
			{
				scanStr=new String(ch,start,length);
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
					if(familyName!=null)
					{
						fileName=FontKey.turnBigHumpName23(familyName,false);
						for(List<String> fontList : familyList)
						{
							fontList.add(fileName.concat(fontList.get(1).substring(fontList.get(1).indexOf("-"))));
							for(int itr =1;itr<fontList.size();itr++)
							{
								perMap2.put(fontList.get(itr),fontList);
							}
						}
					}
					else
					{
						for(List<String> fontList : familyList)
						{
							perMap2.put(fontList.get(1),fontList);
						}
					}
					break;
				case "font":
					//存储
					List<String> entry=new LinkedList<>();
					
					//文件地址
					entry.add(FONTS_DIR.concat(scanStr));
					
					fileName=FontKey.turnBigHumpName23(scanStr,true);
					
					entry.add(FontKey.makeKey(fileName,style,variant,weight));
					
					familyList.add(entry);
					
					isFont=false;
					break;
				case "familyset":
					for(String key : perMap2.keySet())
					{
						List<String> member=perMap2.get(key);
						String[] perArray=new String[member.size()];
						member.toArray(perArray);
						
						for(int itr=1;itr<perArray.length;itr++)
						{
							FONTS_PATHS.put(perArray[itr],perArray);
						}
					}
					break;
			}
		}
	}
	
	static
	{
		parseSystemFontsConfig();
		
		SANS_SERIF=create("SansSerif");
		SERIF=create("Serif");
		MONOSPACE=create("Monospace");
	}
	
	//常用字体常量
	public static final ConchFont MONOSPACE;
	
	public static final ConchFont SANS_SERIF;
	
	public static final ConchFont SERIF;
}
