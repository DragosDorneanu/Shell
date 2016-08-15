package functions;
import shellFrameCharacteristics.ShellFrame;

public class ClearFunction 
{
	public static void clear(){
		ShellFrame.commandArea.setText("Copyrights reserved to Dragos!\nType \"man\" for a list of available functions or type \"man functionName\" for information about a specific function.");
	}
}