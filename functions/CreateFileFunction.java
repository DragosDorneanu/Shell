package functions;
import java.nio.file.Path;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import customExceptions.CreateFileException;
import shellFrameCharacteristics.ShellFrame;

public class CreateFileFunction 
{
	private static Path getPath(String fileName){
		return FileSystems.getDefault().getPath(ShellFrame.currentPath.toString(), fileName);
	}
	
	public static void createFile(String fileName) throws Exception
	{
		Path newFilePath = getPath(fileName);
		if(!Files.exists(newFilePath))
		{
			Files.createFile(newFilePath);
			OpenFileFunction.open(newFilePath.toFile());
		}
		else throw new CreateFileException();
	}
}