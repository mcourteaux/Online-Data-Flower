package be.martijncourteaux.dataflower.modifiers;

import be.martijncourteaux.dataflower.DataModifier;
import be.martijncourteaux.dataflower.Vector;
import be.martijncourteaux.dataflower.VectorQueue;

/**
 * 
 * @author martijncourteaux
 *
 */
public class AbsModifier implements DataModifier
{
	public AbsModifier()
	{
	}

	public Vector modify(VectorQueue in)
	{
		Vector outData = new Vector(in.getDim());
		Vector inData = in.last();
		for (int c = 0; c < in.getDim(); ++c)
		{
			outData.set(c, Math.abs(inData.get(c)));
		}

		return outData;
	}

}
