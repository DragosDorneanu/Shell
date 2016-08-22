package customExceptions;

public class RenameException extends Exception
{
	public static final long serialVersionUID = 0L;
	@Override
	public String getMessage(){
		return "Rename exception : Invalid number of arguments or arguments";
	}
}