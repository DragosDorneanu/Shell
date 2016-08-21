package functions;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.StringTokenizer;
import customExceptions.RemoveException;
import shellFrameCharacteristics.ShellFrame;

public class RemoveFileFunction 
{
	private static Path getPath(String toRemoveFileName){
		return FileSystems.getDefault().getPath(ShellFrame.currentPath.toString(), toRemoveFileName);
	}
	
	public static Path remove(Path filePath) throws Exception
	{
		if(Files.exists(filePath))
		{
			if(Files.isDirectory(filePath))
			{
				if(filePath.toFile().listFiles().length == 0)
					Files.delete(filePath);
				else 
					throw new RemoveException();
			}
			else Files.delete(filePath);
			return filePath;
		}
		else throw new RemoveException();
	}
	
	private static void parseArguments(ArrayList<Path> toBeRemovedFiles, String arguments) throws RemoveException
	{
		StringTokenizer st = new StringTokenizer(arguments);
		StringBuilder currentFilePath = new StringBuilder();
		Path fileArgumentPath;
		while(st.hasMoreTokens())
		{
			while(st.hasMoreTokens())
			{
				currentFilePath.append(st.nextToken().trim());
				if(Files.exists(getPath(currentFilePath.toString())))
						 break;
				currentFilePath.append(' ');
			}
			fileArgumentPath = getPath(currentFilePath.toString());
			if(!Files.exists(fileArgumentPath))
				throw new RemoveException();
			toBeRemovedFiles.add(fileArgumentPath);
			currentFilePath = new StringBuilder();
		}
	}
	
	private static void deleteFileArguments(ArrayList<Path> toBeRemovedFiles) throws Exception
	{
		for(int index = 0; index < toBeRemovedFiles.size(); ++index)
			Files.delete(toBeRemovedFiles.get(index));
	}
	
	public static void remove(String arguments) throws Exception
	{
		ArrayList<Path> toBeRemovedFiles = new ArrayList<Path>();
		parseArguments(toBeRemovedFiles, arguments);
		deleteFileArguments(toBeRemovedFiles);
	}
	
	private static void recursiveRemoveDirectory(Path currentPath, File[] docs) throws Exception
	{
		Path filePath;
		for(File currentFile : docs)
		{
			filePath = FileSystems.getDefault().getPath(currentPath.toString(), currentFile.getName());
			if(Files.isDirectory(filePath))
			{
				recursiveRemoveDirectory(filePath, currentFile.listFiles());
				Files.delete(filePath);
			}
			else Files.delete(filePath);
		}
	}
	
	public static void deleteArgumentDirectory(String directoryName) throws Exception
	{
		Path directoryPath = getPath(directoryName);
		if(Files.isDirectory(directoryPath))
		{
			recursiveRemoveDirectory(directoryPath, directoryPath.toFile().listFiles());
			Files.delete(directoryPath);
		}
		else throw new RemoveException();
	}
}
