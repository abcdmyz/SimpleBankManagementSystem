package system.manager;

import java.sql.Connection;
import java.sql.SQLException;

import system.element.Account;
import system.element.Client;
import database.connection.JDBCConnection;
import database.operation.TbAccountOperation;
import database.operation.TbEnterpriseOperatorOperation;
import enumtype.ClientType;
import exception.dboperation.AccountDBOperationException;

import exception.elmanager.AccountManagerException;

public class AccountEnterpriseManager extends AccountManager
{ 
	public String createAccount( Account account ) throws AccountDBOperationException, AccountManagerException
	{
		if ( account.getBalance() <= 0 )
		{
			throw new AccountManagerException("Enterprise Account Initial Balance Must Greater Than 0");
		}
		
		String accountID = super.AccountIDGenerator();
		account.setAccountID(accountID);
		
		Client operator = new Client();
		operator.setAccountID(account.getAccountID());
		operator.setClientID(account.getClientID());
		operator.setPassword(account.getPassword());
		operator.setSuperClient("Y");
		
		//System.out.println("password " + account.getPassword());
		
		Connection connection = null;
		try 
		{
			connection = JDBCConnection.getCommonConnection();
			TbAccountOperation.createAccount(connection, account);
			TbEnterpriseOperatorOperation.createEnterpriseOperator(connection, operator);
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
	
	public void changePassword( Account account, Client client, String newPassword ) throws AccountManagerException, AccountDBOperationException
	{
		Client operator = null;
		Connection connection = null;
		
		try 
		{
			connection = JDBCConnection.getCommonConnection();
			operator = TbEnterpriseOperatorOperation.selectEnterpriseOperatorByAccountIDClientID(connection, account.getAccountID(), client.getClientID());
			
			if ( operator == null )
			{
				throw new AccountManagerException("Operator " + client.getClientID() + " Not Exist");
			}			
			if ( !operator.getPassword().equals(client.getPassword()) )
			{
				throw new AccountManagerException("Operator " + operator.getClientID() + " Password Not Match");
			}
			
			TbEnterpriseOperatorOperation.updateOperatorPassword(connection, account.getAccountID(), client.getClientID(), newPassword);
			
			connection.close();
		
		} 
		catch (ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	public void depositAccount( Account account, Client client, double balance ) throws AccountManagerException, AccountDBOperationException
	{
		Client operator = null;
		Connection connection = null;
		
		try 
		{
			connection = JDBCConnection.getCommonConnection();
			operator = TbEnterpriseOperatorOperation.selectEnterpriseOperatorByAccountIDClientID(connection, account.getAccountID(), client.getClientID());
			
			
			if ( !operator.getPassword().equals(client.getPassword()) )
			{
				throw new AccountManagerException("Operator " + operator.getClientID() + " Password Not Match");
			}
			
			if ( balance <0 )
			{
				throw new AccountManagerException("Deposit Balance Can't be Negative");
			} 
			
			TbAccountOperation.updateAccountBalance(connection, account.getAccountID(), account.getBalance()+balance);
			
			connection.close();
		
		} 
		catch (ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 	
		
	}
	
	public void withdrawAccount( Account account, Client client, double balance ) throws AccountManagerException, AccountDBOperationException
	{
		Client operator = null;
		Connection connection = null;
		
		try 
		{
			connection = JDBCConnection.getCommonConnection();
			operator = TbEnterpriseOperatorOperation.selectEnterpriseOperatorByAccountIDClientID(connection, account.getAccountID(), client.getClientID());
			
					
			if ( !operator.getPassword().equals(client.getPassword()) )
			{
				throw new AccountManagerException("Operator " + operator.getClientID() + " Password Not Match");
			}
			
			if ( balance <0 )
			{
				throw new AccountManagerException("Withdraw Balance Can't be Negative");
			}                           
			
			if ( account.getBalance()-balance < 0 )
			{
				throw new AccountManagerException("Enterprise Account " + account.getAccountID() + " Has No Enough Money");
			}
			
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
	
	public Account checkAccount( Account account, Client client, double balance ) throws AccountManagerException, AccountDBOperationException
	{
		Client operator = null;
		Connection connection = null;
		
		try 
		{
			connection = JDBCConnection.getCommonConnection();
			operator = TbEnterpriseOperatorOperation.selectEnterpriseOperatorByAccountIDClientID(connection, account.getAccountID(), client.getClientID());
			
					
			if ( !operator.getPassword().equals(client.getPassword()) )
			{
				throw new AccountManagerException("Operator " + operator.getClientID() + " Password Not Match");
			}
			
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
		
		return account;
	}
	
	public void transferAccount( Account account, Client client, String accountID2, double balance ) throws AccountManagerException, AccountDBOperationException
	{
		Connection connection = null;
		Account account2= null;
		Client operator= null;
		
		try 
		{
			connection = JDBCConnection.getCommonConnection();
			account2 = TbAccountOperation.selectAccount(connection, accountID2);
			
			operator = TbEnterpriseOperatorOperation.selectEnterpriseOperatorByAccountIDClientID(connection, account.getAccountID(), client.getClientID());
			
			
			
			if ( ! operator.getPassword().equals(client.getPassword()) )
			{
				throw new AccountManagerException("Operator " + client.getClientID() + " Password Not Match");
			}
			
			if ( balance <0 )
			{
				throw new AccountManagerException("Balance Can't be Negative");
			}                           
			
			if ( account.getBalance()-balance <0 )
			{
				throw new AccountManagerException("Enterprise Account " + account.getAccountID() + " Has No Enough Money in Account");
			}
			
			if ( ! account2.getClientType().equals(ClientType.enterprise) )
			{
				throw new AccountManagerException("Enterprise Account Can't Transfer to Non-Enterprise's Account");
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
	
	public double checkAccountBalance( Account account, Client client ) throws AccountDBOperationException, AccountManagerException
	{
		Connection connection = null;
		Client operator= null;
		
		try 
		{
			connection = JDBCConnection.getCommonConnection();
			operator = TbEnterpriseOperatorOperation.selectEnterpriseOperatorByAccountIDClientID(connection, account.getAccountID(), client.getClientID());
			
			
			if ( ! operator.getPassword().equals(client.getPassword()) )
			{
				throw new AccountManagerException("Operator " + client.getClientID() + " Password Not Match");
			}
		} 
		catch (ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	
		return account.getBalance();
	}
	
	public void addOperator( Account account, Client superClient, Client client ) throws AccountManagerException, AccountDBOperationException
	{
		Connection connection;
		Client superOperator = new Client();
		
		if ( !account.getClientType().equals(ClientType.enterprise) )
		{
			throw new AccountManagerException("Non-Enterprise Account Have More Than One Client");
		}
		
		try 
		{
			connection = JDBCConnection.getCommonConnection();
			superOperator = TbEnterpriseOperatorOperation.selectEnterpriseOperatorByAccountIDClientID(connection, superClient.getAccountID(), superClient.getClientID());
			
			//System.out.println(superOperator.getPassword());
			//System.out.println(superClient.getPassword());
			
			if ( ! superOperator.getPassword().equals(superClient.getPassword()) )
			{
				throw new AccountManagerException("Super Operator " + superClient.getClientID() + " Password Not Match");
			}
			if ( !superOperator.getSuperClient().equals("Y"))
			{
				throw new AccountManagerException("Operator " + superClient.getClientID() + " is Not a Super Operator");
			}
			
			client.setAccountID(superOperator.getAccountID());
			client.setSuperClient("N");
			
			TbEnterpriseOperatorOperation.createEnterpriseOperator(connection, client);
		} 
		catch (ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}

	
}
