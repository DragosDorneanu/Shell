package customExceptions;

public class MkdirException extends Exception
{	
	public static final long serialVersionUID = 0L;
	@Override
	public String getMessage(){
		return "Mkdir Exception : new directory couldn't be created or invalid number of arguments";
	}
}
