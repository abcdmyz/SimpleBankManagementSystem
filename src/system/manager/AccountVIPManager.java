package system.manager;

import java.sql.Connection;
import java.sql.SQLException;

import system.element.Account;
import system.element.Client;
import database.connection.JDBCConnection;
import database.operation.TbAccountOperation;
import enumtype.ClientType;
import exception.dboperation.AccountDBOperationException;
import exception.elmanager.AccountManagerException;

public class AccountVIPManager extends AccountManager
{	
	public String createAccount( Account account ) throws AccountManagerException, AccountDBOperationException
	{
		if ( account.getBalance() <= 0 )
		{
			throw new AccountManagerException("VIP Account Initial Balance Must Greater Than 0");
		}
		
		if ( account.getBalance() <= 1000000 )
		{
			throw new AccountManagerException("VIP Account Initial Balance Must Greater Than 1000000");
		}
		
		String accountID = super.AccountIDGenerator();
		account.setAccountID(accountID);
		
		Connection connection = null;
		try 
		{
			connection = JDBCConnection.getCommonConnection();
			TbAccountOperation.createAccount(connection, account);
			connection.close();
			
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return accountID;
	}
	
	public void depositAccount( Account account, Client client, double balance ) throws AccountManagerException, AccountDBOperationException
	{	
		if ( ! account.getPassword().equals(client.getPassword()) )
		{
			throw new AccountManagerException("Account " + account.getAccountID() +  " Password Not Match");
		}
			
		if ( balance <0 )
		{
			throw new AccountManagerException("Deposit Balance Can't be Negative");
		}  
		Connection connection = null;
			
		try 
		{
			connection = JDBCConnection.getCommonConnection();
			TbAccountOperation.updateAccountBalance(connection, account.getAccountID(), account.getBalance()+balance);
			connection.close();
			
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void withdrawAccount( Account account, Client client, double balance ) throws AccountManagerException, AccountDBOperationException
	{	
		if ( ! account.getPassword().equals(client.getPassword()) )
		{
			throw new AccountManagerException("Account " + account.getAccountID() +  " Password Not Match");
		}
			
		if ( balance <0 )
		{
			throw new AccountManagerException("Withdraw Balance Can't be Negative");
		}                            
			
		if (account.getBalance()-balance < -100000 )
		{
			throw new AccountManagerException("VIP Account " + account.getAccountID() + " Can't Overdraw More Than 10000");
		}
			
		Connection connection = null;
		try 
		{
			connection = JDBCConnection.getCommonConnection();
			TbAccountOperation.updateAccountBalance(connection, account.getAccountID(), account.getBalance()-balance);
			connection.close();
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Account checkAccount( Account account, Client client ) throws AccountManagerException, AccountDBOperationException
	{
		if ( ! account.getPassword().equals(client.getPassword()) )
		{
			throw new AccountManagerException("Account " + account.getAccountID() +  " Password Not Match");
		}
		
		return account;
	}
	
	public void changePassword( Account account, Client client, String newPassword ) throws AccountManagerException, AccountDBOperationException
	{
		Connection connection = null;
		
		if ( ! account.getPassword().equals(client.getPassword()) )
		{
			throw new AccountManagerException("Account " + account.getAccountID() + " Password Not Match");
		}
	 
		try 
		{
			connection = JDBCConnection.getCommonConnection();
			TbAccountOperation.updateAccountPassword(connection, client.getPassword(), newPassword);
			connection.close();		
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void transferAccount( Account account, Client client, String accountID2, double balance ) throws AccountManagerException, AccountDBOperationException
	{
		Connection connection = null;
		Account account2 = new Account(); 
		
		try 
		{
			connection = JDBCConnection.getCommonConnection();
			account2 = TbAccountOperation.selectAccount(connection, accountID2);
		
			if ( ! account.getPassword().equals(client.getPassword()) )
			{
				throw new AccountManagerException("Account " + account.getAccountID() + " Password Not Match");
			}
			
			if ( !super.AccountIDExist(accountID2) )
			{
				throw new AccountManagerException("Account " + accountID2 + " Not Exist");
			}
				
			if ( balance <0 )
			{
				throw new AccountManagerException("Balance Can't be Negative");
			}                           
			
			if ( account2.getClientType().toString().equals(ClientType.enterprise.toString()) )
			{
				throw new AccountManagerException("VIP Account Can't Transfer to Enterprise's Account");
			}
			
			if (account.getBalance()-balance < -100000 )
			{
				throw new AccountManagerException("VIP Account " + account.getAccountID() + " Can't Overdraw More Than 100000");
			}
				
			
			
			TbAccountOperation.transferAccount(connection, account.getAccountID(), account2.getAccountID(), balance);
			connection.close();
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public double checkAccountBalance( Account account, Client client ) throws AccountManagerException
	{
		if ( ! account.getPassword().equals(client.getPassword()) )
		{
			throw new AccountManagerException("Account " + account.getAccountID() +  " Password Not Match");
		}
		
		return account.getBalance();
	}

	@Override
	public void addOperator( Account account, Client SuperClient, Client client) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Account checkAccountPassword(Account account, Client client) throws AccountManagerException, AccountDBOperationException 
	{
		Connection connection = null;
		Account account2 = new Account(); 
		
		try 
		{
			connection = JDBCConnection.getCommonConnection();
			account2 = TbAccountOperation.selectAccount(connection, account.getAccountID());
			
			if ( ! account2.getPassword().equals(client.getPassword()) )
			{
				throw new AccountManagerException("Account " + account.getAccountID() + " Password Not Match");
			}
		} 
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		// TODO Auto-generated method stub
		return null;
	}

}
