package org.tonygatins.tonysmith.conch.resource;

import org.xml.sax.Attributes;

/**
 * 对于一些字体的键的处理方法集合。耦合性高，需要本地编写。
 * @author Tony Chen Smith
 */
final class FontKey
{
	private FontKey(){}
	
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
		if(attrs==null||attrs.getLength()==0)
		{
			return 'N';
		}
		else
		{
			String value=attrs.getValue(0);
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
}
