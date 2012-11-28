package system.manager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import system.element.Account;
import system.element.Client;
import database.connection.JDBCConnection;
import database.operation.TbAccountOperation;
import enumtype.ClientType;
import exception.dboperation.AccountDBOperationException;
import exception.elmanager.AccountManagerException;

public class AccountOrdinaryManager extends AccountManager
{
	public String createAccount( Account account ) throws AccountManagerException, AccountDBOperationException
	{
		if ( account.getBalance() <= 0 )
		{
			throw new AccountManagerException("Ordinary Account Initial Balance Must Greater Than 0");
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
		} 
		catch (ClassNotFoundException e) 
		{
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
			
		if ( account.getClientType().equals(ClientType.ordinary) && account.getBalance()-balance <0 )
		{
			throw new AccountManagerException("Ordianry Account " + account.getAccountID() + " Has No Enough Money in Account");
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
			TbAccountOperation.updateAccountPassword(connection, account.getAccountID(), newPassword);
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
			
			if ( ! account2.getClientType().equals(ClientType.ordinary) )
			{
				throw new AccountManagerException("Ordinary Account Can Only Transfer to Ordinary Account");
			}
			
			if ( ! account.getClientID().equals(account2.getClientID() ) )
			{
				throw new AccountManagerException("Ordinary Account Can't Transfer to Other Client's Account");
			}
			
			if ( account.getBalance()-balance <0 )
			{
				throw new AccountManagerException("Ordianry Account " + account.getAccountID() + " Has No Enough Money in Account");
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
	public void addOperator(Account account, Client SuperClient, Client client) {
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
