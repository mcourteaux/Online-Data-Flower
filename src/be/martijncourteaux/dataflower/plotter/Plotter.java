package be.martijncourteaux.dataflower.plotter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import be.martijncourteaux.dataflower.unittest.VectorData;


public class Plotter
{
	private Ellipse2D.Double dot = new Ellipse2D.Double(0, 0, 3, 3);
	private Line2D.Double line = new Line2D.Double(0, 0, 0, 0);
	private Stroke stroke;
	private Font xFont;
	private Font yFont;
	private float yAxisIndications;
	private double arrowSize;
	private float axisWidth;
	private float markerWidth;

	private BufferedImage img;
	private Graphics2D g;
	private Rectangle2D.Double viewport;
	private double scaleX, scaleY;
	private Margin margin;
	
	private int flags;

	public static final int FLAG_LINES = 0x01;
	public static final int FLAG_DOTS = 0x02;
	

	public Plotter(int w, int h)
	{
		this.img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB_PRE);
		this.g = img.createGraphics();
		this.g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		this.flags = FLAG_LINES;
		this.margin = new Margin();
		this.setViewport(new Rectangle2D.Double(0, 0, 1, 1));
		this.setStrokeWidth(1.0);
		this.setFont(new Font("", Font.PLAIN, 15));
		this.setArrowSize(10);
		this.setAxisWidth(3);
		this.setMarkerWidth(3);
	}
	
	public Plotter(PlotterSettings settings)
	{
		this(settings.getWidth(), settings.getHeigth());
		this.setFlags(settings.getFlags());
		this.setArrowSize(settings.getArrowSize());
		this.setMargin(settings.getMargin());
		this.setXFont(settings.getXFont());
		this.setYFont(settings.getYFont());	
		this.setAxisWidth(settings.getAxisWidth());
		this.setMarkerWidth(settings.getMarkerWidth());
		this.setYAxisIndications(settings.getYAxisIndications());
	}

	public void setFont(Font font)
	{
		this.xFont = font;
		this.yFont = font;
		this.g.setFont(font);
	}
	
	public void setArrowSize(double as)
	{
		this.arrowSize = as;
	}

	public void setFlags(int flags)
	{
		this.flags = flags;
	}

	public void setDotSize(double size)
	{
		dot.width = dot.height = size;
	}

	public void setStrokeWidth(double width)
	{
		stroke = new BasicStroke((float) width);
		g.setStroke(stroke);
	}
	
	public void setMargin(Margin margin)
	{
		this.margin = margin;
		setViewport(viewport);
	}
	
	public void setXFont(Font xFont)
	{
		this.xFont = xFont;
	}
	
	public void setYFont(Font yFont)
	{
		this.yFont = yFont;
	}
	
	public void setMarkerWidth(float markerWidth)
	{
		this.markerWidth = markerWidth;
	}
	
	public void setAxisWidth(float axisWidth)
	{
		this.axisWidth = axisWidth;
	}

	public float getYAxisIndications()
	{
		return yAxisIndications;
	}

	public void setYAxisIndications(float yAxisIndications)
	{
		this.yAxisIndications = yAxisIndications;
	}

	public void setAutoViewPort(double x0, double x1, double y0, double y1)
	{
		double min = Math.min(y0 - 0.1, -1.2);
		double max = Math.max(y1 - 0.1, +1.2);
		double height = max - min;
		height *= 1.1;
		min *= 1.1;
		setViewport(new Rectangle2D.Double(x0, min, x1 - x0, height));
	}

	public void setViewport(Rectangle2D.Double viewport)
	{
		this.viewport = viewport;
		scaleX = (img.getWidth() - margin.l - margin.r) / viewport.width;
		scaleY = (img.getHeight() - margin.t - margin.b) / viewport.height;
		g.setTransform(AffineTransform.getTranslateInstance(margin.l, margin.t));
	}

	public void plotVectorComponent(VectorData data, int comp, LineStyle style)
	{
		g.setColor(style.getColor());
		g.setStroke(new BasicStroke(style.getWidth()));
		setDotSize(style.getDotSize());
		
		int lower = Math.max((int) Math.floor(viewport.x), 0);
		int upper = Math.min((int) Math.ceil(viewport.x + viewport.width), data.size());
		
		for (int i = lower; i < upper; ++i)
		{
			double d = data.getVector(i).get(comp);
			plotPoint(i, d);
		}
	}
	
	public void plotVectorData(VectorData data)
	{
		if (data.getDim() == 3)
		{
			plotVectorComponent(data, 0, new LineStyle(Color.RED,   1.0f, 3.0f));
			plotVectorComponent(data, 1, new LineStyle(Color.GREEN, 1.0f, 3.0f));
			plotVectorComponent(data, 2, new LineStyle(Color.BLUE,  1.0f, 3.0f));
		} else if (data.getDim() == 1)
		{
			plotVectorComponent(data, 0, new LineStyle(new Color(150, 0, 150), 1.0f, 3.0f));
		}
	}

	private void plotPoint(double x, double y)
	{
		dot.x = x - viewport.x;
		dot.y = y - viewport.y;
		dot.x *= scaleX;
		dot.y *= scaleY;
		dot.y = img.getHeight() - dot.y - margin.t - margin.b;

		line.setLine(line.x2, line.y2, dot.x, dot.y);
		if (line.x1 < line.x2 && (flags & FLAG_LINES) != 0)
		{
			g.draw(line);
		}
		dot.x -= dot.width * 0.5d;
		dot.y -= dot.height * 0.5d;

		if ((flags & FLAG_DOTS) != 0)
			g.fill(dot);
	}

	public void plotXAxis()
	{
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(axisWidth));
		double y = img.getHeight() + viewport.y * scaleY - margin.t - margin.b;
		double x = img.getWidth() - arrowSize * 2 - margin.l - margin.r;
		line.setLine(0, y, x, y);
		g.draw(line);
		
		Path2D.Double triangle = new Path2D.Double();
		triangle.moveTo(x, y - arrowSize);
		triangle.lineTo(x + 2 * arrowSize, y);
		triangle.lineTo(x, y + arrowSize);
		
		g.fill(triangle);
	}
	
	public void plotYAxis()
	{
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(axisWidth));
		line.setLine(0, 20, 0, img.getHeight() - margin.t - margin.b);
		g.draw(line);
		
		Path2D.Double triangle = new Path2D.Double();
		triangle.moveTo(0, 0);
		triangle.lineTo(arrowSize, arrowSize * 2);
		triangle.lineTo(-arrowSize, arrowSize * 2);
		
		g.fill(triangle);
	}
	
	public void plotXIndications(double time, double interval, String name, String unit, String format)
	{
		double w = img.getWidth() - margin.l - margin.r;
		double wPerUnit = w / time;
		int intervals = (int) Math.ceil(time / interval);
		g.setStroke(new BasicStroke(markerWidth));
		g.setColor(Color.BLACK);
		g.setFont(xFont);
		double y = img.getHeight() + viewport.y * scaleY - margin.t - margin.b;
		FontMetrics fm = g.getFontMetrics();


		for (int i = 1; i < intervals; ++i)
		{
			double x = i * wPerUnit * interval;
			double t = interval * i;
			line.x1 = line.x2 = x;
			line.y1 = y - 10;
			line.y2 = y + 10;
			
			g.draw(line);
			String txt = String.format(format, t);
			g.drawString(txt, (float) (x - fm.stringWidth(txt) * 0.5), (float) (y - 30));
		}
		
		String label = name + " (" + unit + ")";
		g.drawString(label, (float) (w - fm.stringWidth(label) - 10), (float) (y + 10 + xFont.getSize2D()));
	}
	
	public void plotYIndications(double interval, String name, String unit, String format)
	{
		double h = img.getHeight() - margin.t - margin.b;
		double hPerUnit = h / viewport.height;
		int intervalsMax = (int) Math.ceil((viewport.y + viewport.height) / interval);
		int intervalsMin = (int) Math.ceil((viewport.y) / interval);
		g.setStroke(new BasicStroke(markerWidth));
		g.setColor(Color.BLACK);
		g.setFont(yFont);
		FontMetrics fm = g.getFontMetrics();
		double zeroY = img.getHeight() + viewport.y * scaleY - margin.t - margin.b;


		for (int i = intervalsMin; i < intervalsMax; ++i)
		{
			if (i == 0 && yAxisIndications > 0.0f) continue;
			
			double y = zeroY - i * hPerUnit * interval;
			double t = interval * i;
			line.y1 = line.y2 = y;
			line.x1 = -10;
			line.x2 = +10;
			
			g.draw(line);
			String txt = String.format(format, t);
			g.drawString(txt, (float) (15 * yAxisIndications + fm.stringWidth(txt) * Math.max(-1.0, Math.min(0.0, -0.5 + 0.5 * yAxisIndications))), (float) (y + fm.getHeight() * 0.3f));
		}
		
		String label = name + " (" + unit + ")";
		g.drawString(label, -10.0f, 10.0f - yFont.getSize2D());
	}
	
	public void plotUnitAxis()
	{
		g.setStroke(new BasicStroke(axisWidth));
		g.setColor(Color.DARK_GRAY);
		{
			double y = img.getHeight() - (1 - viewport.y) * scaleY;
			line.setLine(0, y, img.getWidth() - margin.l - margin.r, y);
			g.draw(line);
		}
		{
			double y = img.getHeight() - (-1 - viewport.y) * scaleY;
			line.setLine(0, y, img.getWidth() - margin.l - margin.r, y);
			g.draw(line);
		}
	}

	public BufferedImage getImg()
	{
		return img;
	}


}

