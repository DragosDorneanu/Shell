package functions;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import shellFrameCharacteristics.ShellFrame;

public class ChangeDirectoryFunction 
{
	public static void changeDirectory(Path newPath){
		ShellFrame.currentPath = newPath;
	}
	
	public static void changeCurrentDirectoryToParentDirectory()
	{
		StringBuilder currentDirectory = new StringBuilder(ShellFrame.currentPath.toString());
		while (currentDirectory.length() > 2 && currentDirectory.charAt(currentDirectory.length() - 1) != '\\')
			currentDirectory.deleteCharAt(currentDirectory.length() - 1);
		if(currentDirectory.length() > 2)
			currentDirectory.deleteCharAt(currentDirectory.length() - 1);
		ShellFrame.currentPath = FileSystems.getDefault().getPath(currentDirectory.toString().trim());
	}
}
