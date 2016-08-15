package customExceptions;

public class CreateFileException extends Exception
{
	public static final long serialVersionUID = 0L;
	@Override
	public String getMessage(){
		return "Create Exception : no arguments or the given file name is already used by another document";
	}
}