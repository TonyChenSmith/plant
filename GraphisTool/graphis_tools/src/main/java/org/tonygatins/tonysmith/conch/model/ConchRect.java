package org.tonygatins.tonysmith.conch.model;

/**
 * 抽象模型：矩形。
 * 以屏幕坐标系为设计基准。（也可以尝试用于别的坐标系，需要进行一定的转换）
 * 左上为top点，右下为buttom点。
 * @author Tony Chen Smith
 */
public class ConchRect implements Cloneable
{
	//四个坐标值
	private float topX,topY,buttomX,buttomY;

	//四坐标值构造 float
	public ConchRect(float topX,float topY,float buttomX,float buttomY)
	{
		this.topX = topX;
		this.topY = topY;
		this.buttomX = buttomX;
		this.buttomY = buttomY;
		checkCoordinateAndFix();
	}

	//四坐标值构造 int
	public ConchRect(int topX,int topY,int buttomX,int buttomY)
	{
		this.topX = topX;
		this.topY = topY;
		this.buttomX = buttomX;
		this.buttomY = buttomY;
		checkCoordinateAndFix();
	}

	//四坐标值构造 double
	public ConchRect(double topX,double topY,double buttomX,double buttomY)
	{
		this.topX = (float)topX;
		this.topY = (float)topY;
		this.buttomX = (float)buttomX;
		this.buttomY = (float)buttomY;
		checkCoordinateAndFix();
	}

	//检查坐标并在错误时修正
	private void checkCoordinateAndFix()
	{
		if(buttomX - topX < 0)
		{
			float tempX=topX;
			topX = buttomX;
			buttomX = tempX;
		}

		if(buttomY - topY < 0)
		{
			float tempY=topY;
			topY = buttomY;
			buttomY = tempY;
		}
	}

	//设置坐标方法

	//float

	//topX
	public void setTopX(float topX)
	{
		this.topX = topX;
		checkCoordinateAndFix();
	}

	//topY
	public void setTopY(float topY)
	{
		this.topY = topY;
		checkCoordinateAndFix();
	}

	//buttomX
	public void setButtomX(float buttomX)
	{
		this.buttomX = buttomX;
		checkCoordinateAndFix();
	}

	//buttomY
	public void setButtomY(float buttomY)
	{
		this.buttomY = buttomY;
		checkCoordinateAndFix();
	}

	//top坐标
	public void setTop(float topX,float topY)
	{
		this.topX = topX;
		this.topY = topY;
		checkCoordinateAndFix();
	}

	//buttom坐标
	public void setButtom(float buttomX,float buttomY)
	{
		this.buttomX = buttomX;
		this.buttomY = buttomY;
		checkCoordinateAndFix();
	}

	//top and buttom
	public void setAll(float topX,float topY,float buttomX,float buttomY)
	{
		this.topX = topX;
		this.topY = topY;
		this.buttomX = buttomX;
		this.buttomY = buttomY;
		checkCoordinateAndFix();
	}

	//int

	//topX
	public void setTopX(int topX)
	{
		this.topX = topX;
		checkCoordinateAndFix();
	}

	//topY
	public void setTopY(int topY)
	{
		this.topY = topY;
		checkCoordinateAndFix();
	}

	//buttomX
	public void setButtomX(int buttomX)
	{
		this.buttomX = buttomX;
		checkCoordinateAndFix();
	}

	//buttomY
	public void setButtomY(int buttomY)
	{
		this.buttomY = buttomY;
		checkCoordinateAndFix();
	}

	//top坐标
	public void setTop(int topX,int topY)
	{
		this.topX = topX;
		this.topY = topY;
		checkCoordinateAndFix();
	}

	//buttom坐标
	public void setButtom(int buttomX,int buttomY)
	{
		this.buttomX = buttomX;
		this.buttomY = buttomY;
		checkCoordinateAndFix();
	}

	//top and buttom
	public void setAll(int topX,int topY,int buttomX,int buttomY)
	{
		this.topX = topX;
		this.topY = topY;
		this.buttomX = buttomX;
		this.buttomY = buttomY;
		checkCoordinateAndFix();
	}

	//double

	//topX
	public void setTopX(double topX)
	{
		this.topX = (float)topX;
		checkCoordinateAndFix();
	}

