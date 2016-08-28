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
	private static boolean displayListWithAccessRights = false;
	
	private static Path getPath(File fileName){
		return FileSystems.getDefault().getPath(fileName.getAbsolutePath());
	}
	
	private static void displayListResult(File[] currentDirectoryDocs)
	{
		if(currentDirectoryDocs != null)
		{
			String allFiles = "";
			for(File currentFile : currentDirectoryDocs)
			{
				 if(currentFile != null)
					 allFiles += currentFile.getName() + '\n';
			}
			ShellFrame.commandArea.setText(ShellFrame.commandArea.getText() + '\n' + allFiles);
		}
		else ShellFrame.commandArea.setText(ShellFrame.commandArea.getText() + '\n');
	}
	
	private static String getAccessRightsOnFile(File file)
	{
		Path currentFilePath = getPath(file);
		StringBuilder accessRightsOnCurrentFile = new StringBuilder("----");
		if(Files.isDirectory(currentFilePath))
			accessRightsOnCurrentFile.setCharAt(0, 'd');
		if(Files.isReadable(currentFilePath))
			accessRightsOnCurrentFile.setCharAt(1, 'r');
		if(Files.isWritable(currentFilePath))
			accessRightsOnCurrentFile.setCharAt(2, 'w');
		if(Files.isWritable(currentFilePath))
			accessRightsOnCurrentFile.setCharAt(3, 'x');
		return accessRightsOnCurrentFile.toString();
	}
	
	private static void displayListResultWithAccessRightsOnFiles(File[] currentDirectoryDocs)
	{
		if(currentDirectoryDocs != null)
		{
			String allFiles = "";
			for(File currentFile : currentDirectoryDocs)
			{
				if(currentFile != null)
					allFiles += getAccessRightsOnFile(currentFile) + "    " + currentFile.getName() + '\n';
			}
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
	
	private static long findAPivot(File[] docs, int lowerBound, int upperBound) throws Exception
	{
		int mid = (lowerBound + upperBound) / 2, auxMid;
		if(docs[mid] != null)
			return Files.size(getPath(docs[mid]));
		auxMid = mid - 1;
		if(auxMid >= lowerBound)
		{
			while(auxMid >= lowerBound && docs[auxMid] == null)
				--auxMid;
		}
		if(auxMid >= lowerBound)
			return Files.size(getPath(docs[auxMid]));
		auxMid = mid + 1;
		while(auxMid <= upperBound && docs[auxMid] == null)
			++auxMid;
		if(auxMid <= upperBound)
			return Files.size(getPath(docs[auxMid]));
		return -1;
	}
	
	private static void sortByFileSize(File[] docs, int lowerBound, int upperBound) throws Exception
	{
		int left = lowerBound, right = upperBound;
		File aux;
		long sizeOfPivotDoc = findAPivot(docs, lowerBound, upperBound);
		if(sizeOfPivotDoc == -1)
			return;
		while(left < right)
		{
			while(left <= right)
			{
				if(docs[left] != null)
				{
					if(Files.size(getPath(docs[left])) < sizeOfPivotDoc)
						++left;
					else
						break;
				}
				else ++left;
			}
			while(right >= left)
			{
				if(docs[right] != null)
				{
					if(Files.size(getPath(docs[right])) > sizeOfPivotDoc)
						--right;
					else
						break;
				}
				else --right;
			}
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
		if(type.equals("-pdf"))
			return 2;
		if(type.equals("-txt"))
			return 3;
		if(type.equals("-png"))
			return 4;
		if(type.equals("-java"))
			return 5;
		if(type.equals("-cpp"))
			return 6;
		if(type.equals("-exe"))
			return 7;
		if(type.equals("-flac"))
			return 8;
		if(type.equals("-mp4"))
			return 9;
		if(type.equals("-zip"))
			return 10;
		if(type.equals("-jar"))
			return 11;
		if(type.equals("-jpg"))
			return 12;
		if(type.equals("-xlsx"))
			return 13;
		if(type.equals("-docx"))
			return 14;
		if(type.equals("-pptx"))
			return 15;
		if(type.equals("-html"))
			return 16;
		if(type.equals("-css"))
			return 17;
		if(type.equals("-rar"))
			return 18;
		if(type.equals("-rights"))
			return 19;
		if(type.equals("-dir"))
			return 20;
		return -1;
	}
	
	private static void parseArgumentsAndGetFileParameter(StringTokenizer st, ArrayList<String> args, StringBuilder fileName)
	{
		while(st.hasMoreTokens())
			args.add(st.nextToken().trim());
		for(int index = args.size() - 1; index >= 0 && args.get(index).charAt(0) != '-'; --index)
			fileName.insert(0, args.get(index) + ' ');
	}
	
	private static void getFilesWithASpecificType(File[] docs, String fileType)
	{
		for(int index = 0; index < docs.length; ++index)
		{
			if(!docs[index].getName().endsWith(fileType))
				docs[index] = null;
		}
	}
	
	private static void getDirectories(File[] docs)
	{
		for(int index = 0; index < docs.length; ++index)
		{
			if(!docs[index].isDirectory())
				docs[index] = null;
		}
	}
	
	private static void parseListOptions(File[] docs, ArrayList<String> args) throws Exception
	{
		int typeOfListing;
		displayListWithAccessRights = false;
		for(int index = 0; index < args.size() && args.get(index).charAt(0) == '-'; ++index)
		{
			typeOfListing = getTypeOfListing(args.get(index));
			switch(typeOfListing)
			{
			case 0 : sortByFileSize(docs, 0, docs.length - 1); break;
			case 1 : descendingSort(docs); break;
			case 2 : getFilesWithASpecificType(docs, ".pdf"); break;
			case 3 : getFilesWithASpecificType(docs, ".txt"); break;
			case 4 : getFilesWithASpecificType(docs, ".png"); break;
			case 5 : getFilesWithASpecificType(docs, ".java"); break;
			case 6 : getFilesWithASpecificType(docs, ".cpp"); break;
			case 7 : getFilesWithASpecificType(docs, ".exe"); break;
			case 8 : getFilesWithASpecificType(docs, ".flac"); break;
			case 9 : getFilesWithASpecificType(docs, ".mp4"); break;
			case 10 : getFilesWithASpecificType(docs, ".zip"); break;
			case 11 : getFilesWithASpecificType(docs, ".jar"); break;
			case 12 : getFilesWithASpecificType(docs, ".jpg"); break;
			case 13 : getFilesWithASpecificType(docs, ".xlsx"); break;
			case 14 : getFilesWithASpecificType(docs, ".docx"); break;
			case 15 : getFilesWithASpecificType(docs, ".pptx"); break;
			case 16 : getFilesWithASpecificType(docs, ".html"); break;
			case 17 : getFilesWithASpecificType(docs, ".css"); break;
			case 18 : getFilesWithASpecificType(docs, ".rar"); break;
			case 19 : displayListWithAccessRights = true; break;
			case 20 : getDirectories(docs); break;
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
			if(displayListWithAccessRights == true)
				displayListResultWithAccessRightsOnFiles(docs);
			else
				displayListResult(docs);
		}
	}
}