//字体配置表分析测试
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;
import java.io.FileNotFoundException;

public class Main
{
	private static int SDK_INT;
	
	public static void main(String[] args)
	{
		System.out.println("Set SDK_INT:");
		System.out.print("SDK_INT=");
		SDK_INT=new Scanner(System.in).nextInt();
		PrintStream out=System.out;
		try
		{
			System.setOut(new PrintStream(new FileOutputStream(new File("/storage/emulated/10/langc/output.txt"))));
		}
		catch (FileNotFoundException e)
		{}
		parseSystemFontsConfig();
		out.println("End");
	}
	
	private static String[] getSystemFontsConfigFileName()
	{
		if(SDK_INT<=19)
		{
			return new String[]{"/system/etc/system_fonts.xml","/system/etc/fallback_fonts.xml"};
		}
		else if(SDK_INT<=23)
		{
			return new String[]{"/system/etc/system_fonts.xml","/system/etc/fallback_fonts.xml","/system/etc/fonts.xml"};
		}
		else
		{
			return new String[]{"/system/etc/fonts.xml"};
		}
	}
	
	private static void parseSystemFontsConfig()
	{
		String configs[]=getSystemFontsConfigFileName();

		switch(configs.length)
		{
			case 1:
				parseSystemFontsConfig(configs[0]);
				break;
			case 2:
				parseSystemFontsConfig(configs[0]);
				parseSystemFontsConfig(configs[1]);
				break;
			case 3:
				parseSystemFontsConfig(configs[0]);
				parseSystemFontsConfig(configs[1]);
				parseSystemFontsConfig(configs[2]);
				break;
		}
	}

	//旧设备配置文件方法
	private static void parseSystemFontsConfig(String configPath)
	{
		try
		{
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
		protected FontsConfig19()
		{}

		//开始分析元素
		@Override
		public void startElement(String uri,String localName,String qName,Attributes attributes)
		{
			// TODO: Implement this method
			System.out.println("Start:"+localName);
			System.out.println("QName:"+qName);
			if(attributes.getLength()>0)
			{
				int lenght=0;
				while(lenght<attributes.getLength())
				{
					System.out.println("Attribute:"+attributes.getLocalName(lenght)+",QName="+attributes.getQName(lenght)+",Value="+attributes.getValue(lenght));
					lenght++;
				}
			}
			System.out.println("========Start========");
		}

		//截取字符串
		@Override
		public void characters(char[] ch,int start,int length)
		{
			// TODO: Implement this method
			System.out.println("Value:");
			System.out.println(new String(ch,start,length));
			System.out.println("========Value end========");
		}

		//结束分析元素
		@Override
		public void endElement(String uri,String localName,String qName)
		{
			// TODO: Implement this method
			System.out.println("End:"+localName);
			System.out.println("QName:"+qName);
			System.out.println("========End========");
		}
	}
}
