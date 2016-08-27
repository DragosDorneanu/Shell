package customExceptions;

public class MoveException extends Exception 
{
	public static final long serialVersionUID = 0L;
	@Override
	public String getMessage(){
		return "Move exception : to few arguments or invalid move target";
	}
}