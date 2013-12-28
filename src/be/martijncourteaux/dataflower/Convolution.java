package be.martijncourteaux.dataflower;

/**
 * 
 * @author martijncourteaux
 * 
 */
public class Convolution implements DataModifier
{

	protected double weights[];
	protected double totalWeight;
	protected boolean requiresAllWeigths;

	public Convolution(int weights)
	{
		this.weights = new double[weights];
	}

	public void setWeight(int i, double w)
	{
		weights[i] = w;
	}

	@Override
	public Vector modify(VectorQueue in)
	{
		if (in.size() >= weights.length || !requiresAllWeigths)
		{
			Vector outData = new Vector(in.getDim());
			double sum = 0.0;
			for (int p = 0; p < weights.length && p < in.size(); ++p)
			{
				Vector vIn = in.getVector(p);
				double w = weights[weights.length - p - 1];
				outData.add(vIn, w);
				sum += w;
			}
			if (Math.abs(totalWeight - sum) > 0.00001 && totalWeight != 0.0 && sum != 0.0)
			{
				outData.scale(totalWeight / sum);
			}
			return outData;
		} else
		{
			return null;
		}
	}

	public void scale(double f)
	{
		for (int i = 0; i < weights.length; ++i)
		{
			weights[i] *= f;
		}
		
	}

	public void power(double e)
	{
		for (int i = 0; i < weights.length; ++i)
		{
			weights[i] = Math.pow(weights[i], e);
		}
		normalize();
	}

	public void normalize()
	{
		double sum = 0.0d;
		for (int i = 0; i < weights.length; ++i)
		{
			sum += weights[i];
		}
		scale(1.0d / sum);
		totalWeight = 1.0;
	}

	public void setUniform()
	{
		double w = 1.0d / weights.length;
		for (int i = 0; i < weights.length; ++i)
		{
			weights[i] = w;
		}
		totalWeight = 1.0;
	}

	public void setTriangle()
	{
		double sum = 0.0d;
		for (int i = 0; i < weights.length; ++i)
		{
			double x = (double) i / (weights.length - 1);
			double w = Math.abs(2 * x - 1) + 1;
			sum += w;
			weights[i] = w;
		}
		scale(1.0d / sum);
		totalWeight = 1.0;
	}

	public void setInvertedTriangle()
	{
		double sum = 0.0d;
		for (int i = 0; i < weights.length; ++i)
		{
			double x = (double) i / (weights.length - 1);
			double w = -Math.abs(2 * x - 1);
			sum += w;
			weights[i] = w;
		}
		scale(1.0d / sum);
		totalWeight = 1.0;
	}

	public void setLinearAscending()
	{
		double sum = 0.0d;
		for (int i = 0; i < weights.length; ++i)
		{
			double x = (double) i / (weights.length - 1);
			double w = x;
			sum += w;
			weights[i] = w;
		}
		scale(1.0d / sum);
		totalWeight = 1.0;
	}

	public void setLinearDescending()
	{
		double sum = 0.0d;
		for (int i = 0; i < weights.length; ++i)
		{
			double x = (double) i / (weights.length - 1);
			double w = 1 - x;
			sum += w;
			weights[i] = w;
		}
		scale(1.0d / sum);
		totalWeight = 1.0;
	}

	public void setArcAscending()
	{
		double sum = 0.0d;
		for (int i = 0; i < weights.length; ++i)
		{
			double x = (double) i / (weights.length - 1);
			x = 1 - x;
			double w = Math.sqrt(1 - x * x);
			sum += w;
			weights[i] = w;
		}
		scale(1.0d / sum);
		totalWeight = 1.0;
	}

	public void setArcDescending()
	{
		double sum = 0.0d;
		for (int i = 0; i < weights.length; ++i)
		{
			double x = (double) i / (weights.length - 1);
			double w = Math.sqrt(1 - x * x);
			sum += w;
			weights[i] = w;
		}
		scale(1.0d / sum);
		totalWeight = 1.0;
	}

	public void setGaussian(double compression)
	{
		double sum = 0.0d;
		for (int i = 0; i < weights.length; ++i)
		{
			double x = (double) i / (weights.length - 1);
			x = 2 * x - 1;
			x *= compression;
			double w = Math.exp(-x * x);
			sum += w;
			weights[i] = w;
		}
		scale(1.0d / sum);
		totalWeight = 1.0;
	}

	public void setLeftGaussian(double compression)
	{
		double sum = 0.0d;
		for (int i = 0; i < weights.length; ++i)
		{
			double x = (double) i / (weights.length - 1);
			x = 2 * x - 2;
			x *= compression;
			double w = Math.exp(-x * x);
			sum += w;
			weights[i] = w;
		}
		scale(1.0d / sum);
		totalWeight = 1.0;
	}

	public void bandpass(double top)
	{
		double sum = 0.0d;
		for (int i = 0; i < weights.length; ++i)
		{
			double x = (double) i / (weights.length - 1);
			double ly = x / (0.5 - top * 0.5);
			double ry = x / (0.5 - top * 0.5);
			double w = Math.min(Math.min(ly, ry), 1.0d);
			sum += w;
			weights[i] = w;
		}
		scale(1.0d / sum);
		totalWeight = 1.0;
	}
	
	public void calculateTotalWeight()
	{
		double sum = 0.0d;
		for (int i = 0; i < weights.length; ++i)
		{
			sum += weights[i];
		}
		totalWeight = sum;
	}

	public static Convolution getDerivation()
	{
		return getDerivation(1);
	}
	
	public static Convolution getDerivation(double scale)
	{
		Convolution c = new Convolution(2);
		c.setWeight(0, -scale);
		c.setWeight(1, scale);
		c.totalWeight = 0.0;
		c.requiresAllWeigths = true;
		return c;
	}

	public static DataModifier getScale(double d)
	{
		Convolution c = new Convolution(1);
		c.setWeight(0, d);
		c.totalWeight = d;
		return c;
	}


}
