package BaseElement;

import java.util.Date;

import Configuration.AccountType;
import Configuration.ClientType;

public class Account 
{
	private String accountID, clientID, password;
	private double balance;
	private Date openDate;
	private AccountType accountType;
	private ClientType ClientType;
	
	public Account()
	{
	}
	
	public Account( String accountID, String clientID, String password )
	{
		this.accountID = accountID;
		this.clientID = clientID;
		this.password = password;
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

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getBalance() {
		return balance;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setClientType(ClientType ClientType) {
		this.ClientType = ClientType;
	}

	public ClientType getClientType() {
		return ClientType;
	}
	
	public void depositBalance( double money )
	{ 
		 this.balance += money;
	}
	
	public void withdrawBalance( double money )
	{
		this.balance -= money;
	}
	
	public double checkBalance()
	{
		return this.balance;
	}
}
