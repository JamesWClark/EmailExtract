import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.awt.Dimension;

public class FileLoader 
{
	private static JFrame frame;
	private static JButton btnBrowse;
	private static JButton btnExtract;
	private static JTextField fileIn;
	private static File file;
	private static JCheckBox cbPurgeAllDuplicates;
	
	public static void main(String[] args) 
	{
		frame = new JFrame("Flat Text Email Extract");
		//frame.setSize(700, 300);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Box boxPanel = new Box(BoxLayout.Y_AXIS);
		
		//textual instruction
		boxPanel.add(new JLabel("Step 1: Click \"Browse\" and select a .csv or .txt file"));
		boxPanel.add(new JLabel("Step 2: Click \"Extract Emails\""));
		boxPanel.add(new JLabel("Step 3: Extracted emails are output and saved to \"emails.csv\""));
		boxPanel.add(new JLabel("  * Wherever this program is located is where the emails.csv file will be saved"));
		boxPanel.add(new JLabel("  * To use the extract again, move, rename, or delete the emails.csv file"));
		boxPanel.add(new JLabel("  * To prevent loss of data, only one emails.csv file can exist at the output location"));
		boxPanel.add(new JLabel(" "));
		boxPanel.add(new JLabel(" "));
		
		//form controls
		JPanel input = new JPanel(new FlowLayout());
		fileIn = new JTextField(25);
		btnBrowse = new JButton("Browse");
		input.add(new JLabel("Source File: "));
		input.add(fileIn);
		input.add(btnBrowse);
		
		boxPanel.add(input);
		
		//the action listener that opens a file browser
		btnBrowse.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser fc = new JFileChooser();
				
				if(e.getSource() == btnBrowse)
				{
					int returnVal = fc.showOpenDialog(frame);
					
					if(returnVal == JFileChooser.APPROVE_OPTION)
					{
						file = fc.getSelectedFile();
						fileIn.setText(file.getAbsolutePath());
					}
				}
			}
		});

		//more controls added to the form - at the bottom
		btnExtract = new JButton("Extract Emails");
		cbPurgeAllDuplicates = new JCheckBox("Completely delete any duplicates. Do not include them in extract.");
		boxPanel.add(new JLabel(" "));
		boxPanel.add(btnExtract);
		boxPanel.add(cbPurgeAllDuplicates);
		
		btnExtract.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				new CSVReader(frame, file, cbPurgeAllDuplicates.isSelected());
			}
		});
		
		frame.add(boxPanel);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		
		//http://www.java-forums.org/awt-swing/3491-jframe-center-screen.html
	    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();    
	    int w = frame.getSize().width;
	    int h = frame.getSize().height;
	    int x = (dim.width-w)/2;
	    int y = (dim.height-h)/2;
	    frame.setLocation(x, y);
	}
}
