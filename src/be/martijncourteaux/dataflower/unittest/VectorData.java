package be.martijncourteaux.dataflower.unittest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import be.martijncourteaux.dataflower.Vector;

public class VectorData implements Iterable<Vector>
{
	
	private int dim;
	private List<Vector> data;

	public VectorData(int dim, int initialCap)
	{
		this.dim = dim;
		this.data = new ArrayList<Vector>(initialCap);
	}
	
	public int getDim()
	{
		return dim;
	}
	
	public void addVector(Vector v)
	{
		if (v.getDim() != dim) throw new IllegalArgumentException("Invalid dim");
		data.add(v);
	}
	
	public void setVector(int i, Vector v)
	{
		data.set(i, v);
	}
	
	public Vector getVector(int i)
	{
		return data.get(i);
	}
		
	public int size()
	{
		return data.size();
	}


	public MinMaxData calculateMinMaxData()
	{
		MinMaxData mmd = new MinMaxData(getDim());
		for (Vector v : data)
		{
			mmd.include(v);
		}
		return mmd;
	}

	public VectorData subRange(int lower, int upper)
	{
		VectorData data = new VectorData(dim, upper - lower);
		for (int i = lower; i < upper; ++i)
		{
			data.addVector(this.getVector(i));
		}
		return data;
	}

	@Override
	public Iterator<Vector> iterator()
	{
		return new Iterator<Vector>()
		{
			
			private int index;
			
			@Override
			public void remove()
			{
				throw new RuntimeException("Operation not supported!");
			}
			
			@Override
			public Vector next()
			{
				return data.get(index++);
			}
			
			@Override
			public boolean hasNext()
			{
				return index < size() - 1;
			}
		};
	}
}
