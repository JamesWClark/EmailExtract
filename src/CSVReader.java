import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class CSVReader 
{
	private static Scanner scanner = null;
	private static ArrayList<String> emails = null;
	private static FileExtension fe;
	private static boolean isValidScanner = false;
		
	//the boolean here isPurgingDuplicates is passed from FileLoader (parentFrame) checkbox
	public CSVReader(JFrame parentFrame, File input, boolean isPurgingDuplicates)
	{
		emails = new ArrayList<String>();
		
		setScanner(parentFrame, input);

		if(isValidScanner)
		{
			getEmails(); // fills ArrayList emails with email values only
			
			ArrayList<String> clean = new ArrayList<String>();
			ArrayList<String> duplicatesFound = new ArrayList<String>();
			
			for(int i = 0; i < emails.size(); i++)
			{
				//builds a list of unique addresses
				if(!clean.contains(emails.get(i)) /*DON'T THINK THIS IS NECESSARY&& emails.get(i).length() != 0*/)
				{
					clean.add(emails.get(i));
				}
				//builds a list of duplicates only if duplicates need to be purged
				else if(isPurgingDuplicates)
				{
					//the list of duplicates may not contain duplicates!
					if(!duplicatesFound.contains(emails.get(i)))
					{
						duplicatesFound.add(emails.get(i));
					}
					
				}
			}
			
			//if purging duplicates
			if(isPurgingDuplicates)
			{
				for(int i = 0; i < duplicatesFound.size(); i++)
				{
					clean.remove(duplicatesFound.get(i));
				}
			}
			
			/*Iterator<String> it = clean.iterator();
			while(it.hasNext())
				System.out.println(it.next());*/// this is test stub for outputting the scanned arraylist
			
			Collections.sort(clean);
			
			new CSVWriter(parentFrame, clean);	
		}
	}
	
	private void setScanner(Frame parent, File input)
	{
		try
		{
			fe = new FileExtension(input);
			
			//check if the file is a CSV
			if(fe.extension.equalsIgnoreCase(".csv") || fe.extension.equals(".txt"))
			{
				scanner = new Scanner(new BufferedReader(new FileReader(input)));
				//comma-separated value documented has two delimiters: comma and newline
				scanner.useDelimiter("\t|,|\n| ");
				isValidScanner = true;
			}
			//if it's not a CSV, post an error message
			else
			{
				JOptionPane.showMessageDialog(parent, "Error: Invalid File Type " + fe.extension + "\n\n" + 
						input.getName() + " is not a valid .csv file.");
				isValidScanner = false;
			}
		}
		catch(FileNotFoundException ex)
		{
			JOptionPane.showMessageDialog(parent, "Exception: File Not Found:\n" + ex.getMessage());
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(parent, "Click \"Browse\" to select a file.");
		}	
	}
	private void getEmails()
	{
		String line = null;
		//int cellCount = 0;
		int locAt = 0; //index of @ symbol in a line
		boolean hasAt = false;
		boolean hasDot = false;

		while(scanner.hasNext())
		{
			//cellCount++;
			
			line = scanner.next();
		
			for(int i = 0; i < line.length(); i++)
			{
				if(i > 0 && line.charAt(i) == '@')
				{
					locAt = i;
					hasAt = true;
				}
				if(i > locAt + 1 && hasAt == true && line.charAt(i) == '.')
				{
					hasDot = true;
				}
			}
			//http://www.coderanch.com/t/426877/java/java/remove-carriage-return-string
			if(hasAt == true && hasDot == true)
			{
				line = line.replaceAll("[\r\n]", "");
				line = removePunctuatedEndPoints(line);
				emails.add(line.toLowerCase());
			}
		
			hasAt = false;
			hasDot = false;
		}
		//System.out.println(cellCount); //a stub test to verify the entire table dimension is captured
	}
	private String removePunctuatedEndPoints(String email){
		String clean = email;
		
		char first = clean.charAt(0);
		char last = clean.charAt(clean.length()-1);
		
		// if it is NOT starting with alphabet character, purge the first character
		if(!isAlphabet(first)){
			clean = clean.substring(1);//removes first character
			removePunctuatedEndPoints(clean);
		}
		if(!isAlphabet(last)){
			clean = clean.substring(0, clean.length()-1);//removes last character
			removePunctuatedEndPoints(clean);
		}
		
		return clean;
	}
	private boolean isAlphabet(char c){
		switch(c)
		{
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				return true;
		}
		return false;
	}
}
