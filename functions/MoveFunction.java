package functions;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.StringTokenizer;
import customExceptions.MoveException;
import shellFrameCharacteristics.ShellFrame;

public class MoveFunction 
{		
	private static Path getPath(String fileName){
		return FileSystems.getDefault().getPath(ShellFrame.currentPath.toString(), fileName);
	}
	
	private static void parseArguments(String arguments, ArrayList<Path> filePaths) throws MoveException
	{
		StringTokenizer st = new StringTokenizer(arguments);
		String fileName;
		Path filePath;
		while(st.hasMoreTokens())
		{
			fileName = "";
			filePath = null;
			while(st.hasMoreTokens())
			{
				fileName += st.nextToken().trim();
				filePath = getPath(fileName);
				if(Files.exists(filePath))
					break;
				fileName += ' ';
			}
			if(filePath != null)
				filePaths.add(filePath);
		}
	}
	
	private static Path getDestinationPath(ArrayList<Path> filePaths) throws MoveException
	{
		Path destinationPath = filePaths.get(filePaths.size() - 1);
		if(!Files.isDirectory(destinationPath))
			throw new MoveException();
		filePaths.remove(filePaths.size() - 1);
		return destinationPath;
	}
	
	private static void recursiveMoveDirectory(Path currentDirectoryPath, Path destination) throws Exception
	{
		destination = FileSystems.getDefault().getPath(destination.toString(), currentDirectoryPath.getFileName().toString());
		Files.copy(currentDirectoryPath , destination);
		File[] docs = currentDirectoryPath.toFile().listFiles();
		for(File currentFile : docs)
		{
			if(currentFile.isDirectory())
				recursiveMoveDirectory(FileSystems.getDefault().getPath(currentDirectoryPath.toString(), currentFile.getName()), destination);
			else
			{
				Files.copy(currentFile.toPath(), FileSystems.getDefault().getPath(destination.toString(), currentFile.getName()));
				Files.delete(currentFile.toPath());
			}
		}
		Files.delete(currentDirectoryPath);
	}
	
	private static void moveFilesToDestination(ArrayList<Path> filePaths, Path destination) throws Exception
	{
		for(int index = 0; index < filePaths.size(); ++index)
		{
			if(Files.isDirectory(filePaths.get(index)))
				recursiveMoveDirectory(filePaths.get(index), destination);
			else
			{
				Files.copy(filePaths.get(index), FileSystems.getDefault().getPath(destination.toString(), filePaths.get(index).getFileName().toString()));
				Files.delete(filePaths.get(index));
			}
		}
	}
	
	public static void move(String arguments) throws Exception
	{
		ArrayList<Path> filePaths = new ArrayList<Path>();
		parseArguments(arguments, filePaths);
		Path destination = getDestinationPath(filePaths);
		moveFilesToDestination(filePaths, destination);
	}
}