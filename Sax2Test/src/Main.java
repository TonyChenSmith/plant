//字体配置表分析测试
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

public class Main
{
	private static int SDK_INT;

	private static final Map<String,String[]> FONTS_PATHS=new HashMap<>();

	private static final String FONTS_DIR="/system/fonts/";

	public static void main(String[] args)
	{
		System.out.println("Set SDK_INT:");
		System.out.print("SDK_INT=");
		SDK_INT = new Scanner(System.in).nextInt();
		parseSystemFontsConfig();
		System.out.println("End");
		for (String key : FONTS_PATHS.keySet())
		{
			System.out.println(key + '=' + Arrays.toString(FONTS_PATHS.get(key)));
		}
	}

	private static String[] getSystemFontsConfigFileName()
	{
		if (SDK_INT <= 19)
		{
			return new String[]{"/system/etc/system_fonts.xml","/system/etc/fallback_fonts.xml"};
		}
		else if (SDK_INT <= 23)
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
}



/**
 * 对于一些字体的键的处理方法集合。耦合性高，需要本地编写。
 * @author Tony Chen Smith
 */
final class FontKey
{
	private FontKey(){}

	//19系

	//使单词首字母大写，成为大驼峰，输入为扫描出的字符串。
	protected static String turnBigHumpName19(String scanStr,boolean isFileName)
	{
		scanStr=scanStr.trim();
		if(isFileName)
		{
			char scan[]=scanStr.toCharArray();
			int mark=0;
			for(int index=scan.length-1;index>=0;index--)
			{
				if(scan[index]=='.')
				{
					mark=index;
				}

				if(scan[index]=='-')
				{
					mark=index;
					break;
				}
			}

			return new String(scan,0,mark);
		}
		else
		{
			StringBuilder builder = new StringBuilder();

			char scan[]=scanStr.toCharArray();
			int index=0;
			if (scanStr.startsWith("sans-serif"))
			{
				builder.append("SansSerif");
				index = 10;
			}

			for (int mark=-1;index < scan.length;index++)
			{
				if (Character.isLetter(scan[index]))
				{
					if (mark == -1)
					{
						mark = index;
					}

					if (index == scan.length - 1)
					{
						if (Character.isLowerCase(scan[mark]))
						{
							scan[mark] = Character.toUpperCase(scan[mark]);
						}
						builder.append(scan, mark, index - mark + 1);
						break;
					}
					continue;
				}
				else if (mark != -1)
				{
					if (Character.isLowerCase(scan[mark]))
					{
						scan[mark] = Character.toUpperCase(scan[mark]);
					}
					builder.append(scan, mark, index - mark);
					mark = -1;
				}
			}

			return builder.toString();
		}
	}

	//返回字体的风格，输入为扫描出的字符串。
	protected static char getFontStyle19(String scanStr)
	{
		scanStr=scanStr.trim().toLowerCase();
		if(scanStr.indexOf("italic")>=0)
		{
			return 'I';
		}
		else
		{
			return 'N';
		}
	}

	//获得形式变种，输入为属性对象。
	protected static char getFontVariant19(Attributes attrs)
	{
		if(attrs==null||attrs.getValue("variant")==null)
		{
			return 'N';
		}
		else
		{
			String value=attrs.getValue("variant");
			switch(value)
			{
				case "elegant":
					return 'E';
				case "compact":
					return 'C';
				default:
					return 'N';
			}
		}
	}

	//获得字体权重，输入为扫描字符串。
	protected static byte getFontWeight19(String scanStr)
	{
		scanStr=scanStr.trim().toLowerCase();
		if(scanStr.indexOf("thin")>=0)
		{
			return 1;
		}
		else if(scanStr.indexOf("extralight")>=0)
		{
			return 2;
		}
		else if(scanStr.indexOf("light")>=0)
		{
			return 3;
		}
		else if(scanStr.indexOf("normal")>=0||scanStr.indexOf("regular")>=0)
		{
			return 4;
		}
		else if(scanStr.indexOf("medium")>=0)
		{
			return 5;
		}
		else if(scanStr.indexOf("semibold")>=0)
		{
			return 6;
		}
		else if(scanStr.indexOf("extrabold")>=0)
		{
			return 8;
		}
		else if(scanStr.indexOf("bold")>=0)
		{
			return 7;
		}
		else if(scanStr.indexOf("black")>=0)
		{
			return 9;
		}
		else
		{
			return -1;
		}
	}

	//合成键
	protected static String makeKey(String name,char style,char variant,byte weight)
	{
		return new StringBuilder(name).append('-').append(style).append('-').append(variant).append('-').append(weight).toString();
	}

	//23系

	//继承19系方法
	protected static String turnBigHumpName23(String scanStr,boolean isFileName)
	{
		return FontKey.turnBigHumpName19(scanStr,isFileName);
	}

	//获得字体权重，输入为字体标签属性值。基本不可能返回-1。
	protected static byte getFontWeight23(Attributes attrs)
	{
		String weight=attrs.getValue("weight");
		if(weight==null)
		{
			return -1;
		}
		switch(weight)
		{
			case "100":
				return 1;
			case "200":
				return 2;
			case "300":
				return 3;
			case "400":
				return 4;
			case "500":
				return 5;
			case "600":
				return 6;
			case "700":
				return 7;
			case "800":
				return 8;
			case "900":
				return 9;
			default:
				return -1;
		}
	}

	//继承19系方法，参数为字体家族的属性集。
	protected static char getFontVariant23(Attributes attrs)
	{
		return getFontVariant19(attrs);
	}

	//获得风格，参数为字体的属性集。
	protected static char getFontStyle23(Attributes attrs)
	{
		String style=attrs.getValue("style");
		if(style.equals("italic"))
		{
			return 'I';
		}
		else
		{
			return 'N';
		}
	}

	//获得别名的名字
	protected static String getAliasName23(Attributes attrs)
	{
		return attrs.getValue("name");
	}

	//获得别名的指向（指针）
	protected static String getAliasPointer23(Attributes attrs)
	{
		return attrs.getValue("to");
	}

	//标准系
	//分析时用的搜索方法
	protected static List<String> searchFontKey(Set<String> keySet,String fontName,byte weight)
	{
		//正则生成
		StringBuilder ragex = new StringBuilder(fontName);
		ragex.append('-').append("[IN]").append('-').append("[CEN]").append('-');

		if(weight==-1)
		{
			ragex.append("[1-9]");
		}
		else
		{
			ragex.append(weight);
		}

		List<String> keyList=new LinkedList<>();

		//遍历
		Pattern pattern = Pattern.compile(ragex.toString());
		for(String key : keySet)
		{
			Matcher matcher=pattern.matcher(key);
			if(matcher.matches())
			{
				keyList.add(key);
			}
		}

		return keyList;
	}
}
