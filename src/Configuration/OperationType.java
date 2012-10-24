package Configuration;

public enum OperationType 
{
	create("create"), deposit("deposit"), withdraw("withdraw"), check("check"), 
	transfer("transfer"), chpassword("chpassword"), cancel("cancel");
	
	private String typeName;
	
	OperationType( String typeName )
	{
		this.typeName = typeName;
	}
	
	public static OperationType getEnumFromString( String typeName )
	{
		if ( typeName != null )
		{
			return Enum.valueOf(OperationType.class, typeName);
		}
		
		return null;
	}
}
