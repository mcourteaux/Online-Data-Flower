package be.ugent.martijncourteaux.dataflower.unittest;

import be.ugent.martijncourteaux.dataflower.Vector;

public interface DetectionAlgorithm
{
	public void reset();
	public boolean process(Vector v);
	public int getResult();
}
