package Configuration;

public enum AccountType 
{	
	fixed_deposit("fixed"), current_deposit("current");
	
	private String typeName;
	
	private AccountType( String tyepName )
	{
		this.typeName = typeName;
	}
	
	public static AccountType getEnumFromString( String typeName )
	{
		if ( typeName != null )
		{
			return Enum.valueOf(AccountType.class, typeName);
		}
		
		return null;
	}
	

}
