package be.martijncourteaux.dataflower.unittest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import be.martijncourteaux.dataflower.Vector;

public class CSVDataDumper
{

	public static void dumpVector(VectorData data, File file, int hertz)
	{
		try
		{
			PrintWriter ps = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
			for (int i = 0; i < data.size(); ++i)
			{
				Vector v = data.getVector(i);
				ps.print((double) i / hertz);
				for (int c = 0; c < data.getDim(); ++c)
				{
					ps.print(";");
					ps.print(v.get(c));
				}
				ps.println();
			}
			ps.flush();
			ps.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}
}
