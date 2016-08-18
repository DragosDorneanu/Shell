package functions;
import java.io.BufferedReader;
import java.io.FileReader;
import customExceptions.ManException;
import shellFrameCharacteristics.ManFrame;
import shellFrameCharacteristics.ShellFrame;

public class ManFunction
{
	public static void man() throws Exception
	{
		String manText = "";
		String currentFileLine;
		BufferedReader in = new BufferedReader(new FileReader("ManText.txt"));
		while((currentFileLine = in.readLine()) != null)
			manText += currentFileLine + '\n';
		in.close();
		ManFrame manFrame = new ManFrame(manText);
		manFrame.setVisible(true);
		ShellFrame.thisShellFrame.setVisible(false);
	}
	
	public static void manSpecificFunction(String functionName) throws Exception 
	{
		String line;
		String manText = "";
		BufferedReader in = null;
		if(functionName.equals("exit"))
			 in = new BufferedReader(new FileReader("Exit Man Page.txt"));
		else if(functionName.equals("clear"))
			in = new BufferedReader(new FileReader("Clear Man Page.txt"));
		else if(functionName.equals("cd"))
			in = new BufferedReader(new FileReader("Cd Man Page.txt"));
		else if(functionName.equals("list"))
			in = new BufferedReader(new FileReader("List Man Page.txt"));
		else if(functionName.equals("find"))
			in = new BufferedReader(new FileReader("Find Man Page.txt"));
		else if(functionName.equals("open"))
			in = new BufferedReader(new FileReader("Open Man Page.txt"));
		else if(functionName.equals("mkdir"))
			in = new BufferedReader(new FileReader("Mkdir Man Page.txt"));
		else if(functionName.equals("remove"))
			in = new BufferedReader(new FileReader("Remove Man Page.txt"));
		else if(functionName.equals("create"))
			in = new BufferedReader(new FileReader("Create Man Page.txt"));
		else if(functionName.equals("cat"))
			in = new BufferedReader(new FileReader("Cat Man Page.txt"));
		else if(functionName.equals("cp"))
			in = new BufferedReader(new FileReader("Cp Man Page.txt"));
		else
			throw new ManException();
		while((line = in.readLine()) != null)
			manText += line + '\n';
		in.close();
		ManFrame manFunctionFrame = new ManFrame(manText);
		manFunctionFrame.setVisible(true);
		ShellFrame.thisShellFrame.setVisible(false);
	}
}