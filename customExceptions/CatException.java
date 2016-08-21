package customExceptions;

public class CatException extends Exception
{
	public static final long serialVersionUID = 0L;
	@Override
	public String getMessage(){
		return "Cat Exception : Too many arguments or invalid file name";
	}
}