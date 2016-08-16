package functions;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class FindFunction 
{
	private static Path binarySearchFile(File[] docs, int lower, int upper, String searchedFileName)
	{
		if(lower <= upper)
		{
			int mid = (lower + upper) / 2;
			int differenceBetweenFileNames = searchedFileName.compareTo(docs[mid].getName().toLowerCase());
			if(differenceBetweenFileNames == 0)
				return docs[mid].toPath();
			if(differenceBetweenFileNames < 0)
				return binarySearchFile(docs, lower, mid - 1, searchedFileName);
			return binarySearchFile(docs, mid + 1, upper, searchedFileName);
		}
		return null;
	}
	
	public static Path find(Path currentDirectoryPath, String searchedFileName)
	{
		File[] docs = currentDirectoryPath.toFile().listFiles();
		if(docs != null)
		{
			Path foundFilePath = binarySearchFile(docs, 0, docs.length - 1, searchedFileName);
			if(foundFilePath != null)
				return foundFilePath;
			for(File currentFile : docs)
			{
				if(currentFile.isDirectory())
				{
					foundFilePath = find(FileSystems.getDefault().getPath(currentDirectoryPath.toString(), currentFile.getName()), searchedFileName);
					if(foundFilePath != null)
						return foundFilePath;
				}
			}
		}
		return null;
	}
}