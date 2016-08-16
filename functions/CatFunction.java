package functions;
import shellFrameCharacteristics.ShellFrame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class CatFunction 
{
	public static void cat(File toCatFile) throws Exception
	{
		Path toCatFilePath = FileSystems.getDefault().getPath(ShellFrame.currentPath.toString(), toCatFile.getName());
		if(Files.exists(toCatFilePath))
		{
			BufferedReader in = new BufferedReader(new FileReader(toCatFilePath.toString()));
			String fileContent = "";
			String currentFileLine;
			while((currentFileLine = in.readLine()) != null)
				fileContent += currentFileLine + '\n';
			in.close();
			ShellFrame.commandArea.setText(ShellFrame.commandArea.getText() + '\n' + fileContent);
		}
		else throw new customExceptions.CatException();
	}
}