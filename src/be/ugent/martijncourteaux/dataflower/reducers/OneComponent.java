package be.ugent.martijncourteaux.dataflower.reducers;

import be.ugent.martijncourteaux.dataflower.DataModifier;
import be.ugent.martijncourteaux.dataflower.Vector;
import be.ugent.martijncourteaux.dataflower.VectorQueue;

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
