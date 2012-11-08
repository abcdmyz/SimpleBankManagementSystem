package system.manager;


import static system.manager.AccountManager.accountIDSet;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import system.element.Account;
import system.element.Client;

import database.connection.JDBCConnection;
import database.operation.TbAccountOperation;
import database.operation.TbEnterpriseOperatorOperation;
import enumtype.ClientType;
import exception.dboperation.AccountDBOperationException;
import exception.elmanager.AccountManagerException;



public abstract class AccountManager 
{
	protected static HashSet<String>  accountIDSet = new HashSet<String>();
	private static long accountNumForTest = 0;
	
	public static void initial() throws ClassNotFoundException
	{
		accountIDSet.clear();
		accountNumForTest = 0;
		
		Connection connection = JDBCConnection.getCommonConnection();
		
		try 
		{
			accountIDSet = TbAccountOperation.loadAccountID(connection);
		}
		catch (AccountDBOperationException e) 
		{
			System.out.println(e.getMessage());
		}
	}
	
	protected boolean AccountIDExist( String accountID )
	{
		return accountIDSet.contains(accountID);
	}
	
	protected String AccountIDGenerator()
	{
		String accountID = new String();
		long randomNum;
		
		Random random =new Random(); 
		
		do
		{
			/*
			 * This apart for true System
			randomNum = Math.abs(random.nextInt()) % 999999;
			accountID = String.format("A" + "%06d", randomNum);
			*/
			
			//This part for test
			accountNumForTest++;
			accountID = String.format("A" + "%03d", accountNumForTest);
			
		}while ( accountIDSet.contains(accountID) );
		
		accountIDSet.add(accountID);
			
		return accountID;
	}
	
	public static Account selectAccount( String accountID ) throws AccountManagerException, AccountDBOperationException
	{
		Connection connection = null;
		Account account = null;
		
		try 
		{
			connection = JDBCConnection.getCommonConnection();
			account = TbAccountOperation.selectAccount(connection, accountID);
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		} 
		
		return account;
	}
	
	public static void createAccountForTest( Account account ) throws AccountManagerException, AccountDBOperationException
	{		
		Connection connection = null;
		try 
		{
			accountIDSet.add(account.getAccountID());
			
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
	}
	
	public static void deleteAllAccountForTest()
	{
		Connection connection = null;
		
		try 
		{
			connection = JDBCConnection.getCommonConnection();
			TbAccountOperation.deleteAccountTable(connection);
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
	
	public static void deleteAllClientForTest()
	{
		Connection connection = null;
		
		try 
		{
			connection = JDBCConnection.getCommonConnection();
			TbEnterpriseOperatorOperation.deleteEnterpriseOperatorTable(connection);
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
	
	public abstract String createAccount( Account account ) throws AccountManagerException, AccountDBOperationException;
	public abstract void depositAccount( Account account, Client client, double balance ) throws AccountManagerException, AccountDBOperationException;
	public abstract void withdrawAccount( Account account, Client client, double balance ) throws AccountManagerException, AccountDBOperationException;
	public abstract void transferAccount( Account account, Client client, String accountID2, double balance ) throws AccountManagerException, AccountDBOperationException;;
	public abstract void changePassword( Account account, Client client, String newPassword ) throws AccountManagerException, AccountDBOperationException;
	public abstract double checkAccountBalance( Account account, Client client ) throws AccountManagerException, AccountDBOperationException;;
	public abstract void addOperator( Account account, Client SuperClient, Client client) throws AccountManagerException, AccountDBOperationException;
	public abstract Account checkAccountPassword( Account account, Client client ) throws AccountManagerException, AccountDBOperationException;
}
