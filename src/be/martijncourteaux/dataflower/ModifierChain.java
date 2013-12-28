package be.martijncourteaux.dataflower;

import java.util.ArrayList;
import java.util.List;

import be.martijncourteaux.dataflower.unittest.VectorData;

/**
 * 
 * @author martijncourteaux
 * 
 */
public class ModifierChain
{
	private List<DataModifier> chain;
	private VectorQueue inputQueue;
	private VectorQueue outputQueue;
	private List<VectorQueue> queues;
	private int queueLength;
	private int activatedModifiers;

	public ModifierChain(int inputQueueLength, int inputDim)
	{
		this.chain = new ArrayList<DataModifier>();
		this.queues = new ArrayList<VectorQueue>();
		this.inputQueue = new VectorQueue(inputQueueLength, inputDim);
		this.activatedModifiers = -1;
		this.queueLength = inputQueueLength;
	}

	public void add(DataModifier dm, int outputDim)
	{
		chain.add(dm);
		queues.add(new VectorQueue(queueLength, outputDim));
	}

	public Vector add(Vector vector)
	{
		inputQueue.push(vector);
		VectorQueue currentQueue = inputQueue;
		for (int m = 0; m < activeModifierCount(); ++m)
		{
			DataModifier dm = chain.get(m);
			VectorQueue q = queues.get(m);

			Vector output = dm.modify(currentQueue);
			/* Once the chain could not proceed, stop and let it continue later */
			if (output == null)
			{
				return null;
			}
			q.push(output);
			currentQueue = q;
		}
		outputQueue = currentQueue;
		return outputQueue.last();
	}

	public VectorData add(VectorData data)
	{
		VectorData vd = new VectorData(queues.get(activeModifierCount() - 1).getDim(), data.size());
		for (Vector v : data)
		{
			Vector output = add(v);
			if (output != null)
				vd.addVector(output);
		}
		return vd;
	}

	public VectorQueue getOutputQueue()
	{
		return outputQueue;
	}

	public VectorData applyModifier(VectorData data, int modifierIndex)
	{
		DataModifier modifier = chain.get(modifierIndex);
		VectorData vd = new VectorData(getOuputDimForModifier(modifierIndex), data.size());
		VectorQueue queue = new VectorQueue(queueLength, getInputDimForModifier(modifierIndex));
		for (Vector v : data)
		{
			queue.push(v);
			Vector output = modifier.modify(queue);
			if (output != null)
			{
				vd.addVector(output);
			}
		}
		return vd;
	}

	public int modifierCount()
	{
		return chain.size();
	}

	public int activeModifierCount()
	{
		return activatedModifiers == -1 ? chain.size() : activatedModifiers;
	}

	public void stopAfter(int steps)
	{
		this.activatedModifiers = steps;
	}

	public int getInputDimForModifier(int index)
	{
		if (index == 0)
			return inputQueue.getDim();
		return queues.get(index - 1).getDim();
	}

	public int getOuputDimForModifier(int index)
	{
		return queues.get(index).getDim();
	}

}
