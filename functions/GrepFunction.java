package functions;
import java.util.ArrayList;
import java.io.File;
import java.lang.StringBuilder;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.StringTokenizer;
import customExceptions.GrepException;
import shellFrameCharacteristics.ShellFrame;

public class GrepFunction 
{
	private static boolean countOptionUsed = false;
	
	private static void parseArgumentsAndGetPattern(ArrayList<String> args, StringBuilder pattern, String arguments)
	{
		StringTokenizer st = new StringTokenizer(arguments);
		while(st.hasMoreTokens())
			args.add(st.nextToken().trim());
		while(args.size() != 0 && args.get(args.size() - 1).charAt(0) != '-')
		{
			pattern.insert(0, args.get(args.size() - 1) + ' ');
			args.remove(args.size() - 1);
		}
	}
	
	private static int getTypeOfGrep(String option)
	{
		if(option.equals("-reverse"))
			return 0;
		if(option.equals("-count"))
			return 1;
		if(option.equals("-recursive"))
			return 2;
		return -1;
	}
	
	private static ArrayList<String> getLinesWherePatternDoesNotAppear(String pattern)
	{
		ArrayList<String> result = new ArrayList<String>();
		File[] docs = ShellFrame.currentPath.toFile().listFiles();
		for(File currentFile : docs)
		{
			if(!currentFile.getName().contains(pattern))
				result.add(currentFile.getName());
		}
		return result;
	}
	
	private static ArrayList<String> getLinesWherePatternAppear(String pattern)
	{
		ArrayList<String> result = new ArrayList<String>();
		File[] docs = ShellFrame.currentPath.toFile().listFiles();
		for(File currentFile : docs)
		{
			if(currentFile.getName().contains(pattern))
				result.add(currentFile.getName());
		}
		return result;
	}
	
	private static ArrayList<String> getAllPatternMatchFromDirectoryAndSubdirectories(Path currentPath, String pattern)
	{
		ArrayList<String> result = new ArrayList<String>();
		File[] docs = currentPath.toFile().listFiles();
		for(File currentFile : docs)
		{
			if(currentFile.getName().contains(pattern))
				result.add(currentFile.getPath());
			if(currentFile.isDirectory())
				result.addAll(getAllPatternMatchFromDirectoryAndSubdirectories(FileSystems.getDefault().getPath(currentPath.toString(), currentFile.getName()), pattern));
		}
		return result;
	}
	
	private static void displayGrepResult(ArrayList<String> result)
	{
		String resultText = "\n";
		for(int index = 0; index < result.size(); ++index)
			resultText += result.get(index) + '\n';
		ShellFrame.commandArea.setText(ShellFrame.commandArea.getText() + resultText);
	}
	
	private static void parseGrepOptions(ArrayList<String> args, String pattern) throws GrepException
	{
		int typeOfGrep;
		ArrayList<String> result = new ArrayList<String>();
		countOptionUsed = false;
		for(int index = 0; index < args.size(); ++index)
		{
			typeOfGrep = getTypeOfGrep(args.get(index));
			switch(typeOfGrep)
			{
			case 0 : result = getLinesWherePatternDoesNotAppear(pattern); break;
			case 1 : countOptionUsed = true; if(result.size() == 0) result = getLinesWherePatternAppear(pattern); break;
			case 2 : result = getAllPatternMatchFromDirectoryAndSubdirectories(ShellFrame.currentPath, pattern); break;
			default : throw new GrepException();
			}
		}
		if(countOptionUsed == true)
			ShellFrame.commandArea.setText(ShellFrame.commandArea.getText() + "\nFound " + result.size() + " pattern matchings");
		else
			displayGrepResult(result);
	}
	
	public static void grep(String arguments) throws GrepException
	{
		ArrayList<String> args = new ArrayList<String>();
		StringBuilder searchedPattern = new StringBuilder();
		parseArgumentsAndGetPattern(args, searchedPattern, arguments);
		parseGrepOptions(args, searchedPattern.toString().trim());
	}

	public static void grepPattern(String pattern) 
	{
		ArrayList<String> result = new ArrayList<String>();
		File[] docs = ShellFrame.currentPath.toFile().listFiles();
		for(File currentFile : docs)
		{
			if(currentFile.getName().contains(pattern))
				result.add(currentFile.getName());
		}
		displayGrepResult(result);
	}
}