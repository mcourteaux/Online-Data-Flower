package be.martijncourteaux.dataflower.plotter;

import java.awt.Color;

public class RenderTask
{
	private int position;
	private LineStyle[] lineStyles;
	private static final float DEFAULT_LINE_WIDTH = 4.0f;
	private static final float DEFAULT_DOT_SIZE = 7.0f;
	
	public RenderTask(int position, int lines)
	{
		this.position = position;
		this.lineStyles = new LineStyle[lines];
		
		/* Default line style */
		if (lines == 1)
		{
			lineStyles[0] = new LineStyle(new Color(150, 0, 150), DEFAULT_LINE_WIDTH, DEFAULT_DOT_SIZE);
		} else if (lines == 3)
		{
			lineStyles[0] = new LineStyle(new Color(200, 0, 0), DEFAULT_LINE_WIDTH, DEFAULT_DOT_SIZE);
			lineStyles[1] = new LineStyle(new Color(0, 200, 0), DEFAULT_LINE_WIDTH, DEFAULT_DOT_SIZE);
			lineStyles[2] = new LineStyle(new Color(0, 0, 200), DEFAULT_LINE_WIDTH, DEFAULT_DOT_SIZE);
		} else
		{
			for (int i = 0; i < lines; ++i)
			{
				lineStyles[i] = new LineStyle(Color.BLACK, DEFAULT_LINE_WIDTH, DEFAULT_DOT_SIZE);
			}
		}
	}
	
	public LineStyle getLineStyle(int line)
	{
		return lineStyles[line];
	}
	
	public int getPosition()
	{
		return position;
	}
	
	public RenderTask setAlpha(float alpha)
	{
		for (int i = 0; i < lineStyles.length; ++i)
		{
			LineStyle ls = lineStyles[i];
			Color old = ls.getColor();
			ls.setColor(new Color(old.getRed(), old.getGreen(), old.getBlue(), (int) (alpha * 255)));
		}
		return this;
	}
	
	public RenderTask setLineWidth(float width)
	{
		for (int i = 0; i < lineStyles.length; ++i)
		{
			LineStyle ls = lineStyles[i];
			ls.setWidth(width);
		}
		return this;
	}
	
	public RenderTask setDotSize(float size)
	{
		for (int i = 0; i < lineStyles.length; ++i)
		{
			LineStyle ls = lineStyles[i];
			ls.setDotSize(size);
		}
		return this;
	}

	public RenderTask setColor(int i, Color color)
	{
		lineStyles[i].setColor(color);
		return this;
	}
}
