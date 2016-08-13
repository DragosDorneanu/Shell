package functions;
import java.io.File;
import shellFrameCharacteristics.ShellFrame;

public class ListDirectoryFunction 
{
	public static void listCurrentDirectory()
	{
		File currentDirectory = ShellFrame.currentPath.toFile();
		File []currentDirectoryDocs = currentDirectory.listFiles();
		if(currentDirectoryDocs != null)
		{
			String allFiles = "";
			for(File currentFile : currentDirectoryDocs)
				allFiles += currentFile.getName() + '\n';
			ShellFrame.commandArea.setText(ShellFrame.commandArea.getText() + '\n' + allFiles);
		}
		else ShellFrame.commandArea.setText(ShellFrame.commandArea.getText() + '\n');
	}
}