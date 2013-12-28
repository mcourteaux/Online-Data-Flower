package be.ugent.martijncourteaux.dataflower.plotter;

import java.awt.Color;

public class LineStyle
{
	private Color color;
	private float width;
	private float dotSize;
	
	public LineStyle()
	{
	}
	
	public LineStyle(Color color, float width, float dotSize)
	{
		super();
		this.color = color;
		this.width = width;
		this.dotSize = dotSize;
	}

	public Color getColor()
	{
		return color;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}

	public float getWidth()
	{
		return width;
	}

	public void setWidth(float width)
	{
		this.width = width;
	}

	public float getDotSize()
	{
		return dotSize;
	}

	public void setDotSize(float dotSize)
	{
		this.dotSize = dotSize;
	}
	
	
}
