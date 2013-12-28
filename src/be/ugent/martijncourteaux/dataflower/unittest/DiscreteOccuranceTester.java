package be.ugent.martijncourteaux.dataflower.unittest;

import be.ugent.martijncourteaux.dataflower.ModifierChain;
import be.ugent.martijncourteaux.dataflower.Vector;
import be.ugent.martijncourteaux.dataflower.plotter.Plotter;

public class DiscreteOccuranceTester
{

	public static TestResult test(String name, VectorData input, ModifierChain algorithm, DetectionAlgorithm detectionAlgorithm, int expected, Plotter plotter)
	{
		detectionAlgorithm.reset();
		int counter = 0;
		VectorData vd = null;
		if (plotter != null)
			vd = new VectorData(1, input.size());

		for (Vector v : input)
		{
			Vector output = algorithm.add(v);
			if (output != null)
			{
				detectionAlgorithm.process(output);
				if (plotter != null)
				{
					vd.addVector(output);
				}
				counter++;
			}
		}

		if (plotter != null)
		{
			plotter.plotVectorData(vd);
		}

		int result = detectionAlgorithm.getResult();
		String code;
		if (result != expected)
		{
			code = "FAIL";
		} else
		{
			code = "SUCC";
		}

		System.out.printf("[%s]   %5d / %5d    (error = %2d)  [%4d] %s\n", code, result, expected, result - expected, counter, name);
		return new TestResult(expected, result);
	}

}
