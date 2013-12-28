package be.martijncourteaux.dataflower.demo;

import java.awt.Font;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import be.martijncourteaux.dataflower.Convolution;
import be.martijncourteaux.dataflower.ModifierChain;
import be.martijncourteaux.dataflower.modifiers.AddModifier;
import be.martijncourteaux.dataflower.modifiers.MaxModifier;
import be.martijncourteaux.dataflower.modifiers.ToleranceModifier;
import be.martijncourteaux.dataflower.plotter.Margin;
import be.martijncourteaux.dataflower.plotter.PlotterSettings;
import be.martijncourteaux.dataflower.plotter.RenderJob;
import be.martijncourteaux.dataflower.plotter.RenderTask;
import be.martijncourteaux.dataflower.reducers.DimensionReducer;
import be.martijncourteaux.dataflower.reducers.DimensionReducer.Technique;
import be.martijncourteaux.dataflower.reducers.OneComponent;

public class ModernMainMartijn
{


	public static void main(String[] args)
	{
		PlotterSettings settings = new PlotterSettings();
		settings.setWidth(2000);
		settings.setHeigth(900);
		settings.setArrowSize(10);
		settings.setMargin(new Margin(12, 0, 50, 0));
		settings.setYAxisIndicationsPosition(1);
		settings.setXFont(new Font("", Font.BOLD, 40));
		settings.setYFont(new Font("", Font.BOLD, 30));

		File outputFolder = new File("renderjobs");
		outputFolder.mkdir();
		File inputFolder = new File(new File("").getAbsoluteFile().getParentFile().getParentFile(), "/Data/raw/");

		Map<String, Boolean> renderKeys = new HashMap<String, Boolean>();
		renderKeys.put("FullAlgo_1", false);
		renderKeys.put("FullAlgo_2", true);
		renderKeys.put("Ruis", false);
		renderKeys.put("FilterDemo", false);

		if (renderKeys.get("FullAlgo_1"))
		{
			int steps = constructChain_LengthBased().modifierCount();
			File inputFile = new File(inputFolder, "3D-Bart-Hand-10.csv");
			for (int i = 0; i <= steps; ++i)
			{
				RenderJob job = new RenderJob(inputFile, null, 420, 650, new File(outputFolder, "FullAlgo1_" + String.format("%02d", i) + ".png"), constructChain_LengthBased());
//				RenderJob job = new RenderJob(inputFile, null, 0, -1, new File(outputFolder, "SpeedTest_" + String.format("%02d", i) + ".png"), constructChain_LengthBased_Speed());
				job.createPlotter(settings);
				job.setHertz(100);
				job.setXInterval(1.0);
				job.setYInterval(4.0);
				if (i == 0)
				{
					job.addRenderTask(new RenderTask(0, 3).setAlpha(0.4f).setLineWidth(3.0f));
				} else
				{
					job.addRenderTask(new RenderTask(0, 3).setAlpha(0.4f).setLineWidth(3.0f));
					job.addRenderTask(new RenderTask(i, 1).setAlpha(0.7f).setLineWidth(6.0f).setDotSize(9.0f));
				}
				job.execute();
			}
		}

		if (renderKeys.get("FullAlgo_2"))
		{
			int steps = constructChain_LengthBased().modifierCount();
			File inputFile = new File(inputFolder, "data_stappen_joggen_hand_broek.csv");
			for (int i = 0; i <= steps; ++i)
			{
				RenderJob job = new RenderJob(inputFile, "stappen hand", 425, 650, new File(outputFolder, "FullAlgo2_" + String.format("%02d", i) + ".png"), constructChain_LengthBased());
				job.createPlotter(settings);
				job.setHertz(100);
				job.setXInterval(1.0);
				job.setYInterval(4.0);
				if (i == 0)
				{
					job.addRenderTask(new RenderTask(0, 3).setAlpha(0.4f).setLineWidth(3.0f));
				} else
				{
					job.addRenderTask(new RenderTask(0, 3).setAlpha(0.4f).setLineWidth(3.0f));
					job.addRenderTask(new RenderTask(i, 1).setAlpha(0.7f).setLineWidth(6.0f).setDotSize(9.0f));
				}
				job.execute();
			}
		}

		if (renderKeys.get("Ruis"))
		{
			{
				RenderJob job = new RenderJob(new File(inputFolder, "../final/final.csv"), "ruis-hand-0", 0, -1, new File(outputFolder, "RuisHand.png"), takeOneComponent(0));
				job.createPlotter(settings);
				job.setHertz(100);
				job.setXInterval(1.0);
				job.setYInterval(0.2);
				job.addRenderTask(new RenderTask(0, 3).setAlpha(0.4f).setLineWidth(6.0f).setDotSize(9.0f));
				job.setReferenceVectorDataMinMax(1);
				job.execute();
			}

			{
				RenderJob job = new RenderJob(new File(inputFolder, "../final/final.csv"), "ruis-tafel-0", 550, 750, new File(outputFolder, "RuisTafel.png"), takeOneComponent(0));
				job.createPlotter(settings);
				job.setHertz(100);
				job.setXInterval(0.5);
				job.setYInterval(0.2);
				job.addRenderTask(new RenderTask(0, 3).setAlpha(0.4f).setLineWidth(6.0f).setDotSize(9.0f));
				job.setReferenceVectorDataMinMax(1);
				job.execute();
			}
		}

		if (renderKeys.get("FilterDemo"))
		{
			{
				RenderJob job = new RenderJob(new File(inputFolder, "3D-Bart-Hand-10.csv"), null, 0, -1, new File(outputFolder, "Bart_Raw.png"), takeOneComponent(2));
				job.createPlotter(settings);
				job.setHertz(100);
				job.setXInterval(2.0);
				job.setYInterval(3.0);
				job.addRenderTask(new RenderTask(1, 1).setAlpha(0.4f).setLineWidth(6.0f).setDotSize(9.0f));
				job.setReferenceVectorDataMinMax(1);
				job.execute();
			}
			{
				RenderJob job = new RenderJob(new File(inputFolder, "3D-Bart-Hand-10.csv"), null, 0, -1, new File(outputFolder, "Bart_LowPass.png"), lowPass(2));
				job.createPlotter(settings);
				job.setHertz(100);
				job.setXInterval(2.0);
				job.setYInterval(3.0);
				job.addRenderTask(new RenderTask(2, 1).setAlpha(0.4f).setLineWidth(6.0f).setDotSize(9.0f));
				job.setReferenceVectorDataMinMax(1);
				job.execute();
			}
			{
				RenderJob job = new RenderJob(new File(inputFolder, "3D-Bart-Hand-10.csv"), null, 0, -1, new File(outputFolder, "Bart_HighPass.png"), highPass(2));
				job.createPlotter(settings);
				job.setHertz(100);
				job.setXInterval(2.0);
				job.setYInterval(3.0);
				job.addRenderTask(new RenderTask(2, 1).setAlpha(0.4f).setLineWidth(6.0f).setDotSize(9.0f));
				job.setReferenceVectorDataMinMax(1);
				job.execute();
			}
		}
	}

