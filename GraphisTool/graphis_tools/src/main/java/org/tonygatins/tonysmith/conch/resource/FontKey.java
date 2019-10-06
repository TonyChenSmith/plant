package org.tonygatins.tonysmith.conch.resource;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.xml.sax.Attributes;

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
	
	//风格枚举转数字代码
	protected static char toStyleCode(ConchFontAttribute.Style style)
	{
		if(style==null)
		{
			return 'N';
		}
		
		switch(style)
		{
			case ITALIC:
				return 'I';
			default:
				return 'N';
		}
	}
	
	//形式枚举转数字代码
	protected static char toVariantCode(ConchFontAttribute.Variant variant)
	{
		if(variant==null)
		{
			return 'N';
		}
		
		switch(variant)
		{
			case ELEGANT:
				return 'E';
			case COMPACT:
				return 'C';
			default:
				return 'N';
		}
	}
	
	//权重枚举转数字代码
	protected static byte toWeightCode(ConchFontAttribute.Weight weight)
	{
		if(weight==null)
		{
			return 4;
		}
		
		switch(weight)
		{
			case THIN:
				return 1;
			case EXTRA_LIGHT:
				return 2;
			case LIGHT:
				return 3;
			case REGULAR:
				return 4;
			case MEDIUM:
				return 5;
			case SEMI_BOLD:
				return 6;
			case BOLD:
				return 7;
			case EXTRA_BOLD:
				return 8;
			case BLACK:
				return 9;
			default:
				return -1;
		}
	}
	
	//扫描字符串变权重对象
	protected static ConchFontAttribute.Weight toWeightObject(String scanStr)
	{
		switch(scanStr)
		{
			case "1":
				return ConchFontAttribute.Weight.THIN;
			case "2":
				return ConchFontAttribute.Weight.EXTRA_LIGHT;
			case "3":
				return ConchFontAttribute.Weight.LIGHT;
			case "4":
				return ConchFontAttribute.Weight.REGULAR;
			case "5":
				return ConchFontAttribute.Weight.MEDIUM;
			case "6":
				return ConchFontAttribute.Weight.SEMI_BOLD;
			case "7":
				return ConchFontAttribute.Weight.BOLD;
			case "8":
				return ConchFontAttribute.Weight.EXTRA_BOLD;
			case "9":
				return ConchFontAttribute.Weight.BLACK;
			default:
				return null;
		}
	}
	
	//扫描字符串变形式对象
	protected static ConchFontAttribute.Variant toVariantObject(String scanStr)
	{
		switch(scanStr)
		{
			case "N":
				return ConchFontAttribute.Variant.NORMAL;
			case "E":
				return ConchFontAttribute.Variant.ELEGANT;
			case "C":
				return ConchFontAttribute.Variant.COMPACT;
			default:
				return null;
		}
	}
	
	//扫描字符串变形式对象
	protected static ConchFontAttribute.Style toStyleObject(String scanStr)
	{
		switch(scanStr)
		{
			case "N":
				return ConchFontAttribute.Style.NORMAL;
			case "I":
				return ConchFontAttribute.Style.ITALIC;
			default:
				return null;
		}
	}
	
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
