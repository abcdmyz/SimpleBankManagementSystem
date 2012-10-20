package DatabaseOperation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import BaseElement.Account;
import Configuration.AccountType;
import Configuration.ClientType;

public class AccountOperation 
{
	public void createAccount( Connection connection, Account account )
	{
		PreparedStatement pstmt;

		try
		{	
			pstmt = connection.prepareStatement ("INSERT INTO bank (accountID, clientID, accountType, clientType, password, balance, openDate) values ( ?, ?, ?, ?, ?, ?, ?)");
			
			pstmt.setString(1, account.getAccountID());
			pstmt.setString(2, account.getClientID());
			pstmt.setString(3, account.getAccountType().toString());
			pstmt.setString(4, account.getClientType().toString());
			pstmt.setString(5, account.getPassword());
			pstmt.setDouble(6, account.getBalance());
			pstmt.setDate(7, account.getOpenDate());
			
			pstmt.executeUpdate();

			pstmt.close();		
		}

		catch (SQLException ex)
		{
			System.err.println(ex.getMessage());
		}
		
	}
	
	public void updateAccountBalance( Connection connection, String accountID, Double balance )
	{
		PreparedStatement pstmt;

		try
		{	
			pstmt = connection.prepareStatement ("UPDATE bank set balance=? WHERE accountID=? ;");

			pstmt.setDouble(1, balance);
			pstmt.setString(2, accountID);
			
			pstmt.executeUpdate();

			pstmt.close();		
		}

		catch (SQLException ex)
		{
			System.err.println(ex.getMessage());
		}
	}
	
	public void updateAccountPassword( Connection connection, String accountID, String password )
	{
		PreparedStatement pstmt;

		try
		{	
			pstmt = connection.prepareStatement ("UPDATE bank set password=? WHERE accountID=? ;");

			pstmt.setString(1, password);
			pstmt.setString(2, accountID);
			
			pstmt.executeUpdate();

			pstmt.close();		
		}

		catch (SQLException ex)
		{
			System.err.println(ex.getMessage());
		}
	}
	
	public Account getAccount( Connection connection, String accountID )
	{
		PreparedStatement pstmt;
		ResultSet resultset;	

		Account account = new Account();

		try
		{	
			pstmt = connection.prepareStatement ("SELECT * FROM bank WHERE accountID = ? ;");

			pstmt.setString(1, accountID);
			resultset = pstmt.executeQuery();

			if ( resultset.next() )
			{
				account.setAccountID(resultset.getString("accountID"));
				account.setClientID(resultset.getString("clientID"));
				
				String type;
				type = resultset.getString("accountType");
				account.setAccountType(AccountType.getAccountType(type));
				
				type = resultset.getString("clientType");
				account.setClientType(ClientType.getClientType(type));
				
				account.setBalance(resultset.getDouble("balance"));
				account.setPassword(resultset.getString("password"));
				account.setOpenDate(resultset.getDate("openDate"));
			}
			
			pstmt.close();
			resultset.close();
		}

		catch (SQLException ex)
		{
			System.err.println(ex.getMessage());
		}

		return account;
	}
	
	public void deleteAccount( Connection connection, String accountID )
	{
		PreparedStatement pstmt;

		try
		{	
			pstmt = connection.prepareStatement ("DELETE FROM bank WHERE accountID=?");

			pstmt.setString(1, accountID);
			pstmt.executeUpdate();

			pstmt.close();		
		}

		catch (SQLException ex)
		{
			System.err.println(ex.getMessage());
		}
	}

}
