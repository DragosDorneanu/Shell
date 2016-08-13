package shellFrameCharacteristics;
import customExceptions.*;
import functions.*;
import java.awt.Color;
import java.awt.Frame;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JScrollPane;
import java.util.StringTokenizer;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.File;
import java.lang.StringBuilder;

public class ShellFrame extends Frame 
{
	public static final long serialVersionUID = 0L;
	public static StringBuilder command;
	public static JTextArea commandArea;
	public static byte argc;
	public static Font textFont;
	private JScrollPane scroll;
	public static Frame thisShellFrame;
	public static Path currentPath;
		
	private void initShellFrameCharacteristics()
	{
		this.setBounds(200, 200, 850, 400);
		this.setResizable(false);
		this.setTitle("My Shell");
		this.addWindowListener(new WindowListenerClass());
		currentPath = FileSystems.getDefault().getPath("C:", "Users", "Skynet");
		textFont = new Font("Dornilian", Font.BOLD, 14);
	}
	
	private void initCommandArea()
	{
		commandArea = new JTextArea(this.getWidth(), this.getHeight());
		commandArea.setBackground(Color.black);
		commandArea.setFont(textFont);
		commandArea.setForeground(Color.white);
		commandArea.setText("Copyrights reserved to Dragos!\nType \"man\" for a list of available functions or type \"man functionName\" for information about a specific function.\n\n" + currentPath + " > ");
		commandArea.setEditable(false);
		commandArea.addKeyListener(new KeyListenerClass());
		scroll = new JScrollPane(commandArea);
		this.add(scroll);
	}
	
	ShellFrame()
	{
		command = new StringBuilder();
		argc = 0;
		initShellFrameCharacteristics();
		initCommandArea();
		thisShellFrame = this;
	}
	
	public static void solve() throws Exception
	{
		String function;
		StringTokenizer st = new StringTokenizer(command.toString());
		if(st.hasMoreTokens())
		{
			function = st.nextToken().trim();
			if(function.equals("exit"))
				System.exit(0);
			else if(function.equals("clear"))
			{
				if(argc == 0)
					ClearFunction.clear();
				else
					throw new ClearException();
			}
			else if(function.equals("man"))
			{
				if(argc == 0)
					ManFunction.man();
				else if(argc == 1)
					ManFunction.manSpecificFunction(st.nextToken().trim());
				else
					throw new ManException();
			}
			else if(function.equals("list"))
			{
				if(argc == 0)
					ListDirectoryFunction.listCurrentDirectory();
				else
					ListDirectoryFunction.listParameterDirectory(st.nextToken("\n").trim());
			}
			else if(function.equals("cd"))
			{
				String directoryName = st.nextToken("\n").trim();
				if(directoryName.equals(".."))
					ChangeDirectoryFunction.changeCurrentDirectoryToParentDirectory();
				else
				{
					Path newDirectoryPath = FileSystems.getDefault().getPath(currentPath.toString(), directoryName);
					File newDirectory = newDirectoryPath.toFile();
					if(newDirectory.exists() && newDirectory.isDirectory())
						ChangeDirectoryFunction.changeDirectory(newDirectoryPath);
					else
						throw new CDException();
				}
			}
			else if(function.equals("open"))
			{
				Path toOpenFilePath = FileSystems.getDefault().getPath(currentPath.toString(), st.nextToken("\n").trim());
				if(Files.exists(toOpenFilePath))
					OpenFileFunction.open(toOpenFilePath.toFile());
				else
					throw new OpenException();
			}
			else if(function.equals("find"))
				FindFunction.find(currentPath, st.nextToken("\n").trim(), 1);
			else if(function.equals("cat"))
				CatFunction.cat(new File(st.nextToken("\n").trim()));
			else if(function.length() == 2 && function.charAt(1) == ':')
			{
				 Path possibleLocalDisk = FileSystems.getDefault().getPath(function); 
				 if(Files.exists(possibleLocalDisk))
					ChangeDirectoryFunction.changeDirectory(possibleLocalDisk);
				 else
					throw new CDException();
			}
			else throw new FunctionNotFoundException();
		}
	}
	
	public static void main(String args[])
	{
		ShellFrame frame = new ShellFrame();
		frame.setVisible(true);
	}
}

// 1) implementeaza pipe-uri ("|")
