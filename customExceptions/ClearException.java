package customExceptions;

public class ClearException extends Exception
{
	public static final long serialVersionUID = 0L;
	@Override
	public String getMessage(){
		return "Clear Exception : too many arguments or you entered one or more whitespaces";
	}
}
