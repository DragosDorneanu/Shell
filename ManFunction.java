package functions;
import java.io.BufferedReader;
import java.io.FileReader;
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
		BufferedReader in = null;
		String manText = "", line;
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
		else
			throw new customExceptions.ManException();
		while((line = in.readLine()) != null)
			manText += line + '\n';
		in.close();
		ManFrame manFunctionFrame = new ManFrame(manText);
		manFunctionFrame.setVisible(true);
		ShellFrame.thisShellFrame.setVisible(false);
	}
}