	//topY
	public void setTopY(double topY)
	{
		this.topY = (float) topY;
		checkCoordinateAndFix();
	}

	//buttomX
	public void setButtomX(double buttomX)
	{
		this.buttomX = (float) buttomX;
		checkCoordinateAndFix();
	}

	//buttomY
	public void setButtomY(double buttomY)
	{
		this.buttomY = (float) buttomY;
		checkCoordinateAndFix();
	}

	//top坐标
	public void setTop(double topX,double topY)
	{
		this.topX = (float)topX;
		this.topY = (float)topY;
		checkCoordinateAndFix();
	}

	//buttom坐标
	public void setButtom(double buttomX,double buttomY)
	{
		this.buttomX = (float)buttomX;
		this.buttomY = (float)buttomY;
		checkCoordinateAndFix();
	}

	//top and buttom
	public void setAll(double topX,double topY,double buttomX,double buttomY)
	{
		this.topX = (float)topX;
		this.topY = (float)topY;
		this.buttomX = (float)buttomX;
		this.buttomY = (float)buttomY;
		checkCoordinateAndFix();
	}
	
	//访问器
	
	//topX
	public float getTopX()
	{
		return topX;
	}

	//topY
	public float getTopY()
	{
		return topY;
	}

	//buttomX
	public float getButtomX()
	{
		return buttomX;
	}

	//buttomY
	public float getButtomY()
	{
		return buttomY;
	}
	
	//其他方法
	
	//中央x坐标
	public float getCenterX()
	{
		return (topX+buttomX)/2;
	}
	
	//中央y坐标
	public float getCenterY()
	{
		return (topY+buttomY)/2;
	}
	
	//检查点是否在矩形中(含边界)
	public boolean isInRect(float pointX,float pointY)
	{
		return pointX>=topX&&pointX<=buttomX&&pointY>=topY&&pointY<=buttomY;
	}
	
	//检查点是否在矩形内
	public boolean isInRectWithoutBorder(float pointX,float pointY)
	{
		return pointX>topX&&pointX<buttomX&&pointY>topY&&pointY<buttomY;
	}
	
	//检查点是否在矩形中(含边界)
	public boolean isInRect(double pointx,double pointy)
	{
		float pointX=(float)pointx;
		float pointY=(float)pointy;
		return pointX>=topX&&pointX<=buttomX&&pointY>=topY&&pointY<=buttomY;
	}

	//检查点是否在矩形内
	public boolean isInRectWithoutBorder(double pointx,double pointy)
	{
		float pointX=(float)pointx;
		float pointY=(float)pointy;
		return pointX>topX&&pointX<buttomX&&pointY>topY&&pointY<buttomY;
	}
	
	//检查点是否在矩形中(含边界)
	public boolean isInRect(int pointx,int pointy)
	{
		float pointX=pointx;
		float pointY=pointy;
		return pointX>=topX&&pointX<=buttomX&&pointY>=topY&&pointY<=buttomY;
	}

	//检查点是否在矩形内
	public boolean isInRectWithoutBorder(int pointx,int pointy)
	{
		float pointX=pointx;
		float pointY=pointy;
		return pointX>topX&&pointX<buttomX&&pointY>topY&&pointY<buttomY;
	}
	
