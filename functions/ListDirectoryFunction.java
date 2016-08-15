package functions;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.StringTokenizer;
import java.util.ArrayList;
import customExceptions.ListException;
import shellFrameCharacteristics.ShellFrame;

public class ListDirectoryFunction 
{
	private static Path getPath(File fileName){
		return FileSystems.getDefault().getPath(fileName.getAbsolutePath());
	}
	
	private static void displayListResult(File[] currentDirectoryDocs)
	{
		if(currentDirectoryDocs != null)
		{
			String allFiles = "";
			for(File currentFile : currentDirectoryDocs)
				allFiles += currentFile.getName() + '\n';
			ShellFrame.commandArea.setText(ShellFrame.commandArea.getText() + '\n' + allFiles);
		}
		else ShellFrame.commandArea.setText(ShellFrame.commandArea.getText() + '\n');
	}
	
	public static void listCurrentDirectory()
	{
		File currentDirectory = ShellFrame.currentPath.toFile();
		File []currentDirectoryDocs = currentDirectory.listFiles();
		displayListResult(currentDirectoryDocs);
	}
	
	private static void sortByFileSize(File[] docs, int lowerBound, int upperBound) throws Exception
	{
		int left = lowerBound, right = upperBound;
		int mid = (left + right) / 2;
		File aux;
		long sizeOfMidDoc = Files.size(getPath(docs[mid]));
		while(left < right)
		{
			while(Files.size(getPath(docs[left])) < sizeOfMidDoc)
				++left;
			while(Files.size(getPath(docs[right])) > sizeOfMidDoc)
				--right;
			if(left <= right)
			{
				aux = docs[left];
				docs[left] = docs[right];
				docs[right] = aux;
				++left;
				--right;
			}
		}
		if(left < upperBound)
			sortByFileSize(docs, left, upperBound);
		if(lowerBound < right)
			sortByFileSize(docs, lowerBound, right);
	}
	
	private static void descendingSort(File[] docs)
	{
		if(docs != null)
		{
			int frontIndex = 0;
			int tailIndex = docs.length - 1;
			File aux;
			while(frontIndex < tailIndex)
			{
				aux = docs[frontIndex];
				docs[frontIndex] = docs[tailIndex];
				docs[tailIndex] = aux;
				++frontIndex;
				--tailIndex;
			}
		}
	}
	
	private static int getTypeOfListing(String type)
	{
		if(type.equals("-size"))
			return 0;
		if(type.equals("-desc"))
			return 1;
		return -1;
	}
	
	private static void parseArgumentsAndGetFileParameter(StringTokenizer st, ArrayList<String> args, StringBuilder fileName)
	{
		while(st.hasMoreTokens())
			args.add(st.nextToken().trim());
		for(int index = args.size() - 1; index >= 0 && args.get(index).charAt(0) != '-'; --index)
			fileName.insert(0, args.get(index) + ' ');
	}
	
	private static void parseListOptions(File[] docs, ArrayList<String> args) throws Exception
	{
		int typeOfListing;
		for(int index = 0; index < args.size() && args.get(index).charAt(0) == '-'; ++index)
		{
			typeOfListing = getTypeOfListing(args.get(index));
			switch(typeOfListing)
			{
			case 0 : sortByFileSize(docs, 0, docs.length - 1); break;
			case 1 : descendingSort(docs); break;
			default : throw new ListException();
			}
		}
	}
	
	public static void listParameterDirectory(String arguments) throws Exception
	{
		if(arguments.charAt(0) != '-')
		{
			Path possibleDirectory = FileSystems.getDefault().getPath(ShellFrame.currentPath.toString(), arguments.trim());
			if(!Files.exists(possibleDirectory) || !Files.isDirectory(possibleDirectory))
				throw new ListException();
			displayListResult(possibleDirectory.toFile().listFiles());
		}
		else
		{
			ArrayList<String> args = new ArrayList<String>();
			StringTokenizer st = new StringTokenizer(arguments);
			StringBuilder fileName = new StringBuilder();
			Path toListFilePath;
			parseArgumentsAndGetFileParameter(st, args, fileName);
			toListFilePath = FileSystems.getDefault().getPath(ShellFrame.currentPath.toString(), fileName.toString().trim());
			if(!Files.exists(toListFilePath) || !Files.isDirectory(toListFilePath))
				throw new ListException();
			File[] docs = toListFilePath.toFile().listFiles();
			parseListOptions(docs, args);
			displayListResult(docs);
		}
	}
}