	/**
	 * Steps are made when 1th derivate of len is strong positive
	 * 
	 * @param chain
	 */
	private static ModifierChain constructChain_LengthBased()
	{
		ModifierChain chain = new ModifierChain(300, 3);

		/* 3D -> 1D */
		chain.add(new DimensionReducer(Technique.LENGTH), 1);

		/* Smooth */
		{
			Convolution c = new Convolution(50);
			c.setLeftGaussian(1);
			chain.add(c, 1);
		}

		/* Derivate */
		chain.add(Convolution.getDerivation(20), 1);

		/* Denoise */
		chain.add(new AddModifier(-0.3), 1);
		chain.add(new MaxModifier(0), 1);

		/* Smooth */
		{
			Convolution c = new Convolution(50);
			c.setLeftGaussian(2);
			chain.add(c, 1);
		}

		/* Take out no-steps */
		chain.add(new ToleranceModifier(0.5), 1);

		/* Smooth */
		{
			Convolution c = new Convolution(60);
			c.setLeftGaussian(2);
			chain.add(c, 1);
		}

		/* Derivate */
		chain.add(Convolution.getDerivation(20), 1);

		/* Smooth */
		{
			Convolution c = new Convolution(60);
			c.setLeftGaussian(2);
			chain.add(c, 1);
		}

		return chain;
	}

	private static ModifierChain takeOneComponent(int comp)
	{
		ModifierChain chain = new ModifierChain(1, 3);
		chain.add(new OneComponent(comp), 1);
		return chain;
	}

	private static ModifierChain lowPass(int comp)
	{
		ModifierChain chain = new ModifierChain(70, 3);
		chain.add(new OneComponent(comp), 1);
		Convolution c = new Convolution(60);
		c.setLeftGaussian(2);
		chain.add(c, 1);
		return chain;
	}

	private static ModifierChain highPass(int comp)
	{
		ModifierChain chain = new ModifierChain(5, 3);
		chain.add(new OneComponent(comp), 1);
		chain.add(Convolution.getDerivation(1), 1);
		return chain;
	}

}
