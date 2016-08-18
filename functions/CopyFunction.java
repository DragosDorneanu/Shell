package functions;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.StringTokenizer;
import customExceptions.CopyFunctionException;
import shellFrameCharacteristics.ShellFrame;

public class CopyFunction 
{
	private static Path getPath(String fileName){
		return FileSystems.getDefault().getPath(ShellFrame.currentPath.toString(), fileName);
	}
	
	private static void parseArguments(ArrayList<Path> fileArgumentsToBeCopied, String arguments) throws CopyFunctionException
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
				throw new CopyFunctionException();
			fileArgumentsToBeCopied.add(fileArgumentPath);
			currentFilePath = new StringBuilder();
		}
	}
	
	private static Path getDestinationPath(ArrayList<Path> fileArgumentsToBeCopied)
	{
		Path destination = fileArgumentsToBeCopied.get(fileArgumentsToBeCopied.size() - 1);
		fileArgumentsToBeCopied.remove(fileArgumentsToBeCopied.size() - 1);
		return destination;
	}
	
	private static void copyFilesToDestinationDirectory(ArrayList<Path> fileArgumentsToBeCopied, Path destinationDirectory) throws Exception
	{
		Path destinationPath;
		for(int index = 0; index < fileArgumentsToBeCopied.size(); ++index)
		{
			destinationPath = FileSystems.getDefault().getPath(destinationDirectory.toString(), fileArgumentsToBeCopied.get(index).getFileName().toString());
			Files.copy(fileArgumentsToBeCopied.get(index), destinationPath, StandardCopyOption.COPY_ATTRIBUTES);
		}
	}
	
	public static void copy(String arguments) throws Exception
	{
		ArrayList<Path> fileArgumentsToBeCopied = new ArrayList<Path>();
		parseArguments(fileArgumentsToBeCopied, arguments);
		Path destinationPath = getDestinationPath(fileArgumentsToBeCopied);
		if(!Files.isDirectory(destinationPath))
			throw new CopyFunctionException();
		copyFilesToDestinationDirectory(fileArgumentsToBeCopied, destinationPath);
	}
}
