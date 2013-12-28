package be.martijncourteaux.dataflower.reducers;

import be.martijncourteaux.dataflower.DataModifier;
import be.martijncourteaux.dataflower.Vector;
import be.martijncourteaux.dataflower.VectorQueue;

public class OneComponent implements DataModifier
{
	private int comp;
	
	public OneComponent(int comp)
	{
		this.comp = comp;
	}
	
	@Override
	public Vector modify(VectorQueue in)
	{
		return Vector.createVector(in.last().get(comp));
	}

}
