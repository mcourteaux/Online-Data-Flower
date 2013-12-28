package be.ugent.martijncourteaux.dataflower.modifiers;

import be.ugent.martijncourteaux.dataflower.DataModifier;
import be.ugent.martijncourteaux.dataflower.Vector;
import be.ugent.martijncourteaux.dataflower.VectorQueue;

/**
 * 
 * @author martijncourteaux
 *
 */
public class AddModifier implements DataModifier
{
	public double c;

	public AddModifier(double c)
	{
		this.c = c;
	}

	public Vector modify(VectorQueue in)
	{
		Vector outData = new Vector(in.getDim());
		Vector inData = in.last();
		for (int c = 0; c < in.getDim(); ++c)
		{
			outData.set(c, this.c + inData.get(c));
		}

		return outData;
	}

}
