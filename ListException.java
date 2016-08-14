package customExceptions;

public class ListException extends Exception
{
	public static final long serialVersionUID = 0L;
	@Override
	public String getMessage(){
		return "List Exception : too many arguments or invalid directory name was given as argument or unidentified command";
	}
}