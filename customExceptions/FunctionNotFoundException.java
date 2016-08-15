package customExceptions;

public class FunctionNotFoundException extends Exception
{
	public static final long serialVersionUID = 0L;
	@Override
	public String getMessage(){
		return "Function Not Found Exception : You entered an invalid function or you entered one or more whitespaces";
	}
}