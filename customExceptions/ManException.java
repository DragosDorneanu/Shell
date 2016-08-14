package customExceptions;

public class ManException extends Exception 
{
	public static final long serialVersionUID = 0L;
	@Override
	public String getMessage()	{
		return "Man Exception : too many arguments or you entered one or more whitespaces or an unavailable function";
	}
}
