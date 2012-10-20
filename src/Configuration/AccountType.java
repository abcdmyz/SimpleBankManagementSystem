package Configuration;

public enum AccountType 
{	
	fixed_deposit, current_deposit;
	
	public static AccountType getAccountType( String type )
	{
		if ( type.equals("fix") )
			return AccountType.fixed_deposit;
		
		if ( type.equals("current") )
			return AccountType.current_deposit;
		
		return null;
	}
}
