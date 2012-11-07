package test.manager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import system.element.Account;
import system.manager.AccountOrdinaryManager;
import system.manager.AccountVIPManager;
import database.connection.JDBCConnection;
import database.operation.TbAccountOperation;
import enumtype.AccountType;
import enumtype.ClientType;
import exception.dboperation.AccountDBOperationException;
import exception.elmanager.AccountManagerException;

public class AccountVIPManagerTest 
{
	/*
	public static void createAccountTest() throws ClassNotFoundException, SQLException, AccountDBOperationException
	{
		Connection connection = JDBCConnection.getCommonConnection();
		
		TbAccountOperation.deleteAccountTable(connection);
		
		Account account1 = new Account("C0001","123");
		account1.setAccountType(AccountType.fixed_deposit);
		account1.setClientType(ClientType.ordinary);
		account1.setBalance(100);
		try {
			AccountVIPManager.createAccount(account1);
		} catch (AccountManagerException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		
		
		Account account2 = new Account("C0001","123");
		account2.setAccountType(AccountType.fixed_deposit);
		account2.setClientType(ClientType.ordinary);
		account2.setBalance(100);
		try {
			AccountVIPManager.createAccount(account2);
		} catch (AccountManagerException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		
		
		Account account3 = new Account("C0003","123");
		account3.setAccountType(AccountType.current_deposit);
		account3.setClientType(ClientType.vip);
		account3.setBalance(100);
		try {
			AccountVIPManager.createAccount(account3);
		} catch (AccountManagerException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		
		Date openDate4 = new Date();
		Account account4 = new Account("C0004","123");
		account4.setAccountType(AccountType.current_deposit);
		account4.setClientType(ClientType.vip);
		account4.setBalance(100);
		try {
			AccountVIPManager.createAccount(account4);
		} catch (AccountManagerException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		
		
		Account account5 = new Account("C0005","123");
		account5.setAccountType(AccountType.current_deposit);
		account5.setClientType(ClientType.enterprise);
		account5.setBalance(100);
		try {
			AccountVIPManager.createAccount(account5);
		} catch (AccountManagerException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		
		Account account6 = new Account("C0006","123");
		account6.setAccountType(AccountType.current_deposit);
		account6.setClientType(ClientType.enterprise);
		account6.setBalance(100);
		try {
			AccountVIPManager.createAccount(account6);
		} catch (AccountManagerException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		
		JDBCConnection.closeConnection(connection);
	}
	
	public static void depositAccountTest() 
	{
		Connection connection = null;
		try 
		{
			connection = JDBCConnection.getCommonConnection();
			
			try 
			{
				AccountVIPManager.depositAccount("A025668", "123", 100);
			} 
			catch (AccountManagerException e1) 
			{
				System.err.println(e1.getMessage());
			} catch (AccountDBOperationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try 
			{		
				AccountVIPManager.depositAccount("A025668", "000", 100);
			} 
			catch (AccountManagerException e1) 
			{
				System.err.println(e1.getMessage());
			} catch (AccountDBOperationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try 
			{
				AccountVIPManager.depositAccount("A11111", "123", 100);
			} 
			catch (AccountManagerException e1) 
			{
				System.err.println(e1.getMessage());
			} catch (AccountDBOperationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try 
			{
				AccountVIPManager.depositAccount("A025668", "123", -100);
			} 
			catch (AccountManagerException e1) 
			{
				System.err.println(e1.getMessage());
			} catch (AccountDBOperationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try 
			{
				JDBCConnection.closeConnection(connection);
			} 
			catch (SQLException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		catch (ClassNotFoundException e2) 
		{
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		
	}
	
	public static void withdrawAccountTest() 
	{
		Connection connection = null;
		try 
		{
			connection = JDBCConnection.getCommonConnection();
			
			try 
			{
				AccountVIPManager.withdrawAccount("A168921", "123", 100);
				System.out.println("OK 1");
			} 
			catch (AccountManagerException e1) 
			{
				System.err.println(e1.getMessage());
			} catch (AccountDBOperationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try 
			{
				AccountVIPManager.withdrawAccount("A168921", "000", 100);
				System.out.println("OK 2");
			} 
			catch (AccountManagerException e1) 
			{
				System.err.println(e1.getMessage());
			} catch (AccountDBOperationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try 
			{		
				AccountVIPManager.withdrawAccount("A168921", "123", -100);
				System.out.println("OK 3");
			} 
			catch (AccountManagerException e1) 
			{
				System.err.println(e1.getMessage());
			} catch (AccountDBOperationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try 
			{
				AccountVIPManager.withdrawAccount("A168921", "123", 1000);
				System.out.println("OK 4");
			} 
			catch (AccountManagerException e1) 
			{
				System.err.println(e1.getMessage());
			} catch (AccountDBOperationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try 
			{
				AccountVIPManager.withdrawAccount("A168921", "123", 99999);
				System.out.println("OK 5");
			} 
			catch (AccountManagerException e1) 
			{
				System.err.println(e1.getMessage());
			} catch (AccountDBOperationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try 
			{
				JDBCConnection.closeConnection(connection);
			} 
			catch (SQLException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		catch (ClassNotFoundException e2) 
		{
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		
	}
	
	
	public static void changePasswordTest() 
	{
		Connection connection = null;
		try 
		{
			connection = JDBCConnection.getCommonConnection();
			
			try 
			{
				AccountVIPManager.changeAccountPassword("A403930", "123", "456");
			} 
			catch (AccountManagerException e1) 
			{
				System.err.println(e1.getMessage());
			} catch (AccountDBOperationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try 
			{		
				AccountVIPManager.changeAccountPassword("A524308", "555", "666");
			} 
			catch (AccountManagerException e1) 
			{
				System.err.println(e1.getMessage());
			} catch (AccountDBOperationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try 
			{
				JDBCConnection.closeConnection(connection);
			} 
			catch (SQLException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		catch (ClassNotFoundException e2) 
		{
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		
	}
	
	public static void transferAccountTest() 
	{
		Connection connection = null;
		try 
		{
			connection = JDBCConnection.getCommonConnection();
			Account account1 = new Account();
			Account account2 = new Account();
			
			account1.setAccountID("A168921");
			account1.setPassword("123");
			
			account2.setAccountID("A236406");
			
			try 
			{
				AccountVIPManager.transferAccount(account1, account2, 100);
				System.out.println("OK");
			} 
			catch (AccountManagerException e1) 
			{
				System.err.println(e1.getMessage());
			} catch (AccountDBOperationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try 
			{	
				account1.setPassword("111");
				AccountVIPManager.transferAccount(account1, account2, 100);
			} 
			catch (AccountManagerException e1) 
			{
				System.err.println(e1.getMessage());
			} catch (AccountDBOperationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try 
			{
				account1.setPassword("123");
				account2.setAccountID("A268344");
				AccountVIPManager.transferAccount(account1, account2, 100);
			} 
			catch (AccountManagerException e1) 
			{
				System.err.println(e1.getMessage());
			} catch (AccountDBOperationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try 
			{
				account2.setAccountID("A116960");
				AccountVIPManager.transferAccount(account1, account2, 100);
			} 
			catch (AccountManagerException e1) 
			{
				System.err.println(e1.getMessage());
			} catch (AccountDBOperationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			try 
			{
				JDBCConnection.closeConnection(connection);
			} 
			catch (SQLException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		catch (ClassNotFoundException e2) 
		{
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		
	}
	
	public static void printAccountBalance( Account account )
	{
		System.out.println("AccountID: " + account.getAccountID() + " Balance: " + account.getBalance());
	}
	
	public static void printAccount( Account account )
	{
		System.out.println("AccountID: " + account.getAccountID());
		System.out.println("ClientID: " + account.getClientID());
		System.out.println("AccountType: " + account.getAccountType().toString());
		System.out.println("ClientType: " + account.getClientType().toString());
		System.out.println("Password: " + account.getPassword());
		System.out.println("OpenDate: " + account.getOpenDate().toString());
		System.out.println("Balance: " + account.getBalance());
	}
	*/
}
