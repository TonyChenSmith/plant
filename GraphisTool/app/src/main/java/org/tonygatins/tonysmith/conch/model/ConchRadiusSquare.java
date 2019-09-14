package org.tonygatins.tonysmith.conch.model;

/**
 * 抽象模型：点半径正方形。
 * 以屏幕坐标系为设计基准。（也可以尝试用于别的坐标系，需要进行一定的转换）
 * 由中心（圆心）和半径构成。半径取绝对值。
 * @author Tony Chen Smith
 */
public class ConchRadiusSquare implements Cloneable
{
	//中心点坐标和半径,半径大于等于0
	private float centerX,centerY,radius;
	
	public ConchRadiusSquare(float centerX,float centerY,float radius)
	{
		this.centerX=centerX;
		this.centerY=centerY;
		this.radius=Math.abs(radius);
	}
	
	public ConchRadiusSquare(int centerX,int centerY,int radius)
	{
		this.centerX=centerX;
		this.centerY=centerY;
		this.radius=Math.abs(radius);
	}
	
	public ConchRadiusSquare(double centerX,double centerY,double radius)
	{
		this.centerX=(float)centerX;
		this.centerY=(float)centerY;
		this.radius=Math.abs((float)radius);
	}

	//设置器
	
	//float
	
	public void setCenterX(float centerX)
	{
		this.centerX = centerX;
	}

	public void setCenterY(float centerY)
	{
		this.centerY = centerY;
	}

	public void setCenter(float centerX,float centerY)
	{
		this.centerX=centerX;
		this.centerY=centerY;
	}
	
	public void setRadius(float radius)
	{
		this.radius=Math.abs(radius);
	}
	
	public void setAll(float centerX,float centerY,float radius)
	{
		this.centerX=centerX;
		this.centerY=centerY;
		this.radius=Math.abs(radius);
	}
	
	//int
	
	public void setCenterX(int centerX)
	{
		this.centerX = centerX;
	}

	public void setCenterY(int centerY)
	{
		this.centerY = centerY;
	}

	public void setCenter(int centerX,int centerY)
	{
		this.centerX=centerX;
		this.centerY=centerY;
	}

	public void setRadius(int radius)
	{
		this.radius=Math.abs(radius);
	}

	public void setAll(int centerX,int centerY,int radius)
	{
		this.centerX=centerX;
		this.centerY=centerY;
		this.radius=Math.abs(radius);
	}
	
	//double

	public void setCenterX(double centerX)
	{
		this.centerX =(float) centerX;
	}

	public void setCenterY(double centerY)
	{
		this.centerY =(float) centerY;
	}

	public void setCenter(double centerX,double centerY)
	{
		this.centerX=(float)centerX;
		this.centerY=(float)centerY;
	}

	public void setRadius(double radius)
	{
		this.radius=(float)Math.abs(radius);
	}

	public void setAll(double centerX,double centerY,double radius)
	{
		this.centerX=(float)centerX;
		this.centerY=(float)centerY;
		this.radius=(float)Math.abs(radius);
	}
	
	public float getCenterX()
	{
		return centerX;
	}
	
	public float getCenterY()
	{
		return centerY;
	}
	
	public float getRadius()
	{
		return radius;
	}
	
	//其他方法
	
	//点径图变矩形
	public ConchRect toRect()
	{
		return new ConchRect(centerX-radius,centerY-radius,centerX+radius,centerY+radius);
	}
	
	//检查点是否在点径图里(包括边界)
	public boolean isInRadiusSquare(float pointX,float pointY)
	{
		return toRect().isInRect(pointX,pointY);
	}
	
	public boolean isInRadiusSquare(int pointX,int pointY)
	{
		return toRect().isInRect(pointX,pointY);
	}
	
	public boolean isInRadiusSquare(double pointX,double pointY)
	{
		return toRect().isInRect(pointX,pointY);
	}
	
	//检查点是否在点径图里(不包括边界)
	public boolean isInRadiusSquareWithoutBorder(float pointX,float pointY)
	{
		return toRect().isInRectWithoutBorder(pointX,pointY);
	}

	public boolean isInRadiusSquareWithoutBorder(int pointX,int pointY)
	{
		return toRect().isInRectWithoutBorder(pointX,pointY);
	}

	public boolean isInRadiusSquareWithoutBorder(double pointX,double pointY)
	{
		return toRect().isInRectWithoutBorder(pointX,pointY);
	}
	
	//获得与另一个矩形或点径图的重叠矩形
	public ConchRect getOverlappingRect(ConchRect another)
	{
		return toRect().getOverlappingRect(another);
	}
	
	public ConchRect getOverlappingRect(ConchRadiusSquare another)
	{
		return toRect().getOverlappingRect(another.toRect());
	}
	
	public ConchRect getOverlappingRect(float centerX,float centerY,float radius)
	{
		return toRect().getOverlappingRect(new ConchRadiusSquare(centerX,centerY,radius).toRect());
	}
	
	public ConchRect getOverlappingRect(int centerX,int centerY,int radius)
	{
		return toRect().getOverlappingRect(new ConchRadiusSquare(centerX,centerY,radius).toRect());
	}
	
	public ConchRect getOverlappingRect(double centerX,double centerY,double radius)
	{
		return toRect().getOverlappingRect(new ConchRadiusSquare(centerX,centerY,radius).toRect());
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
		if(obj==null||!(obj instanceof ConchRadiusSquare))
		{
			return false;
		}

		ConchRadiusSquare radsqua=(ConchRadiusSquare)obj;
		return radsqua.centerX==centerX&&radsqua.centerY==centerY&&radsqua.radius==radius ? true:false;
	}

	//重写输出为字符串
	@Override
	public String toString()
	{
		// TODO: Implement this method
		StringBuilder result=new StringBuilder(30);
		result.append(centerX).append(',').append(centerY).append(';').append(radius);
		return result.toString();
	}

	//重写克隆方法
	@Override
	protected ConchRadiusSquare clone() throws CloneNotSupportedException
	{
		// TODO: Implement this method
		return (ConchRadiusSquare)super.clone();
	}
}
