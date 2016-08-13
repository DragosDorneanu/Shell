package customExceptions;

public class CDException extends Exception
{
	public static final long serialVersionUID = 0L;
	@Override
	public String getMessage(){
		return "CD Exception : too many arguments or invalid file name or the path doesn't denote a directory";
	}
}