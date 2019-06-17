package org.tonygatins.tonysmith.api.conch;

/**
 * 引擎运行时出错异常。
 * @author Tony
 */
public final class ConchGEException extends RuntimeException
{
	/**
	 * 无参构造。
	 */
	public ConchGEException()
	{
		super();
	}
	
	/**
	 * 带信息构造。
	 * @param message 错误信息。
	 */
	public ConchGEException(String message)
	{
		super(message);
	}
}
