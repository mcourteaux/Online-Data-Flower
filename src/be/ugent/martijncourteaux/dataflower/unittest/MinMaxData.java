package be.ugent.martijncourteaux.dataflower.unittest;

import be.ugent.martijncourteaux.dataflower.Vector;


public class MinMaxData
{

	public double min[];
	public double max[];
	public int dim;
	
	public MinMaxData(int dim)
	{
		this.dim = dim;
	}
	
	public void include(Vector v)
	{
		if (min == null)
		{
			min = new double[dim];
			max = new double[dim];
			for (int i = 0; i < v.getDim(); ++i)
			{
				min[i] = v.get(i);
				max[i] = v.get(i);
			}
		}
		for (int i = 0; i < v.getDim(); ++i)
		{
			min[i] = Math.min(v.get(i), min[i]);
			max[i] = Math.max(v.get(i), max[i]);
		}
	}
	
	public double getMinValue()
	{
		double v = min[0];
		for (int i = 1; i < dim; ++i)
		{
			v = Math.min(v, min[i]);
		}
		return v;
	}
	
	public double getMaxValue()
	{
		double v = max[0];
		for (int i = 1; i < dim; ++i)
		{
			v = Math.max(v, max[i]);
		}
		return v;
	}
}
