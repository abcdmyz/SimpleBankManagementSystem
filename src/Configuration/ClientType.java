package Configuration;

public enum ClientType 
{
	ordinary, vip, enterprise;
	
	public static ClientType getClientType( String type )
	{
		if ( type.equals("ordinary") )
			return ClientType.ordinary;
		
		if ( type.equals("vip") )
			return ClientType.vip;
		
		if ( type.equals("enterprise") )
			return ClientType.enterprise;
		
		return null;
	}
	
}
