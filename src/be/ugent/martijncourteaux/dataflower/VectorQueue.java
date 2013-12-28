package be.ugent.martijncourteaux.dataflower;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author martijncourteaux
 *
 */
public class VectorQueue
{
	
	private List<Vector> queue;
	private int cap;
	private int dim;
	
	public VectorQueue(int size, int dim)
	{
		this.cap = size;
		this.dim = dim;
		this.queue = new ArrayList<Vector>(size + 1);
	}
	
	public VectorQueue(VectorQueue orig)
	{
		this(orig.cap, orig.dim);
		for (int i = orig.size() - 1; i >= 0; --i)
		{
			Vector v = new Vector(dim);
			Vector o = queue.get(i);
			v.set(o);
			queue.add(v);
		}
	}

	public int getDim()
	{
		return dim;
	}
	
	public int getCapacity()
	{
		return cap;
	}
	
	public int size()
	{
		return queue.size();
	}
	
	public void push(Vector v)
	{
		if (v.getDim() != dim) throw new IllegalArgumentException();
		queue.add(v);
		if (queue.size() == cap + 1)
		{
			queue.remove(0);
		}
	}
	
	public Vector getVector(int index)
	{
		return queue.get(size() - index - 1);
	}

	public Vector last()
	{
		return queue.get(queue.size() - 1);
	}
	
}
