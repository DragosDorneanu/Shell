package functions;
import customExceptions.ManException;
import shellFrameCharacteristics.ManFrame;
import shellFrameCharacteristics.ShellFrame;

public class ManFunction
{
	private static String getManManText()
	{
		return "Man Page\n" +
				"Here you will find information about all of the available functions.\n" +
				"For information about how to use a certein function type \"man functionName\".\n" +
				"Press 'Q'(quit) to exit the Man Page.\n\n" +
				"Available functions :\n" +
				"\t* exit : Close console(shell).\n\n" +
				"\t* clear : Type \"clear\" in shell console to clear all entered commands or outputed information.\n\n" +   
				"\t* cd : Change directory.\n\n" +
				"\t* cat : Display a text file's content on screen.\n\n" +
				"\t* find : Find if a file with given name as parameter exists or not in the current hierarchy of files.\n\n" +
				"\t* list : Display an array of strings denoting all files in the current directory or in the directory given as parameter.\n\n" +
				"\t* open : Open a file(.txt, .png, .flac, .exe etc.) or a folder.\n\n" +
				"\t* mkdir : create a new directory in current directory haveing the name given as parameter.\n\n" +
				"\t* remove : remove from the hard drive the specified file if it exists.\n\n" +    
				"\t* create : create a new file having the specified name and opens it for edit.\n\n" +
				"\t* cp [source 1] [source 2] ... [source n] [destination directory] : copy all files(sources) given as parameter in the destination directory.\n\n" +
				"\t* grep [pattern] : find all file names where a specific pattern is found.\n\n" +
				"\t* rename [fileName] [newFileName] : rename a file with [fileName] name to a file with [newFileName] name.\n\n" +
				"\t* move [source 1] [source 2] ... [source n] [destination directory] : move all files(sources) given as parameter in the destination directory.\n\n";
	}
	
	public static void man() throws Exception
	{
		String manText = getManManText();
		ManFrame manFrame = new ManFrame(manText);
		manFrame.setVisible(true);
		ShellFrame.thisShellFrame.setVisible(false);
	}
	
	private static String getExitManText()
	{
		return "Exit Man Page\n" +
				"Here you will find information about \"exit\" function.\n\n" +
				"Basic information :\n" +
				"\t* exit : close the current shell window\n";
	}
	
	private static String getClearManText()
	{
		return "Clear Man Page\n" + 
				"Here you will find information about \"clear\" function.\n\nBasic information :\n" + 
				"\t* clear : clear all text(input and output) in the shell window\n";
	}
	
	private static String getCdManText()
	{
		return "CD(Change Directory) Man Page\n" +
				"Here you will find information about \"cd\" function.\nBasic information :\n" +
				"\t* cd [directoryName] : change current path to the directoryName path\n";
	}
	
	private static String getListManText()
	{
		return "List Man Page\n" +
	       "Here you will find information about \"list\" function.\nBasic information :\n" + 
			"\t* this function allows you to request a list of current directory files\n\n" +
			"Options available :\n" +
			"\t* -size : request a list of current directory files ordered by size(in bytes)\n" +
			"\t* -desc : request a list of current directory files in non-lexicographc order\n" +
			"\t* -pdf : returns a list of all \".pdf\" files in your current directory\n" +
			"\t* -txt : returns a list of all \".txt\" files in your current directory\n" +
			"\t* -png : returns a list of all \".png\" files in your current directory\n" +
			"\t* -java : returns a list of all \".java\" files in your current directory\n" +
			"\t* -cpp : returns a list of all \".cpp\" files in your current directory\n" +
			"\t* -exe : returns a list of all \".exe\" files in your current directory\n" +
			"\t* -flac : returns a list of all \".flac\" files in your current directory\n" +
			"\t* -mp4 : returns a list of all \".mp3\" files in your current directory\n" +
			"\t* -zip : returns a list of all \".zip\" files in your current directory\n" +
			"\t* -jar : returns a list of all \".jar\" files in your current directory\n" +
			"\t* -jpg : returns a list of all \".jpg\" files in your current directory\n" +
			"\t* -xlsx : returns a list of all \".xlsx\" files in your current directory\n" +
			"\t* -docx : returns a list of all \".docx\" files in your current directory\n" +
			"\t* -pptx : returns a list of all \".pptx\" files in your current directory\n" +
			"\t* -html : returns a list of all \".html\" files in your current directory\n" +
			"\t* -css : returns a list of all \".css\" files in your current directory\n" +
			"\t* -rights : request a list with access rights on every file in your current directory\n" +
			"\t* -dir : returns a list of all directories in your current directory\n";
	}
	
	private static String getFindManText()
	{
		return "Find Man Page\n" +
				"Here you will find information about \"find\" function.\n\n" +
				"Basic information :\n" +
				"* get the file path of the searched file if it exists\n\n" +
				"Options available :\n" +
				"\t* -maxdepth [number] : get the file path of the searched file if it exist in [number] depth range in current hierarchy of files.\n" +
				"\t* -delete : find and delete the parameter file if it exists in current hierarchy of files.\n" + 
				"\t* -printf [format] : get a specific information about the searched file(if it exists) depending on the entered [format]:\n\n" +
				"\t\t[format]:\n\n" +
				"\t\t\t%b : print file size in bytes\n" +
				"\t\t\t%d : print if the file is directory or not\n" +
				"\t\t\t%t : print file last modified time\n" +
				"\t\t\t%n : print file name\n" +
				"\t\t\t%p : print file access rights\n";
	}
	
