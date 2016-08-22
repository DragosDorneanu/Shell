package customExceptions;

public class GrepException extends Exception
{
	public static final long serialVersionUID = 0L;
	@Override
	public String getMessage(){
		return "Grep Exception : to few arguments or invalid arguments";
	}
}