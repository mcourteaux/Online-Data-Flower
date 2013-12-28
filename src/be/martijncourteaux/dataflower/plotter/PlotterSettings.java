package be.martijncourteaux.dataflower.plotter;

import java.awt.Font;

public class PlotterSettings
{
	private int width, heigth;
	private int flags;
	private Font xFont;
	private Font yFont;
	private double arrowSize;
	private Margin margin;
	private float axisWidth;
	private float markerWidth;
	
	private float yAxisIndications;

	public PlotterSettings()
	{
		flags = Plotter.FLAG_DOTS | Plotter.FLAG_LINES;
		arrowSize = 10;
		xFont = yFont = new Font("", Font.BOLD, 10);
		margin = new Margin();
		axisWidth = 3;
		markerWidth = 3;
		yAxisIndications = -1.0f;
	}

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public int getHeigth()
	{
		return heigth;
	}

	public void setHeigth(int heigth)
	{
		this.heigth = heigth;
	}

	public int getFlags()
	{
		return flags;
	}

	public void setFlags(int flags)
	{
		this.flags = flags;
	}

	public Font getXFont()
	{
		return xFont;
	}

	public void setXFont(Font xFont)
	{
		this.xFont = xFont;
	}

	public Font getYFont()
	{
		return yFont;
	}

	public void setYFont(Font yFont)
	{
		this.yFont = yFont;
	}

	public double getArrowSize()
	{
		return arrowSize;
	}

	public void setArrowSize(double arrowSize)
	{
		this.arrowSize = arrowSize;
	}

	public Margin getMargin()
	{
		return margin;
	}

	public void setMargin(Margin margin)
	{
		this.margin = margin;
	}

	public float getAxisWidth()
	{
		return axisWidth;
	}

	public void setAxisWidth(float axisWidth)
	{
		this.axisWidth = axisWidth;
	}

	public float getMarkerWidth()
	{
		return markerWidth;
	}

	public void setMarkerWidth(float markerWidth)
	{
		this.markerWidth = markerWidth;
	}

	public float getYAxisIndications()
	{
		return yAxisIndications;
	}

	public void setYAxisIndicationsPosition(float yAxisIndications)
	{
		this.yAxisIndications = yAxisIndications;
	}

	
	
}
