package functions;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
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
		if(type.equals("-printf"))
			return 2;
		return -1;
	}
	
	private static void parseArgumentsAndGetFileName(String arguments, ArrayList<String> args, StringBuilder fileName)
	{
		StringTokenizer st = new StringTokenizer(arguments);
		while(st.hasMoreTokens())
			args.add(st.nextToken().trim());
		for(int index = args.size() - 1; index >= 0 && args.get(index).charAt(0) != '-' && args.get(index).charAt(0) != '%'; --index)
		{
			if(args.get(index).charAt(0) >= '0' && args.get(index).charAt(0) <= '9')
			{
				if(index > 0 && !args.get(index - 1).equals("-maxdepth"))
					fileName.insert(0, args.get(index) + ' ');
				else
					break;
			}
			else fileName.insert(0, args.get(index) + ' ');
		}
	}
	
	private static Path findMaxDepth(Path filePath, int maxDepth) 
	{
		Path thisFilePath = FileSystems.getDefault().getPath(filePath.toString());
		for(int count = 1; count <= maxDepth; ++count)
		{
			thisFilePath = ChangeDirectoryFunction.changeCurrentDirectoryToParentDirectory(thisFilePath);
			if(ShellFrame.currentPath.toString().equals(thisFilePath.toString()))
			{
				ShellFrame.commandArea.setText(ShellFrame.commandArea.getText() + '\n' + "Searched file found in max depth " + maxDepth + " at : " + filePath);
				return filePath;
			}
		}
		return null;
	}
	
	private static void printFileLastModifiedTime(Path filePath) throws Exception {
		ShellFrame.commandArea.setText(ShellFrame.commandArea.getText() + '\n' + "Last modified time : " + Files.getLastModifiedTime(filePath));
	}
	
	private static void printFileSizeInBytes(Path filePath) throws Exception {
		ShellFrame.commandArea.setText(ShellFrame.commandArea.getText() + '\n' + "File size in bytes : " + Files.size(filePath));
	}
	
	private static void printIfIsDirectoryOrNot(Path filePath) 
	{
		if(Files.isDirectory(filePath))
			ShellFrame.commandArea.setText(ShellFrame.commandArea.getText() + '\n' + "Found file is a directory");
		else
			ShellFrame.commandArea.setText(ShellFrame.commandArea.getText() + '\n' + "Found file is NOT a directory");
	}
	
	private static void printFileName(Path filePath){
		ShellFrame.commandArea.setText(ShellFrame.commandArea.getText() + '\n' + "File name : " + filePath.getFileName());
	}
	
	private static void printFileAccessRights(Path filePath)
	{
		StringBuilder accessRightsOnFile = new StringBuilder("----");
		if(Files.isDirectory(filePath))
			accessRightsOnFile.setCharAt(0, 'd');
		if(Files.isReadable(filePath))
				accessRightsOnFile.setCharAt(1, 'r');
		if(Files.isWritable(filePath))
			accessRightsOnFile.setCharAt(2, 'w');
		if(Files.isWritable(filePath))
			accessRightsOnFile.setCharAt(3, 'x');
		ShellFrame.commandArea.setText(ShellFrame.commandArea.getText() + '\n' + "File access right : " + accessRightsOnFile.toString());
	}
	
	private static void printf(ArrayList<String> printfArguments, int printfArgumentStartIndex, Path filePath) throws Exception
	{
		int index;
		for(index = printfArgumentStartIndex; index < printfArguments.size() && printfArguments.get(index).charAt(0) == '%'; ++index)
		{
			switch(printfArguments.get(index).charAt(1))
			{
			case 'b' : printFileSizeInBytes(filePath); break;
			case 'd' : printIfIsDirectoryOrNot(filePath); break;
			case 't' : printFileLastModifiedTime(filePath); break;
			case 'n' : printFileName(filePath); break;
			case 'p' : printFileAccessRights(filePath); break;
			default : throw new FindFunctionException();
			}
		}
		if(index == printfArgumentStartIndex)
			throw new FindFunctionException();
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
			case 1 : filePathInMaxDepthRange = RemoveFileFunction.remove(filePath); ShellFrame.commandArea.setText(ShellFrame.commandArea.getText() + '\n' + "Deleted!"); break;
			case 2 : printf(args, index + 1, filePath); break;
			default : throw new FindFunctionException();
			}
		}
		return filePathInMaxDepthRange;
	}
	
	public static Path findWithSpecificOptions(Path currentDirectoryPath, String arguments) throws Exception
	{
		ArrayList<String> args = new ArrayList<String>();
		StringBuilder fileName = new StringBuilder();
		parseArgumentsAndGetFileName(arguments, args, fileName);
		Path searchedFilePath = find(currentDirectoryPath, fileName.toString().trim().toLowerCase());
		if(searchedFilePath != null)
		{
			ShellFrame.commandArea.setText(ShellFrame.commandArea.getText() + '\n' + "Searched File found : " + searchedFilePath);
			return parseFindOptions(currentDirectoryPath, args, searchedFilePath);
		}
		return null;
	}
}