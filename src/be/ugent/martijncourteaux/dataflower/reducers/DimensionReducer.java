package be.ugent.martijncourteaux.dataflower.reducers;

import be.ugent.martijncourteaux.dataflower.DataModifier;
import be.ugent.martijncourteaux.dataflower.Vector;
import be.ugent.martijncourteaux.dataflower.VectorQueue;

public class DimensionReducer implements DataModifier
{
	public enum Technique
	{
		DELTA_LENGTH, LENGTH, SUM
	}

	private Technique technique;

	public DimensionReducer(Technique t)
	{
		this.technique = t;
	}

	@Override
	public Vector modify(VectorQueue in)
	{
		if (technique == Technique.DELTA_LENGTH)
		{
			return derivate(in);
		} else if (technique == Technique.LENGTH)
		{
			return length(in);
		} else if (technique == Technique.SUM)
		{
			return sum(in);
		}
		return null;
	}

	public static Vector derivate(VectorQueue data)
	{
		Vector d0 = data.getVector(1);
		Vector d1 = data.getVector(0);
		double sumSq = 0;
		for (int c = 0; c < data.getDim(); ++c)
		{
			double s = (d0.get(c) - d1.get(c));
			sumSq += s * s;
		}
		return Vector.createVector(Math.sqrt(sumSq));
	}

	public static Vector length(VectorQueue data)
	{
		return Vector.createVector(data.getVector(0).length());
	}

	public static Vector sum(VectorQueue data)
	{
		Vector in = data.getVector(0);
		double s = 0;
		for (int c = 0; c < data.getDim(); ++c)
		{
			s += in.get(c);
		}
		return Vector.createVector(s);

	}
}