	private static String getOpenManText()
	{
		return "Open Man Page\n" +
				"Here you will find information about \"open\" function.\n\n" +
				"Basic information:\n" +
				"\t* open [fileName] : open a file with the default application\n";
	}
	
	private static String getMkdirManText()
	{
		return "Mkdir Man Page\n" +
				"Here you will find information about \"mkdir\" function.\n\n" +
				"Basic information :\n" +
				"\t* mkdir [directoryName] : create a new directory with a specific name\n";
	}
	
	private static String getRemoveManText()
	{
		return "Remove Man Page\n" +
				"Here you will find information about \"remove\" function.\n\n" +
				"Basic information :\n" +
				"\t* delete an existing file from the current hierarchy of files\n\n" +
				"Available options:\n" +
				"\t* -r [directory] : recursive delete all files contained by the directory given as parameter and the direcotry\n" +
				"\t* [file1] [file2] ... [fileN] : delete all files given as parameter ( it can be an empty direcotry as well )\n";
	}
	
	private static String getCreateManText()
	{
		return "Create Man Page\n" +
				"Here you will find information about \"create\" function.\n\n" +
				"Basic information :\n" +
				"\t*create [fileName] : create and open a file with a specific name if it doesn't already exist";
	}
	
	private static String getCatManText()
	{
		return "Cat Man Page\n" +
				"Here you will find information about \"cat\" function.\n\n" +
				"Basic infomation :\n" +
				"\t* print in shell window the content of a .txt file\n" +
				"Available options :\n" +
				"\t* -revers : print file content from end to begin\n" +
				"\t* -first [number] : print first [number] lines contained by the file argument\n" +
				"\t* -last [number] : print last [number] lines contained by the file argument\n" +
				"\t* -n : print file content and append the number of lines of the output\n";
	}
	
	private static String getCpManText()
	{
		return "Copy Man Page\n" +
				"Here you will find information about \"cp\" function.\n\n" +
				"Basic information :\n" +
				"\t* copy [file1] [file2] ... [fileN] [directory] : copy files to directory\n" +
				"\t* copy [directory] [destinationDirectory] : make a copy of the directory given as parameter and put it in a specific directory";
	}
	
	private static String getGrepManText()
	{
		return "Grep Man Page\n" +
				"Here you will find information about \"grep\" function.\n\n" +
				"Basic information :\n" +
				"\t* grep [pattern] : gives you all file names which contains a specific pattern from your current directory\n\n" +	
				"Available options :\n" +
				"\t-reverse : from your current directory, this option gives you all file names in which a specific pattern is not found\n" +
				"\t-count : count the number of the grep [pattern] results\n" +
				"\t-recursive : gives all file names which contains a specific pattern from your current hierarchy of files\n";
	}
	
	private static String getRenameManText()
	{
		return "Rename Man Page\n" + 
				"Here you will find information about \"rename\" function.\n\n" +
				"Basic information :\n" +
				"\t* rename [fileName] [newFileName] : rename file with [fileName] name to file with [newFileName] name\n";
	}
	
	private static String getMoveManText()
	{
		return "Move Man Page\n" +
				"Here you will find information about \"mv\" function.\n\n" +
				"Basic information :\n" +
				"\t* mv [file1] [file2] ... [fileN] [directoryName] : move all files given as parameter in a specific directory\n" +
				"\t* mv [directory] [destinationDirectory] : move the entire directory given as paramter into a specific directory\n";
	}
	
	public static void manSpecificFunction(String functionName) throws Exception 
	{
		String manText;
		if(functionName.equals("exit"))
			 manText = getExitManText();
		else if(functionName.equals("clear"))
			manText = getClearManText();
		else if(functionName.equals("cd"))
			manText = getCdManText();
		else if(functionName.equals("list"))
			manText = getListManText();
		else if(functionName.equals("find"))
			manText = getFindManText();
		else if(functionName.equals("open"))
			manText = getOpenManText();
		else if(functionName.equals("mkdir"))
			manText = getMkdirManText();
		else if(functionName.equals("remove"))
			manText = getRemoveManText();
		else if(functionName.equals("create"))
			manText = getCreateManText();
		else if(functionName.equals("cat"))
			manText = getCatManText();
		else if(functionName.equals("cp"))
			manText = getCpManText();
		else if(functionName.equals("grep"))
			manText = getGrepManText();
		else if(functionName.equals("rename"))
			manText = getRenameManText();
		else if(functionName.equals("mv"))
			manText = getMoveManText();
		else
			throw new ManException();
		ManFrame manFunctionFrame = new ManFrame(manText);
		manFunctionFrame.setVisible(true);
		ShellFrame.thisShellFrame.setVisible(false);
	}
}