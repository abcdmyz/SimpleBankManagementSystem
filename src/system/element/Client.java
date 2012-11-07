package system.element;

public class Client 
{
	private String accountID, clientID, password, superClient;
	
	public Client( String accountID, String clientID )
	{
		this.accountID = accountID;
		this.clientID = clientID;
	}
	
	public Client( String accountID, String clientID, String password )
	{
		this.accountID = accountID;
		this.clientID = clientID;
		this.password = password;
	}
	
	public Client()
	{
		
	}
	
	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}
	public String getAccountID() {
		return accountID;
	}
	public void setClientID(String clientID) {
		this.clientID = clientID;
	}
	public String getClientID() {
		return clientID;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return password;
	}
	public void setSuperClient(String superClient) {
		this.superClient = superClient;
	}
	public String getSuperClient() {
		return superClient;
	}
}
