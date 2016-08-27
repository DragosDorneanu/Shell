package customExceptions;

public class MoveException extends Exception 
{
	public static final long serialVersionUID = 0L;
	@Override
	public String getMessage(){
		return "Move Exception : to few arguments or invalid move target";
	}
}
