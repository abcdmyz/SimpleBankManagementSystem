package Configuration;

public enum Position 
{
	operator("operator"), manager("manager"), director("director"), admin("admin");
	
	private String typeName;
	
	Position( String typeName )
	{
		this.typeName = typeName;
	}
	
	public static Position getEnumFromString( String typeName )
	{
		if ( typeName != null )
		{
			return Enum.valueOf(Position.class, typeName);
		}
		
		return null;
	}

}
