package functions;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import shellFrameCharacteristics.ShellFrame;

public class FindFunction 
{
	public static int find(Path currentDirectoryPath, String searchedFileName, int currentLevel)
	{
		File[] currentDirectory = currentDirectoryPath.toFile().listFiles();
		if(currentDirectory != null)
		{
			for(File currentFile : currentDirectory)
			{
				if(searchedFileName.equals(currentFile.getName()))
				{
					ShellFrame.commandArea.setText(ShellFrame.commandArea.getText() + "\nSearched File found : " + currentDirectoryPath + '\\' + searchedFileName);
					return currentLevel;
				}
				else if (currentFile.isDirectory())
				{
					int foundLevel = find(FileSystems.getDefault().getPath(currentDirectoryPath.toString(), currentFile.getName()), searchedFileName, currentLevel + 1);
					if(foundLevel != 0)
						return foundLevel;
				}
			}
		}
		return 0;
	}
}
