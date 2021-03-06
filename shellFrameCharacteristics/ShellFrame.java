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
		this.setBounds(200, 200, 830, 450);
		this.setTitle("My Shell");
		this.addWindowListener(new WindowListenerClass());
		currentPath = FileSystems.getDefault().getPath("C:");
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
	
	private static void tryToExecuteClearFunction() throws ClearException
	{
		if(argc == 0)
			ClearFunction.clear();
		else
			throw new ClearException();
	}
	
	private static void tryToExecuteManFunction(StringTokenizer st) throws Exception
	{
		if(argc == 0)
			ManFunction.man();
		else if(argc == 1)
			ManFunction.manSpecificFunction(st.nextToken().trim());
		else
			throw new ManException();
	}
	
	private static void tryToExecuteListFunction(StringTokenizer st) throws Exception
	{
		if(argc == 0)
			ListDirectoryFunction.listCurrentDirectory();
		else
			ListDirectoryFunction.listParameterDirectory(st.nextToken("\n").trim());
	}
	
	private static void tryToExecuteCdFunction(StringTokenizer st) throws CDException
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
	
	private static void tryToExecuteOpenFileFunction(StringTokenizer st) throws Exception
	{
		Path toOpenFilePath = FileSystems.getDefault().getPath(currentPath.toString(), st.nextToken("\n").trim());
		if(Files.exists(toOpenFilePath))
			OpenFileFunction.open(toOpenFilePath.toFile());
		else
			throw new OpenException();
	}
	
	private static void tryToExecuteMkdirFunction(StringTokenizer st) throws Exception
	{
		if(argc == 0)
			throw new MkdirException();
		else 
			MakeDirectoryFunction.mkdir(st.nextToken("\n").trim());
	}
	
	private static void tryToExecuteRemoveFunction(StringTokenizer st) throws Exception
	{
		if(argc == 0)
			throw new RemoveException();
		else
		{
			String nextToken = st.nextToken().trim();
			if(nextToken.equals("-r"))
				RemoveFileFunction.deleteArgumentDirectory(st.nextToken("\n").trim());
			else if(st.hasMoreTokens())
				RemoveFileFunction.remove(nextToken + ' ' + st.nextToken("\n").trim());
			else
				RemoveFileFunction.remove(nextToken);
		}
	}
	
	private static void tryToExecuteCreateFileFunction(StringTokenizer st) throws Exception
	{
		if(argc == 0)
			throw new CreateFileException();
		else
			CreateFileFunction.createFile(st.nextToken("\n").trim());
	}
	
	private static void tryToChangeCurrentDirectoryToASpecificLocalDisk(String function) throws CDException
	{
		Path possibleLocalDisk = FileSystems.getDefault().getPath(function); 
		 if(Files.exists(possibleLocalDisk))
			ChangeDirectoryFunction.changeDirectory(possibleLocalDisk);
		 else
			throw new CDException();
	}
	
	private static void tryToExecuteFindFunction(StringTokenizer st) throws Exception
	{
		Path resultPath;
		String arguments = st.nextToken("\n").trim().toLowerCase();
		if(arguments.charAt(0) != '-')
		{
			resultPath = FindFunction.find(currentPath, arguments);
			if(resultPath != null)
				ShellFrame.commandArea.setText(ShellFrame.commandArea.getText() + "\nSearched File found : " + resultPath);
			else
				ShellFrame.commandArea.setText(ShellFrame.commandArea.getText() + "\nSearched File was not found\n");

		}
		else FindFunction.findWithSpecificOptions(currentPath, arguments);
	}
	
	private static void tryToExecuteCopyFunction(StringTokenizer st) throws Exception
	{
		if(argc <= 1)
			throw new CopyFunctionException();
		else
			CopyFunction.copy(st.nextToken("\n").trim());
	}
	
	private static void tryToExecuteGrepFunction(StringTokenizer st) throws GrepException
	{
		if(argc == 0)
			throw new GrepException();
		else if(argc == 1)
			GrepFunction.grepPattern(st.nextToken("\n").trim());
		else
			GrepFunction.grep(st.nextToken("\n").trim());
	}
	
	private static void tryToExecuteRenameFunction(StringTokenizer st) throws RenameException
	{
		if(argc <= 1)
			throw new RenameException();
		else
			RenameFunction.rename(st.nextToken("\n").trim());
	}
	
	private static void tryToExecuteMoveFunction(StringTokenizer st) throws Exception
	{
		if(argc <= 1)
			throw new MoveException();
		else
			MoveFunction.move(st.nextToken("\n").trim());
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
				tryToExecuteClearFunction();
			else if(function.equals("man"))
				tryToExecuteManFunction(st);
			else if(function.equals("list"))
				tryToExecuteListFunction(st);
			else if(function.equals("cd"))
				tryToExecuteCdFunction(st);
			else if(function.equals("open"))
				tryToExecuteOpenFileFunction(st);
			else if(function.equals("find"))
				tryToExecuteFindFunction(st);
			else if(function.equals("cat"))
				CatFunction.cat(st.nextToken("\n").trim());
			else if(function.equals("mkdir"))
				tryToExecuteMkdirFunction(st);
			else if(function.equals("remove"))
				tryToExecuteRemoveFunction(st);
			else if(function.equals("create"))
				tryToExecuteCreateFileFunction(st);
			else if(function.equals("cp"))
				tryToExecuteCopyFunction(st);
			else if(function.equals("grep"))
				tryToExecuteGrepFunction(st);
			else if(function.equals("rename"))
				tryToExecuteRenameFunction(st);
			else if(function.equals("mv"))
				tryToExecuteMoveFunction(st);
			else if(function.length() == 2 && function.charAt(1) == ':')
				tryToChangeCurrentDirectoryToASpecificLocalDisk(function);
			else 
				throw new FunctionNotFoundException();
		}
	}
	
	public static void main(String args[])
	{
		ShellFrame frame = new ShellFrame();
		frame.setVisible(true);
	}
}