package functions;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import customExceptions.RemoveException;
import shellFrameCharacteristics.ShellFrame;

public class RemoveFileFunction 
{
	private static Path getPath(String toRemoveFileName){
		return FileSystems.getDefault().getPath(ShellFrame.currentPath.toString(), toRemoveFileName);
	}
	
	public static void remove(String fileName) throws Exception
	{
		Path toRemoveFilePath = getPath(fileName);
		if(Files.exists(toRemoveFilePath))
		{
			if(Files.isDirectory(toRemoveFilePath))
			{
				if(toRemoveFilePath.toFile().listFiles().length == 0)
					Files.delete(toRemoveFilePath);
				else 
					throw new RemoveException();
			}
			else Files.delete(toRemoveFilePath);
		}
		else throw new RemoveException();
	}
}