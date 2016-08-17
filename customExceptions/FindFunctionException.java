package customExceptions;

public class FindFunctionException extends Exception
{
	public static final long serialVersionUID = 0L;
	@Override
	public String getMessage(){
		return "Find Exception : invalid arguments";
	}
}
