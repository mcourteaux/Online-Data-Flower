package be.ugent.martijncourteaux.dataflower.modifiers;

import be.ugent.martijncourteaux.dataflower.DataModifier;
import be.ugent.martijncourteaux.dataflower.ModifierChain;
import be.ugent.martijncourteaux.dataflower.Vector;
import be.ugent.martijncourteaux.dataflower.VectorQueue;

/**
 * 
 * @author martijncourteaux
 *
 */
public class ComponentwiseOperation implements DataModifier
{

	public enum Operation
	{
		ADD, SUBTRACT, MULTIPLY, DIVIDE, POWER
	}

	private Operation o;
	private ModifierChain chain0, chain1;

	/**
	 * Computes the inproduct or dotproduct of the two vectors and divides by the length of vector of chain0 to normalize.
	 * 
	 * @param chain0
	 * @param chain1
	 */
	public ComponentwiseOperation(Operation o, ModifierChain chain0, ModifierChain chain1)
	{
		this.chain0 = chain0;
		this.chain1 = chain1;
		this.o = o;
	}

	private double apply(double l, double r)
	{
		switch (o)
		{
		case ADD:
			return l + r;
		case SUBTRACT:
			return l - r;
		case MULTIPLY:
			return l * r;
		case DIVIDE:
			return l / r;
		case POWER:
			return Math.pow(l, r);
		}
		return 0;
	}

	@Override
	public Vector modify(VectorQueue in)
	{
		Vector v0 = chain0.add(in.last());
		Vector v1 = chain1.add(in.last());

		Vector outV = new Vector(in.getDim());
		for (int c = 0; c < in.getDim(); ++c)
		{
			outV.set(c, apply(v0.get(c), v1.get(c)));
		}
		return outV;
		
	}

}
