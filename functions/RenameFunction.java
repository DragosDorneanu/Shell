package functions;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.StringTokenizer;
import customExceptions.RenameException;
import shellFrameCharacteristics.ShellFrame;

public class RenameFunction 
{
	private static Path getPath(String fileName){
		return FileSystems.getDefault().getPath(ShellFrame.currentPath.toString(), fileName);
	}
	
	private static void getFileNameAndNewFileName(StringBuilder name, StringBuilder newName, String arguments) throws RenameException
	{
		StringTokenizer st = new StringTokenizer(arguments);
		while(st.hasMoreTokens())
		{
			name.append(st.nextToken().trim());
			if(Files.exists(getPath(name.toString())))
				break;
			name.append(' ');
		}
		while(st.hasMoreTokens())
		{
			newName.append(st.nextToken().trim());
			if(Files.exists(getPath(name.toString())))
				break;
			newName.append(' ');
		}
	}
	
	public static void rename(String arguments) throws RenameException
	{
		StringBuilder fileName = new StringBuilder();
		StringBuilder newFileName = new StringBuilder();
		Path filePath, newFilePath;
		getFileNameAndNewFileName(fileName, newFileName, arguments);
		filePath = getPath(fileName.toString().trim());
		if(!Files.exists(filePath))
			throw new RenameException();
		newFilePath = getPath(newFileName.toString().trim());
		filePath.toFile().renameTo(newFilePath.toFile());
	}
}