package be.martijncourteaux.dataflower;

import java.util.Arrays;

/**
 * 
 * @author martijncourteaux
 *
 */
public class Vector
{
	private double[] data;

	public Vector(int dim)
	{
		data = new double[dim];
	}

	public Vector(Vector v)
	{
		this(v.getDim());
		System.arraycopy(v.data, 0, data, 0, v.getDim());
	}

	public int getDim()
	{
		return data.length;
	}

	public void setData(double... d)
	{
		if (d.length != getDim())
			throw new IllegalArgumentException("Dimension mismatch");
		System.arraycopy(d, 0, data, 0, getDim());
	}

	public void set(int i, double d)
	{
		data[i] = d;
	}

	public double get(int i)
	{
		return data[i];
	}

	public void add(Vector v, double w)
	{
		if (v.getDim() != getDim())
			throw new IllegalArgumentException("Dimension mismatch");
		for (int i = 0; i < getDim(); ++i)
		{
			data[i] += v.get(i) * w;
		}
	}

	public double lengthSquared()
	{
		double s = 0;
		for (int i = 0; i < getDim(); ++i)
		{
			double c = data[i];
			s += c * c;
		}
		return s;
	}

	public double length()
	{
		return Math.sqrt(lengthSquared());
	}

	public static Vector createVector(double... data)
	{
		Vector v = new Vector(data.length);
		v.setData(data);
		return v;
	}

	public void scale(double d)
	{
		for (int i = 0; i < getDim(); ++i)
		{
			data[i] *= d;
		}
	}

	public void set(Vector o)
	{
		if (o.getDim() != getDim())
			throw new IllegalArgumentException();
		for (int i = 0; i < getDim(); ++i)
		{
			data[i] = o.data[i];
		}
	}
	
	@Override
	public String toString()
	{
		return "Vector(" + getDim() + ")" + Arrays.toString(data); 
	}

}
