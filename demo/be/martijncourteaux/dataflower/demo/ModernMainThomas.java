package be.martijncourteaux.dataflower.demo;

import java.awt.Font;
import java.io.File;

import be.martijncourteaux.dataflower.Convolution;
import be.martijncourteaux.dataflower.ModifierChain;
import be.martijncourteaux.dataflower.plotter.Margin;
import be.martijncourteaux.dataflower.plotter.PlotterSettings;
import be.martijncourteaux.dataflower.plotter.RenderJob;
import be.martijncourteaux.dataflower.plotter.RenderTask;
import be.martijncourteaux.dataflower.reducers.DimensionReducer;
import be.martijncourteaux.dataflower.reducers.DimensionReducer.Technique;

public class ModernMainThomas
{

	public static void main(String[] args)
	{
		PlotterSettings settings = new PlotterSettings();
		settings.setWidth(2000);
		settings.setHeigth(900);
		settings.setArrowSize(10);
		settings.setMargin(new Margin(100, 0, 100, 0));
		settings.setXFont(new Font("", Font.BOLD, 40));
		settings.setYFont(new Font("", Font.BOLD, 30));

		File outputFolder = new File("test");
		outputFolder.mkdir();
		File inputFolder = new File(new File("").getAbsoluteFile().getParentFile().getParentFile(), "/Data/raw/");
		
		//data_108_stappen_verdiep
		//3D-Thomas-Hand-15
		//
		int steps = constructUltraChain().activeModifierCount();
		File inputFile = new File(inputFolder, "3D-Thomas-Hand-15.csv");

		RenderJob job = new RenderJob(inputFile, null, 0, -1, new File(outputFolder, "3D-Thomas-Hand-15-Extract_Step_" + String.format("%2d", steps) + ".png"), constructUltraChain());
		job.createPlotter(settings);
		job.setHertz(100);
		job.setXInterval(1.0);
		job.setYInterval(4.0);
		job.addRenderTask(new RenderTask(0, 3).setAlpha(0.4f).setLineWidth(3.0f));
		job.addRenderTask(new RenderTask(steps, 1).setAlpha(0.7f).setLineWidth(6.0f).setDotSize(8.0f));
		job.execute();
	}

	private static ModifierChain constructUltraChain()
	{
		ModifierChain chain = new ModifierChain(300, 3);

		//chain.stopAfter(6);

		/* 3D -> 1D */
		chain.add(new DimensionReducer(Technique.LENGTH), 1);
		
		/* Smooth */
		{
			Convolution c = new Convolution(40);
			c.setLeftGaussian(1);
			chain.add(c, 1);
		}
		{
			Convolution c = new Convolution(30);
			c.setLeftGaussian(1);
			chain.add(c, 1);
		}
		{
			Convolution c = new Convolution(20);
			c.setLeftGaussian(1);
			chain.add(c, 1);
		}
		
		/* Derivate */
		chain.add(Convolution.getDerivation(20), 1);
		{
			Convolution c = new Convolution(20);
			c.setLeftGaussian(1);
			chain.add(c, 1);
		}
		chain.add(Convolution.getDerivation(20), 1);
		{
			Convolution c = new Convolution(30);
			c.setLeftGaussian(1);
			chain.add(c, 1);
		}
		{
			Convolution c = new Convolution(20);
			c.setLeftGaussian(1);
			chain.add(c, 1);
		}
		/* Derivate */
		chain.add(Convolution.getDerivation(20), 1);
		
		return chain;
	}
}
