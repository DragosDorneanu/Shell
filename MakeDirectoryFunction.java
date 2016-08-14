package functions;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import shellFrameCharacteristics.ShellFrame;

public class MakeDirectoryFunction 
{
	private static Path getPath(String fileName){
		return FileSystems.getDefault().getPath(ShellFrame.currentPath.toString(), fileName);
	}
	
	public static void mkdir(String newDirectoryName) throws Exception{
		Files.createDirectory(getPath(newDirectoryName));
	}
}
