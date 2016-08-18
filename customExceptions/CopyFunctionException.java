package customExceptions;

public class CopyFunctionException extends Exception
{
	public static final long serialVersionUID = 0L;
	@Override
	public String getMessage(){
		return "Copy Exception : too few arguments or invalid arguments";
	}
}
