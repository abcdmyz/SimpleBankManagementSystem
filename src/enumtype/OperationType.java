package enumtype;

public enum OperationType 
{
	create("create"), deposit("deposit"), withdraw("withdraw"), checkbalance("checkbalance"), 
	transfer("transfer"), chpassword("chpassword"), cancel("cancel"), addoperator("addoperator"),
	withdrawin("withdrawin");
	
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
