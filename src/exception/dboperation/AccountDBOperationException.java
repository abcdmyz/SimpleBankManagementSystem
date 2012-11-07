package exception.dboperation;

public class AccountDBOperationException extends Exception  
{
	public AccountDBOperationException()
	{
		super();
	}
	
	public AccountDBOperationException( String message )
	{
		super(message);
	}
}
