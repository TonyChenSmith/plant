//字体配置表分析测试
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;
import java.util.Arrays;

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

	private static void parseSystemFontsConfig()
	{
		String configs[]=getSystemFontsConfigFileName();

		switch (configs.length)
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

	private static void parseSystemFontsConfig19(String configPath)
	{
		try
		{
			//驱动程序
			XMLReader reader19 = XMLReaderFactory.createXMLReader("org.xmlpull.v1.sax2.Driver");
			reader19.setContentHandler(new FontsConfig19());
			reader19.parse(new InputSource(configPath));
		}
		catch (SAXException e)
		{
			throw new RuntimeException(e);
		}
		catch (IOException e)
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

		protected FontsConfig19()
		{}

		//开始分析元素
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes)
		{
			// TODO: Implement this method
			switch (localName)
			{
				case "family":
					isPrepare = true;
					break;
				case "name":
					isName = true;
					break;
				case "file":
					isFile = true;
					variant = FontKey.getFontVariant19(attributes);
					break;
			}
		}

		//截取字符串
		@Override
		public void characters(char[] ch, int start, int length)
		{
			// TODO: Implement this method
			if (isFile || isName)
			{
				catchString = new String(ch, start, length);
			}
		}

		//结束分析元素
		@Override
		public void endElement(String uri, String localName, String qName)
		{
			// TODO: Implement this method
			switch (localName)
			{
				case "family":
					isPrepare = false;
					names.clear();
					break;
				case "name":
					isName = false;
					NamePair pair=new NamePair();
					pair.weight = FontKey.getFontWeight19(catchString);
					pair.name = FontKey.turnBigHumpName19(catchString, false);
					names.add(pair);
					break;
				case "file":
					isFile = false;

					preArray.add(FONTS_DIR.concat(catchString));

					//文件名键
					style = FontKey.getFontStyle19(catchString);
					byte tmpWeight=FontKey.getFontWeight19(catchString);
					if (tmpWeight == -1)
					{
						weight = 4;
					}
					else
					{
						weight = tmpWeight;
					}
					name = FontKey.turnBigHumpName19(catchString, true);
					preArray.add(FontKey.makeKey(name, style, variant, weight));

					weight = tmpWeight;

					//别名
					for (NamePair everyName:names)
					{
						everyName.setWeight(weight);
						preArray.add(FontKey.makeKey(everyName.name, style, variant, everyName.weight));
					}

					//全注册
					String[] keySet=new String[preArray.size()];
					preArray.toArray(keySet);
					for (int index=1;index < keySet.length;index++)
					{
						FONTS_PATHS.put(keySet[index], keySet);
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
				if (tmpWeight == weight && tmpWeight != -1)
				{
					return;
				}
				else if (tmpWeight == -1)
				{
					if (weight != -1)
					{
						return;
					}
					else
					{
						weight = 4;
					}
				}
				else
				{
					weight = tmpWeight;
				}
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
	private FontKey()
	{}

	//使单词首字母大写，成为大驼峰，输入为扫描出的字符串。
	protected static String turnBigHumpName19(String scanStr, boolean isFileName)
	{
		scanStr = scanStr.trim();
		if (isFileName)
		{
			char scan[]=scanStr.toCharArray();
			int mark=0;
			for (int index=scan.length - 1;index >= 0;index--)
			{
				if (scan[index] == '.')
				{
					mark = index;
				}

				if (scan[index] == '-')
				{
					mark = index;
					break;
				}
			}

			return new String(scan, 0, mark);
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
		scanStr = scanStr.trim().toLowerCase();
		if (scanStr.indexOf("italic") >= 0)
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
		if (attrs == null || attrs.getLength() == 0)
		{
			return 'N';
		}
		else
		{
			String value=attrs.getValue(0);
			switch (value)
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
		scanStr = scanStr.trim().toLowerCase();
		if (scanStr.indexOf("thin") >= 0)
		{
			return 1;
		}
		else if (scanStr.indexOf("extralight") >= 0)
		{
			return 2;
		}
		else if (scanStr.indexOf("light") >= 0)
		{
			return 3;
		}
		else if (scanStr.indexOf("normal") >= 0 || scanStr.indexOf("regular") >= 0)
		{
			return 4;
		}
		else if (scanStr.indexOf("medium") >= 0)
		{
			return 5;
		}
		else if (scanStr.indexOf("semibold") >= 0)
		{
			return 6;
		}
		else if (scanStr.indexOf("extrabold") >= 0)
		{
			return 8;
		}
		else if (scanStr.indexOf("bold") >= 0)
		{
			return 7;
		}
		else if (scanStr.indexOf("black") >= 0)
		{
			return 9;
		}
		else
		{
			return -1;
		}
	}

	//合成键
	protected static String makeKey(String name, char style, char variant, byte weight)
	{
		return new StringBuilder(name).append('-').append(style).append('-').append(variant).append('-').append(weight).toString();
	}
}
