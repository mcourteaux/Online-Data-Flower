package be.martijncourteaux.dataflower.unittest;

import be.martijncourteaux.dataflower.Vector;

public interface DetectionAlgorithm
{
	public void reset();
	public boolean process(Vector v);
	public int getResult();
}
