package be.martijncourteaux.dataflower.reducers;

import be.martijncourteaux.dataflower.Convolution;
import be.martijncourteaux.dataflower.DataModifier;
import be.martijncourteaux.dataflower.ModifierChain;
import be.martijncourteaux.dataflower.Vector;
import be.martijncourteaux.dataflower.VectorQueue;

/**
 * 
 * @author martijncourteaux
 *
 */
public class RelativeZeroFinder implements DataModifier
{

	private int weights;
	private double toleranceInv;
	private Vector mean;
	private int meanCount;
	private ModifierChain preChain;

	public RelativeZeroFinder(int weights, double tolerance, int dim)
	{
		this.weights = weights;
		this.toleranceInv = 1.0 / tolerance;
		this.mean = new Vector(dim);
		this.meanCount = 0;
		this.preChain = new ModifierChain(weights, dim);
		this.preChain.add(Convolution.getDerivation(), dim);
		{
			Convolution c = new Convolution(weights);
			c.setUniform();
			preChain.add(c, dim);
		}
	}

	@Override
	public Vector modify(VectorQueue in)
	{
		Vector v = in.getVector(0);
		Vector diff = preChain.add(v);

		double weight = 1.0;
		if (meanCount == weights)
		{
			double length = diff.length();
			weight = Math.exp(-length * toleranceInv);
		}

		mean.scale(meanCount);
		mean.add(v, weight);
		mean.scale(1.0 / (meanCount + weight));
		meanCount = Math.min(weights, meanCount + 1);

		return new Vector(mean);
	}

}
