package customExceptions;

public class RemoveException extends Exception
{
	public static final long serialVersionUID = 0L;
	
	@Override
	public String getMessage(){
		return "Remove File Exception : invalid file name or to many arguments;\nIf you want to delete a directory, it must be empty or check \"remove -r\" function!";
	}
}
