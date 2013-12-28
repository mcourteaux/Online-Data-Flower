package be.martijncourteaux.dataflower.reducers;

import be.martijncourteaux.dataflower.DataModifier;
import be.martijncourteaux.dataflower.ModifierChain;
import be.martijncourteaux.dataflower.Vector;
import be.martijncourteaux.dataflower.VectorQueue;

/**
 * 
 * @author martijncourteaux
 *
 */
public class DotProduct implements DataModifier
{

	private ModifierChain chain0, chain1;

	/**
	 * Computes the inproduct or dotproduct of the two vectors and divides by the length of vector of chain0 to normalize.
	 * 
	 * @param chain0
	 * @param chain1
	 */
	public DotProduct(ModifierChain chain0, ModifierChain chain1)
	{
		this.chain0 = chain0;
		this.chain1 = chain1;
	}

	@Override
	public Vector modify(VectorQueue in)
	{
		Vector v0 = chain0.add(in.last());
		Vector v1 = chain1.add(in.last());

		double dp = 0.0;
		for (int c = 0; c < in.getDim(); ++c)
		{
			dp += v0.get(c) * v1.get(c);
		}
		double v0l = v0.length();
		if (v0l > 0.001)
		{
			dp /= v0l;
		}
		return (Vector.createVector(dp));

	}

}
