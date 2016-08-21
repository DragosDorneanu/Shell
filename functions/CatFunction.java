package functions;
import shellFrameCharacteristics.ShellFrame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.StringTokenizer;
import customExceptions.CatException;

public class CatFunction 
{
	private static String readFile(File toCatFile) throws Exception
	{
		Path toCatFilePath = FileSystems.getDefault().getPath(ShellFrame.currentPath.toString(), toCatFile.getName());
		if(Files.exists(toCatFilePath))
		{
			BufferedReader in = new BufferedReader(new FileReader(toCatFilePath.toString()));
			String fileContent = "";
			String currentFileLine;
			while((currentFileLine = in.readLine()) != null)
				fileContent += currentFileLine + '\n';
			in.close();
			return fileContent;
		}
		else throw new CatException();
	}
	
	private static void parseArgumentsAndGetFileName(ArrayList<String> args, String arguments, StringBuilder fileName)
	{
		StringTokenizer st = new StringTokenizer(arguments);
		while(st.hasMoreTokens())
			args.add(st.nextToken().trim());
		while(args.size() != 0 && args.get(args.size() - 1).charAt(0) != '-' && (args.get(args.size() - 1).charAt(0) < '0' || args.get(args.size() - 1).charAt(0) > '9'))
		{
			fileName.insert(0, args.get(args.size() - 1).trim() + ' ');
			args.remove(args.size() - 1);
		}
	}
	
	private static int getTypeOfCatOption(String option)
	{
		if(option.equals("-reverse"))
			return 0;
		if(option.equals("-last"))
			return 1;
		if(option.equals("-first"))
			return 2;
		if(option.equals("-n"))
			return 3;
		return -1;
	}
	
	private static String reverse(String text)
	{
		StringTokenizer st = new StringTokenizer(text, "\n");
		ArrayList<String> linesOfText = new ArrayList<String>();
		while(st.hasMoreTokens())
			linesOfText.add(st.nextToken().trim());
		text = "";
		for(int index = linesOfText.size() - 1; index >= 0; --index)
			text += linesOfText.get(index) + '\n';
		return text;
	}
	
	private static String lastLines(String text, int numberOfLines)
	{
		text = reverse(text);
		StringTokenizer st = new StringTokenizer(text, "\n");
		String newText = "";
		while(st.hasMoreTokens() && numberOfLines != 0)
		{
			newText += st.nextToken() + '\n';
			--numberOfLines;
		}
		return reverse(newText);
	}
	
	private static String firstLines(String text, int numberOfLines)
	{
		StringTokenizer st = new StringTokenizer(text, "\n");
		String newText = "";
		while(st.hasMoreTokens() && numberOfLines != 0)
		{
			newText += st.nextToken() + '\n';
			--numberOfLines;
		}
		return newText;
	}
	
	private static String appendNumberOfLines(String text)
	{
		StringTokenizer st = new StringTokenizer(text, "\n");
		int numberOfLines = 0;
		while(st.hasMoreTokens())
		{
			++numberOfLines;
			st.nextToken();
		}
		text += "\n\n" + "This file has " + numberOfLines + " lines\n";
		return text;
	}
	
	private static void parseCatOptions(ArrayList<String> args, Path filePath) throws Exception
	{
		int typeOfCat;
		String catText = readFile(filePath.toFile());
		for(int index = 0; index < args.size(); ++index)
		{
			typeOfCat = getTypeOfCatOption(args.get(index));
			switch(typeOfCat)
			{
			case 0 : catText = reverse(catText); break;
			case 1 : if(index + 1 < args.size()) { catText = lastLines(catText, Integer.parseInt(args.get(index + 1))); ++index; } else throw new CatException(); break;
			case 2 : if(index + 1 < args.size()) { catText = firstLines(catText, Integer.parseInt(args.get(index + 1))); ++index; } else throw new CatException(); break;
			case 3 : catText = appendNumberOfLines(catText); break;
			default : throw new CatException();
			}
		}
		ShellFrame.commandArea.setText(ShellFrame.commandArea.getText() + '\n' + catText);
	}
	
	public static void cat(String arguments) throws Exception
	{
		ArrayList<String> args = new ArrayList<String>();
		StringBuilder fileName = new StringBuilder();
		Path argumentFilePath;
		parseArgumentsAndGetFileName(args, arguments, fileName);
		argumentFilePath = FileSystems.getDefault().getPath(ShellFrame.currentPath.toString(), fileName.toString().trim());
		if(!Files.exists(argumentFilePath))
			throw new CatException();
		parseCatOptions(args, argumentFilePath);
	}
}