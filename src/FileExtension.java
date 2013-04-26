import java.io.*;
import java.lang.String;

public class FileExtension
{	
	public String extension;
	
    public FileExtension(File file)
    {
    	getExtension(file);
    }
    public String getExtension(File file)
    {
    	String name = file.getName();

    	int dotPos = name.lastIndexOf(".");
  
		extension = name.substring(dotPos);
		
		//System.out.println(name);test stub for the file name
		//System.out.println(extension); test stub for the file extension
    
		return extension;
    }
}