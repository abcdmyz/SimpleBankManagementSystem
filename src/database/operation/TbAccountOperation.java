package database.operation;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;

import enumtype.AccountType;
import enumtype.ClientType;
import exception.dboperation.AccountDBOperationException;

import system.element.definition.Account;



public class TbAccountOperation 
{
	public static HashSet<String> loadAccountID( Connection connection ) throws AccountDBOperationException
	{
		HashSet<String> accountSet = new HashSet<String>();
		accountSet.clear();
		
		PreparedStatement pstmt;
		ResultSet resultset;	
		String accountID;

		try
		{	
			pstmt = connection.prepareStatement ("SELECT * FROM account;");
			
			connection.setAutoCommit(false);
			resultset = pstmt.executeQuery();

			while ( resultset.next() )
			{
				accountID = resultset.getString("account_id");
				accountSet.add(accountID);
			}
			
			pstmt.close();
			resultset.close();
			connection.commit();
		}

		catch (SQLException ex)
		{
			System.err.println(ex.getMessage());
			
			throw new AccountDBOperationException("Load AccountID Fail");		
		}
		
		return accountSet;
		
	}
	
	public static void createAccount( Connection connection, Account account ) throws AccountDBOperationException
	{
		
		try
		{	
			PreparedStatement pstmt;
			pstmt = connection.prepareStatement ("INSERT INTO account (account_id, client_id, account_type, client_type, password, balance, open_date) values ( ?, ?, ?, ?, ?, ?, ?)");
			
			pstmt.setString(1, account.getAccountID());
			pstmt.setString(2, account.getClientID());
			pstmt.setString(3, account.getAccountType().toString());
			pstmt.setString(4, account.getClientType().toString());
			pstmt.setString(5, account.getPassword());
			pstmt.setDouble(6, account.getBalance());
			pstmt.setObject(7, account.getOpenDate());
			
			connection.setAutoCommit(false);
			
			pstmt.executeUpdate();

			pstmt.close();
			connection.commit();
		}

		catch (SQLException ex)
		{
			System.err.println(ex.getMessage());
			
			throw new AccountDBOperationException("Create Account Fail");
		}
		
	}
	
	public static void updateAccountBalance( Connection connection, String accountID, double balance ) throws AccountDBOperationException
	{
		PreparedStatement pstmt;

		try
		{	
			pstmt = connection.prepareStatement ("UPDATE account set balance=? WHERE account_id=? ;");

			pstmt.setDouble(1, balance);
			pstmt.setString(2, accountID);

			connection.setAutoCommit(false);
			pstmt.executeUpdate();

			pstmt.close();
			connection.commit();
		}

		catch (SQLException ex)
		{
			System.err.println(ex.getMessage());
			
			throw new AccountDBOperationException("Update Account Balance Fail");
		}
	}
	
	public static void updateAccountPassword( Connection connection, String accountID, String password ) throws AccountDBOperationException
	{
		PreparedStatement pstmt;

		try
		{	
			pstmt = connection.prepareStatement ("UPDATE account set password=? WHERE account_id=? ;");

			pstmt.setString(1, password);
			pstmt.setString(2, accountID);
			
			connection.setAutoCommit(false);
			pstmt.executeUpdate();

			pstmt.close();	
			connection.commit();
		}

		catch (SQLException ex)
		{
			System.err.println(ex.getMessage());
			
			throw new AccountDBOperationException("Update Account Password Fail");
		}
	}
	
	public static Account selectAccount( Connection connection, String accountID ) throws AccountDBOperationException
	{
		PreparedStatement pstmt;
		ResultSet resultset;	

		Account account = new Account();

		try
		{	
			pstmt = connection.prepareStatement ("SELECT * FROM account WHERE account_id = ? ;");

			pstmt.setString(1, accountID);
			
			connection.setAutoCommit(false);
			resultset = pstmt.executeQuery();

			if ( resultset.next() )
			{
				account.setAccountID(resultset.getString("account_id"));
				account.setClientID(resultset.getString("client_id"));

				
				String type;
				type = resultset.getString("account_type");
				account.setAccountType(AccountType.getEnumFromString(type));
				
				type = resultset.getString("client_type");
				account.setClientType(ClientType.getEnumFromString(type));
				
				account.setBalance(resultset.getDouble("balance"));
				account.setPassword(resultset.getString("password"));
				account.setOpenDate(resultset.getDate("open_date"));
			}
			
			
			pstmt.close();
			resultset.close();
			connection.commit();
		}

		catch (SQLException ex)
		{
			System.err.println(ex.getMessage());
			
			throw new AccountDBOperationException("Select Account Fail");
		}

		return account;
	}
	
	public static void deleteAccount( Connection connection, String accountID ) throws AccountDBOperationException
	{
		PreparedStatement pstmt;

		try
		{	
			pstmt = connection.prepareStatement ("DELETE FROM account WHERE account_id=?");

			pstmt.setString(1, accountID);
			
			connection.setAutoCommit(false);
			pstmt.executeUpdate();

			pstmt.close();	
			connection.commit();
		}

		catch (SQLException ex)
		{
			System.err.println(ex.getMessage());
			
			throw new AccountDBOperationException("Delete Account Fail");
		}
	}
	
	public static void transferAccount( Connection connection, String accountID1, String accountID2, double balance) throws AccountDBOperationException
	{
		PreparedStatement pstmt1;
		PreparedStatement pstmt2;

		try
		{	
			pstmt1 = connection.prepareStatement ("UPDATE account set balance=balance-" + balance + " WHERE account_id=? ;");
			pstmt2 = connection.prepareStatement ("UPDATE account set balance=balance+" + balance + " WHERE account_id=? ;");

			pstmt1.setString(1, accountID1);
			pstmt2.setString(1, accountID2);

			connection.setAutoCommit(false);
			pstmt1.executeUpdate();
			pstmt2.executeUpdate();

			pstmt1.close();
			pstmt2.close();
			connection.commit();
		}

		catch (SQLException ex)
		{
			System.err.println(ex.getMessage());
			
			throw new AccountDBOperationException("Transfer Account Fail");
		}
	}
	
	public static void deleteAccountTable( Connection connection ) throws SQLException
	{
		String deleteString;
		deleteString = "DELETE FROM account;";

		java.sql.Statement statement;

		statement = connection.createStatement();
		
		connection.setAutoCommit(false);
		
		statement.executeUpdate(deleteString);
		statement.close();

		connection.commit();
	}

}
