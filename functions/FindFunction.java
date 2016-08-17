package functions;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.StringTokenizer;

import customExceptions.FindFunctionException;
import shellFrameCharacteristics.ShellFrame;

public class FindFunction 
{
	private static Path binarySearchFile(File[] docs, int lower, int upper, String searchedFileName)
	{
		if(lower <= upper)
		{
			int mid = (lower + upper) / 2;
			int differenceBetweenFileNames = searchedFileName.compareTo(docs[mid].getName().toLowerCase());
			if(differenceBetweenFileNames == 0)
				return docs[mid].toPath();
			if(differenceBetweenFileNames < 0)
				return binarySearchFile(docs, lower, mid - 1, searchedFileName);
			return binarySearchFile(docs, mid + 1, upper, searchedFileName);
		}
		return null;
	}
	
	public static Path find(Path currentDirectoryPath, String searchedFileName)
	{
		File[] docs = currentDirectoryPath.toFile().listFiles();
		if(docs != null)
		{
			Path foundFilePath = binarySearchFile(docs, 0, docs.length - 1, searchedFileName);
			if(foundFilePath != null)
				return foundFilePath;
			for(File currentFile : docs)
			{
				if(currentFile.isDirectory())
				{
					foundFilePath = find(FileSystems.getDefault().getPath(currentDirectoryPath.toString(), currentFile.getName()), searchedFileName);
					if(foundFilePath != null)
						return foundFilePath;
				}
			}
		}
		return null;
	}

	private static int getTypeOfFinding(String type)
	{
		if(type.equals("-maxdepth"))
			return 0;
		if(type.equals("-delete"))
			return 1;
		return -1;
	}
	
	private static void parseArgumentsAndGetFileName(String arguments, ArrayList<String> args, StringBuilder fileName)
	{
		StringTokenizer st = new StringTokenizer(arguments);
		while(st.hasMoreTokens())
			args.add(st.nextToken().trim());
		for(int index = args.size() - 1; index >= 0 && args.get(index).charAt(0) != '-' && (args.get(index).charAt(0) < '0' || args.get(index).charAt(0) > '9'); --index)
			fileName.insert(0, args.get(index) + ' ');
	}
	
	private static Path parseFindOptions(Path currentDir, ArrayList<String> args, Path filePath) throws Exception
	{
		int option;
		Path filePathInMaxDepthRange = null;
		for(int index = 0; index < args.size() && args.get(index).charAt(0) == '-'; ++index)
		{
			option = getTypeOfFinding(args.get(index));
			switch(option)
			{
			case 0 : if(index + 1 < args.size()){filePathInMaxDepthRange = findMaxDepth(filePath, Integer.parseInt(args.get(index + 1))); ++index; break;} else throw new FindFunctionException(); 
			case 1 : filePathInMaxDepthRange = RemoveFileFunction.remove(filePath); break;
			default : throw new FindFunctionException();
			}
		}
		return filePathInMaxDepthRange;
	}
	
	private static Path findMaxDepth(Path filePath, int maxDepth) 
	{
		Path thisFilePath = FileSystems.getDefault().getPath(filePath.toString());
		for(int count = 1; count <= maxDepth; ++count)
		{
			thisFilePath = ChangeDirectoryFunction.changeCurrentDirectoryToParentDirectory(thisFilePath);
			if(ShellFrame.currentPath.toString().equals(thisFilePath.toString()))
				return filePath;
		}
		return null;
	}

	public static Path findWithSpecificOptions(Path currentDirectoryPath, String arguments) throws Exception
	{
		ArrayList<String> args = new ArrayList<String>();
		StringBuilder fileName = new StringBuilder();
		parseArgumentsAndGetFileName(arguments, args, fileName);
		Path searchedFilePath = find(currentDirectoryPath, fileName.toString().trim().toLowerCase());
		if(searchedFilePath != null)
			return parseFindOptions(currentDirectoryPath, args, searchedFilePath);
		return null;
	}
}