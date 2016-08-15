package customExceptions;

public class OpenException extends Exception
{
	public static final long serialVersionUID = 0L;
	@Override
	public String getMessage(){
		return "Open Exception : too many arguments or you entered an invalid file name as argument";
	}
}