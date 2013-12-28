package be.ugent.martijncourteaux.dataflower.unittest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import be.ugent.martijncourteaux.dataflower.Vector;

public class CSVFilteredParser
{

	public static void parseFileVector3Filtered(File file, String filter, VectorData data)
	{
		/* Read data */
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));

			/* Skip first line */
			br.readLine();

			String line;
			while ((line = br.readLine()) != null)
			{
				String[] parts = line.split("[,;]");
				if (filter == null || parts[0].equals(filter))
				{
					double x = Double.parseDouble(parts[2]);
					double y = Double.parseDouble(parts[3]);
					double z = Double.parseDouble(parts[4]);

					data.addVector(Vector.createVector(x, y, z));
				}
			}

			br.close();

		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
