package org.tonygatins.tonysmith.conch;

/**
 * 环境接口，为绘制提供一切资源。
 * @author Tony Chen Smith
 */
public interface ConchEnvironment
{
	public abstract void setSize(int xSize,int ySize);
	
	public abstract void getCanvas();
}
