package enumtype;

public enum ClientType 
{
	ordinary("ordinary"), vip("vip"), enterprise("enterprise");
	
	private String typeName;
	
	private ClientType( String typeName )
	{
		this.typeName = typeName;
	}
	
	public static ClientType getEnumFromString( String typeName )
	{
		if ( typeName != null )
		{
			return Enum.valueOf(ClientType.class, typeName);
		}
		
		return null;
	}
}
