package be.martijncourteaux.dataflower.modifiers;

import be.martijncourteaux.dataflower.DataModifier;
import be.martijncourteaux.dataflower.Vector;
import be.martijncourteaux.dataflower.VectorQueue;

public class ToleranceModifier implements DataModifier
{

	private double t;

	public ToleranceModifier(double tolerance)
	{
		this.t = tolerance;
	}

	private double tol(double x)
	{
		return Math.abs(x) < t ? 0 : x;
	}

	public Vector modify(VectorQueue in)
	{
		Vector outData = new Vector(in.getDim());
		Vector inData = in.getVector(0);
		for (int c = 0; c < in.getDim(); ++c)
		{
			outData.set(c, tol(inData.get(c)));
		}
		return outData;
	}

}
