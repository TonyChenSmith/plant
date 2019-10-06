package org.tonygatins.tonysmith.conch.resource;

/**
 * 关于字体属性的枚举集。方便构造用。
 * @author Tony Chen Smith
 */
public final class ConchFontAttribute
{
	private ConchFontAttribute(){}
	
	//权重枚举
	public final static enum Weight
	{
		//w1
		THIN,
		
		//w2
		EXTRA_LIGHT,
		
		//w3
		LIGHT,
		
		//w4
		REGULAR,
		
		//w5
		MEDIUM,
		
		//w6
		SEMI_BOLD,
		
		//w7
		BOLD,
		
		//w8
		EXTRA_BOLD,
		
		//w9
		BLACK
	}
	
	//风格枚举
	public final static enum Style
	{
		//斜体
		ITALIC,
		
		//正常
		NORMAL
	}
	
	//样式枚举
	public final static enum Variant
	{
		ELEGANT,
		NORMAL,
		COMPACT
	}
}
