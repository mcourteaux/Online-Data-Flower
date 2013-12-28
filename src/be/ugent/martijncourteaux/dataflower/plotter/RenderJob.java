package be.ugent.martijncourteaux.dataflower.plotter;

import java.awt.Desktop;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import be.ugent.martijncourteaux.dataflower.ModifierChain;
import be.ugent.martijncourteaux.dataflower.unittest.CSVFilteredParser;
import be.ugent.martijncourteaux.dataflower.unittest.MinMaxData;
import be.ugent.martijncourteaux.dataflower.unittest.VectorData;

public class RenderJob
{

	private File dataFile;
	private String messageFilter;
	private int lower, upper;
	private File outputFile;

	private ModifierChain chain;
	private Plotter plotter;
	private List<RenderTask> tasks;
	
	
	private int referenceVectorDataMinMax;
	private double hertz;
	private double xInterval;
	private double yInterval;

	public RenderJob(File dataFile, String messageFilter, int lower, int upper, File outputFile, ModifierChain chain)
	{
		this.dataFile = dataFile;
		this.messageFilter = messageFilter;
		this.lower = lower;
		this.upper = upper;
		this.outputFile = outputFile;
		this.tasks = new ArrayList<RenderTask>();
		this.chain = chain;
		this.referenceVectorDataMinMax = -1;
		this.hertz = 1;
		this.xInterval = 10;
		this.yInterval = 4;
	}

	public void createPlotter(PlotterSettings settings)
	{
		plotter = new Plotter(settings);
	}
	
	public void setHertz(double hertz)
	{
		this.hertz = hertz;
	}
	
	public void setXInterval(double xInterval)
	{
		this.xInterval = xInterval;
	}
	
	public void setYInterval(double yInterval)
	{
		this.yInterval = yInterval;
	}

	private VectorData load()
	{
		VectorData data = new VectorData(3, 1000);
		File file = dataFile;
		CSVFilteredParser.parseFileVector3Filtered(file, messageFilter, data);

		if (upper == -1)
		{
			upper = data.size();
		}
		data = data.subRange(lower, upper);

		return data;
	}

	public void execute()
	{
		VectorData input = load();
		List<VectorData> output = applyChain(input);

		double min = 0;
		double max = 0;

		if (referenceVectorDataMinMax == -1)
		{
			MinMaxData mmd0 = input.calculateMinMaxData();
			min = mmd0.getMinValue();
			max = mmd0.getMaxValue();
			for (int i = 1; i < output.size(); ++i)
			{
				MinMaxData mmd1 = output.get(i).calculateMinMaxData();

				min = Math.min(min, mmd1.getMinValue());
				max = Math.max(max, mmd1.getMaxValue());
			}
		} else
		{
			MinMaxData mmd = output.get(referenceVectorDataMinMax).calculateMinMaxData();
			min = mmd.getMinValue();
			max = mmd.getMaxValue();
		}

		plotter.setAutoViewPort(0, input.size(), min, max);

		for (RenderTask task : tasks)
		{
			VectorData data = output.get(task.getPosition());
			for (int c = 0; c < data.getDim(); ++c)
			{
				plotter.plotVectorComponent(data, c, task.getLineStyle(c));
			}
		}

		plotter.plotXAxis();
		plotter.plotYAxis();
		plotter.plotXIndications(input.size() / hertz, xInterval, "Tijd", "s", "%.1f");
		plotter.plotYIndications(yInterval, "", "m/s\u00B2", "%.1f");
		
		try
		{
			ImageIO.write(plotter.getImg(), "PNG", outputFile);

			Desktop d = Desktop.getDesktop();
			d.open(outputFile);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private List<VectorData> applyChain(VectorData input)
	{
		List<VectorData> output = new ArrayList<VectorData>();
		output.add(input);
		VectorData temp = input;
		for (int i = 0; i < chain.modifierCount(); ++i)
		{
			VectorData data = chain.applyModifier(temp, i);
			output.add(data);
			temp = data;
		}
		return output;
	}

	public void addRenderTask(RenderTask task)
	{
		tasks.add(task);
	}
	
	public void setReferenceVectorDataMinMax(int referenceVectorDataMinMax)
	{
		this.referenceVectorDataMinMax = referenceVectorDataMinMax;
	}

}
