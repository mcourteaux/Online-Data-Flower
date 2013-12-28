package be.martijncourteaux.dataflower.modifiers;

import be.martijncourteaux.dataflower.DataModifier;
import be.martijncourteaux.dataflower.Vector;
import be.martijncourteaux.dataflower.VectorQueue;

/**
 * 
 * @author martijncourteaux
 *
 */
public class MaxModifier implements DataModifier
{
	private double max;

	public MaxModifier(double max)
	{
		this.max = max;
	}

	public Vector modify(VectorQueue in)
	{

		Vector outData = new Vector(in.getDim());
		Vector inData = in.last();
		for (int c = 0; c < in.getDim(); ++c)
		{
			outData.set(c, Math.max(max, inData.get(c)));
		}

		return outData;
	}

}
