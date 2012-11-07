package database.operation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import system.element.AccountBalanceLog;
import system.element.Client;
import enumtype.OperationType;
import exception.dboperation.AccountDBOperationException;

public class TbEnterpriseOperatorOperation 
{
	public static void createEnterpriseOperator( Connection connection, Client operator ) throws AccountDBOperationException 
	{
		try
		{	
			PreparedStatement pstmt;
			pstmt = connection.prepareStatement ("INSERT INTO EnterpriseOperator (account_id, client_id, password, super_client ) values ( ?, ?, ?, ? )");
			
			pstmt.setString(1, operator.getAccountID());
			pstmt.setString(2, operator.getClientID());
			pstmt.setString(3, operator.getPassword());
			pstmt.setString(4, operator.getSuperClient());
			
			//System.out.println("EO Password " +  operator.getPassword());

			connection.setAutoCommit(false);
			pstmt.executeUpdate();

			pstmt.close();
			connection.commit();
		}

		catch (SQLException ex)
		{
			System.err.println(ex.getMessage());
			throw new AccountDBOperationException("ClientID Has Existed For This Account");
		}
	}
	
	public static Client selectEnterpriseOperatorByAccountIDClientID( Connection connection, String accountID, String clientID ) throws AccountDBOperationException
	{
		Client operator = new Client();
		
		PreparedStatement pstmt;
		ResultSet resultset;	

		try
		{	
			pstmt = connection.prepareStatement ("SELECT * FROM EnterpriseOperator WHERE account_id = ? AND client_id = ?;");

			pstmt.setString(1, accountID);
			pstmt.setString(2, clientID);
			
			connection.setAutoCommit(false);
			resultset = pstmt.executeQuery();

			if ( resultset.next() )
			{
				operator.setAccountID(resultset.getString("account_id"));
				operator.setClientID(resultset.getString("client_id"));
				operator.setPassword(resultset.getString("password"));
				operator.setSuperClient(resultset.getString("super_client"));
				
				/*
				System.out.println(resultset.getString("account_id"));
				System.out.println(resultset.getString("client_id"));
				System.out.println(resultset.getString("password"));
				System.out.println(resultset.getString("super_client"));
				*/
				
			}
			else
			{
				pstmt.close();
				resultset.close();
				connection.commit();
				
				throw new AccountDBOperationException("Accout " + accountID + " Don't Have Operator " + clientID);
			}
			
			
			pstmt.close();
			resultset.close();
			connection.commit();
		}

		catch (SQLException ex)
		{
			System.err.println(ex.getMessage());
		}
		

		return operator;
	}
	
	public static void updateOperatorPassword( Connection connection, String accountID, String clientID, String password ) throws AccountDBOperationException
	{
		PreparedStatement pstmt;

		try
		{	
			pstmt = connection.prepareStatement ("UPDATE EnterpriseOperator set password=? WHERE account_id=? AND client_id=? ;");

			pstmt.setString(1, password);
			pstmt.setString(2, accountID);
			pstmt.setString(3, clientID);
			
			connection.setAutoCommit(false);
			pstmt.executeUpdate();

			pstmt.close();	
			connection.commit();
		}

		catch (SQLException ex)
		{
			System.err.println(ex.getMessage());
			
			throw new AccountDBOperationException("Update Operator Password Fail");
		}
	}
	
	public static void deleteEnterpriseOperatorTable( Connection connection ) throws SQLException
	{
		String deleteString;
		deleteString = "DELETE FROM EnterpriseOperator;";

		java.sql.Statement statement;

		statement = connection.createStatement();
		
		connection.setAutoCommit(false);
		
		statement.executeUpdate(deleteString);
		statement.close();

		connection.commit();
	}

}
