package test.manager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import system.element.Account;
import system.element.Client;
import system.manager.AccountEnterpriseManager;
import system.manager.AccountOrdinaryManager;
import system.manager.AccountVIPManager;
import database.connection.JDBCConnection;
import database.operation.TbAccountOperation;
import database.operation.TbEnterpriseOperatorOperation;
import enumtype.AccountType;
import enumtype.ClientType;
import exception.dboperation.AccountDBOperationException;
import exception.elmanager.AccountManagerException;

public class AccountEnterpriseManagerTest 
{
	/*
	public static void createAccount() throws ClassNotFoundException, SQLException, AccountDBOperationException
	{
		Connection connection = JDBCConnection.getCommonConnection();
		
		TbAccountOperation.deleteAccountTable(connection);
		
		Date openDate1 = new Date();
		Account account1 = new Account("A0001","C0001","123");
		account1.setAccountType(AccountType.fixed_deposit);
		account1.setClientType(ClientType.ordinary);
		account1.setBalance(100);
		account1.setOpenDate(openDate1);
		TbAccountOperation.createAccount(connection, account1);
		
		Date openDate2 = new Date();
		Account account2 = new Account("A0002","C0002","123");
		account2.setAccountType(AccountType.fixed_deposit);
		account2.setClientType(ClientType.ordinary);
		account2.setBalance(100);
		account2.setOpenDate(openDate1);
		TbAccountOperation.createAccount(connection, account2);
		
		Date openDate3 = new Date();
		Account account3 = new Account("A0003","C0003","123");
		account3.setAccountType(AccountType.current_deposit);
		account3.setClientType(ClientType.vip);
		account3.setBalance(100);
		account3.setOpenDate(openDate1);
		TbAccountOperation.createAccount(connection, account3);
		
		Date openDate4 = new Date();
		Account account4 = new Account("A0004","C0004","123");
		account4.setAccountType(AccountType.current_deposit);
		account4.setClientType(ClientType.vip);
		account4.setBalance(100);
		account4.setOpenDate(openDate1);
		TbAccountOperation.createAccount(connection, account4);
		
		Date openDate5 = new Date();
		Account account5 = new Account("A0005","C0005","123");
		account5.setAccountType(AccountType.current_deposit);
		account5.setClientType(ClientType.enterprise);
		account5.setBalance(100);
		account5.setOpenDate(openDate1);
		TbAccountOperation.createAccount(connection, account5);
		
		Date openDate6 = new Date();
		Account account6 = new Account("A0006","C0006","123");
		account6.setAccountType(AccountType.current_deposit);
		account6.setClientType(ClientType.enterprise);
		account6.setBalance(100);
		account6.setOpenDate(openDate1);
		TbAccountOperation.createAccount(connection, account6);
		
		JDBCConnection.closeConnection(connection);
	}
	
	public static void createEnterpriseOperator() throws ClassNotFoundException, SQLException
	{
		Connection connection = JDBCConnection.getCommonConnection();
		
		TbEnterpriseOperatorOperation.deleteEnterpriseOperatorTable(connection);
		
		Client operator1 = new Client("A0005", "C0050", "999");
		operator1.setSuperClient("N");
		TbEnterpriseOperatorOperation.createEnterpriseOperator(connection, operator1);
		
		Client operator2 = new Client("A0005", "C0051", "999");
		operator1.setSuperClient("N");
		TbEnterpriseOperatorOperation.createEnterpriseOperator(connection, operator2);
		
		Client operator3 = new Client("A0006", "C0060", "999");
		operator1.setSuperClient("N");
		TbEnterpriseOperatorOperation.createEnterpriseOperator(connection, operator3);
		
		Client operator4 = new Client("A0006", "C0061", "999");
		operator1.setSuperClient("N");
		TbEnterpriseOperatorOperation.createEnterpriseOperator(connection, operator4);
	}
	
	public static void depositAccountTest() throws ClassNotFoundException, SQLException, AccountDBOperationException 
	{
		createAccount();
		createEnterpriseOperator();
		
		Connection connection = null;
		try 
		{
			connection = JDBCConnection.getCommonConnection();
			
			try 
			{
				AccountEnterpriseManager.depositAccount("A0005","C0051","999",100);
			} 
			catch (AccountManagerException e1) 
			{
				System.err.println(e1.getMessage());
			}
			
			try 
			{		
				AccountEnterpriseManager.depositAccount("A0005","C0059","999",100);
			} 
			catch (AccountManagerException e1) 
			{
				System.err.println(e1.getMessage());
			}
			
			try 
			{
				AccountEnterpriseManager.depositAccount("A0005","C0051","99",100);
			} 
			catch (AccountManagerException e1) 
			{
				System.err.println(e1.getMessage());
			}
			
			try 
			{
				AccountEnterpriseManager.depositAccount("A0005","C0051","999",-100);
			} 
			catch (AccountManagerException e1) 
			{
				System.err.println(e1.getMessage());
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
	
	public static void withdrawAccountTest() throws ClassNotFoundException, SQLException, AccountDBOperationException 
	{
		createAccount();
		createEnterpriseOperator();
		
		Connection connection = null;
		try 
		{
			connection = JDBCConnection.getCommonConnection();
			
			try 
			{
				AccountEnterpriseManager.withdrawAccount("A0005","C0051","999",100);
			} 
			catch (AccountManagerException e1) 
			{
				System.err.println(e1.getMessage());
			}
			
			try 
			{		
				AccountEnterpriseManager.withdrawAccount("A0005","C0061","999",100);
			} 
			catch (AccountManagerException e1) 
			{
				System.err.println(e1.getMessage());
			}
			
			try 
			{
				AccountEnterpriseManager.withdrawAccount("A0005","C0051","99",100);
			} 
			catch (AccountManagerException e1) 
			{
				System.err.println(e1.getMessage());
			}
			
			try 
			{
				AccountEnterpriseManager.withdrawAccount("A0005","C0051","999",-100);
			} 
			catch (AccountManagerException e1) 
			{
				System.err.println(e1.getMessage());
			}
			
			try 
			{
				AccountEnterpriseManager.withdrawAccount("A0005","C0051","999",1000);
			} 
			catch (AccountManagerException e1) 
			{
				System.err.println(e1.getMessage());
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
			
			account1.setAccountID("A0005");
			account2.setAccountID("A0006");
			
			Client operator = new Client();
			operator.setClientID("C0051");
			operator.setPassword("999");
			
			try 
			{
				try {
					AccountEnterpriseManager.transferAccount(account1, operator, account2, 100);
				} catch (AccountDBOperationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("OK");
			} 
			catch (AccountManagerException e1) 
			{
				System.err.println(e1.getMessage());
			}
			
			try 
			{	
				operator.setPassword("123");				
				AccountEnterpriseManager.transferAccount(account1, operator, account2, 100);
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
				operator.setClientID("C0066");
				operator.setPassword("999");
				
				AccountEnterpriseManager.transferAccount(account1, operator, account2, 100);
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
				operator.setClientID("C0051");
				operator.setPassword("999");
				
				account2.setAccountID("A0001");
				
				AccountEnterpriseManager.transferAccount(account1, operator, account2, 100);
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
				account2.setAccountID("A0006");
				
				AccountEnterpriseManager.transferAccount(account1, operator, account2, 100);
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
				
				AccountEnterpriseManager.transferAccount(account1, operator, account2, -100);
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
				AccountEnterpriseManager.transferAccount(account1, operator, account2, 10000);
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
	*/

}
