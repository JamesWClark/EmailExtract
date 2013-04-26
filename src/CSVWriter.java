import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class CSVWriter 
{
	private static File output;
	
	public CSVWriter(JFrame frame, ArrayList<String> list)
	{		
		output = new File("emails.csv");
		
		PrintWriter writer = null;
	
		try
		{
			// this checks for pre-existence of an output file.
			// it's a safeguard to prevent deleting potentially useful data
			if(output.exists())
			{
				JOptionPane.showMessageDialog(frame, "An output file exists in this location.\nMove, rename, or delete the existing emails.csv file.");
			}
			else
			{
				writer = new PrintWriter(output);	
				Iterator<String> it = list.iterator();
				
				for(int i = 1; i < list.size(); i++)
				{
					writer.println(it.next() + ",");
				}
				writer.print(it.next());
				JOptionPane.showMessageDialog(frame, "Extraction Complete!");
			}
		}
		catch(IOException ex)
		{
			JOptionPane.showMessageDialog(frame, "IOException:\n" + ex.getMessage());
		}
		writer.close();
	}
}
