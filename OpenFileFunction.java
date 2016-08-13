package functions;
import java.awt.Desktop;
import java.io.File;

public class OpenFileFunction 
{
	public static void open(File file) throws Exception {
		Desktop.getDesktop().open(file);
	}
}