	//获得面与面的重叠部分(包括边界) overlapping 重叠
	public ConchRect getOverlappingRect(ConchRect another)
	{
		if(equals(another))
		{
			try
			{
				return this.clone();
			}
			catch(CloneNotSupportedException e)
			{
				return new ConchRect(topX,topY,buttomX,buttomY);
			}
		}
		
		if(another.topX>buttomX||another.buttomX<topX||another.topY>buttomY||another.buttomY<topY)
		{
			return null;
		}
		else
		{
			if(another.topX>=topX)
			{
				if(another.buttomX>=buttomX)
				{
					if(another.topY>=topY)
					{
						if(another.buttomY>=buttomY)
						{
							return new ConchRect(another.topX,another.topY,buttomX,buttomY);
						}
						else
						{
							return new ConchRect(another.topX,another.topY,buttomX,another.buttomY);
						}
					}
					else
					{
						if(another.buttomY>=buttomY)
						{
							return new ConchRect(another.topX,topY,buttomX,buttomY);
						}
						else
						{
							return new ConchRect(another.topX,topY,buttomX,another.buttomY);
						}
					}
				}
				else
				{
					if(another.topY>=topY)
					{
						if(another.buttomY>=buttomY)
						{
							return new ConchRect(another.topX,another.topY,another.buttomX,buttomY);
						}
						else
						{
							try
							{
								return another.clone();
							}
							catch(CloneNotSupportedException e)
							{
								return new ConchRect(another.topX,another.topY,another.buttomX,another.buttomY);
							}
						}
					}
					else
					{
						if(another.buttomY>=buttomY)
						{
							return new ConchRect(another.topX,topY,another.buttomX,buttomY);
						}
						else
						{
							return new ConchRect(another.topX,topY,another.buttomX,another.buttomY);
						}
					}
				}
			}
			else
			{
				if(another.buttomX>=buttomX)
				{
					if(another.topY>=topY)
					{
						if(another.buttomY>=buttomY)
						{
							return new ConchRect(topX,another.topY,buttomX,buttomY);
						}
						else
						{
							return new ConchRect(topX,another.topY,buttomX,another.buttomY);
						}
					}
					else
					{
						if(another.buttomY>=buttomY)
						{
							try
							{
								return this.clone();
							}
							catch(CloneNotSupportedException e)
							{
								return new ConchRect(topX,topY,buttomX,buttomY);
							}
						}
						else
						{
							return new ConchRect(topX,topY,buttomX,another.buttomY);
						}
					}
				}
				else
				{
					if(another.topY>=topY)
					{
						if(another.buttomY>=buttomY)
						{
							return new ConchRect(topX,another.topY,another.buttomX,buttomY);
						}
						else
						{
							return new ConchRect(topX,another.topY,another.buttomX,another.buttomY);
						}
					}
					else
					{
						if(another.buttomY>=buttomY)
						{
							return new ConchRect(topX,topY,another.buttomX,buttomY);
						}
						else
						{
							return new ConchRect(topX,topY,another.buttomX,another.buttomY);
						}
					}
				}
			}
		}
	}
	
	public ConchRect getOverlappingRect(ConchRadiusSquare another)
	{
		return getOverlappingRect(another.toRect());
	}
	
	//获得面与面的重叠部分(包括边界)
	public ConchRect getOverlappingRect(float anotherTopX,float anotherTopY,float anotherButtomX,float anotherButtomY)
	{
		return getOverlappingRect(new ConchRect(anotherTopX,anotherTopY,anotherButtomX,anotherButtomY));
	}
	
	//获得面与面的重叠部分(包括边界)
	public ConchRect getOverlappingRect(int anotherTopX,int anotherTopY,int anotherButtomX,int anotherButtomY)
	{
		return getOverlappingRect(new ConchRect(anotherTopX,anotherTopY,anotherButtomX,anotherButtomY));
	}
	
	//获得面与面的重叠部分(包括边界)
	public ConchRect getOverlappingRect(double anotherTopX,double anotherTopY,double anotherButtomX,double anotherButtomY)
	{
		return getOverlappingRect(new ConchRect(anotherTopX,anotherTopY,anotherButtomX,anotherButtomY));
	}
	
	//重写方法

	//重写等于方法
	@Override
	public boolean equals(Object obj)
	{
		// TODO: Implement this method
		if(obj==this)
		{
			return true;
		}
		if(obj==null||!(obj instanceof ConchRect))
		{
			return false;
		}
		
		ConchRect rect=(ConchRect)obj;
		return rect.topX==topX&&rect.topY==topY&&rect.buttomX==buttomX&&rect.buttomY==buttomY ? true:false;
	}

	//重写输出为字符串
	@Override
	public String toString()
	{
		// TODO: Implement this method
		StringBuilder result=new StringBuilder(50);
		result.append(topX).append(',').append(topY).append(';').append(buttomX).append(',').append(buttomY);
		return result.toString();
	}

	//重写克隆方法
	@Override
	protected ConchRect clone() throws CloneNotSupportedException
	{
		// TODO: Implement this method
		return (ConchRect)super.clone();
	}
}
