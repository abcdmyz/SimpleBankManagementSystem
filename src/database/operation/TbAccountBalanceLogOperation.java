package database.operation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import system.element.AccountBalanceLog;
import system.element.Log;
import enumtype.OperationType;

public class TbAccountBalanceLogOperation 
{
	public static void createAccountBalanceLog( Connection connection, AccountBalanceLog log )
	{
		try
		{
			
			PreparedStatement pstmt;
			pstmt = connection.prepareStatement ("INSERT INTO AccountBalanceLog (log_id, account_id, operation_type, operation_balance, left_balance, operation_time ) values ( ?, ?, ?, ?, ?, ?)");
			
			pstmt.setString(1, log.getLogID());
			pstmt.setString(2, log.getAccountID());
			pstmt.setString(3, log.getOperationType().toString());
			pstmt.setDouble(4, log.getOperationBalance());
			pstmt.setDouble(5, log.getLeftBalance());
			pstmt.setObject(6, log.getOperationTime());

			connection.setAutoCommit(false);
			
			pstmt.executeUpdate();

			pstmt.close();
			connection.commit();
		}

		catch (SQLException ex)
		{
			System.err.println(ex.getMessage());
		}
	}
	
	public static ArrayList<AccountBalanceLog> selectAccountBalanceLogsByAccountID( Connection connection, String accountID )
	{
		ArrayList<AccountBalanceLog> logs = new ArrayList<AccountBalanceLog>();
		
		PreparedStatement pstmt;
		ResultSet resultset;	

		try
		{	
			pstmt = connection.prepareStatement ("SELECT * FROM AccountBalanceLog WHERE account_id = ? ;");

			pstmt.setString(1, accountID);
			resultset = pstmt.executeQuery();

			while ( resultset.next() )
			{
				AccountBalanceLog log = new AccountBalanceLog();
				
				log.setLogID(resultset.getString("log_id"));
				log.setAccountID(resultset.getString("account_id"));
				log.setOperationBalance((resultset.getDouble("operation_balance")));
				log.setLeftBalance(resultset.getDouble("left_balance"));
				log.setOperationTime(resultset.getTimestamp("operation_date"));
				
	
				String type;
				type = resultset.getString("operation_type");
				log.setOperationType(OperationType.getEnumFromString(type));
				
				logs.add(log);
			}
			
			connection.setAutoCommit(false);
			pstmt.close();
			resultset.close();
			connection.commit();
		}

		catch (SQLException ex)
		{
			System.err.println(ex.getMessage());
		}
		

		return logs;
	}
	
	public static ArrayList<AccountBalanceLog> selectAccountBalanceLogsByAccountIDInTime( Connection connection, String accountID, Date startDate, Date endDate )
	{
		ArrayList<AccountBalanceLog> logs = new ArrayList<AccountBalanceLog>();
		
		PreparedStatement pstmt;
		ResultSet resultset;	

		try
		{	
			pstmt = connection.prepareStatement ("SELECT * FROM AccountBalanceLog WHERE account_id = ? AND operation_time >= ? AND operation_time <= ?;");

			pstmt.setString(1, accountID);
			pstmt.setObject(2, startDate);
			pstmt.setObject(3, endDate);
			
			resultset = pstmt.executeQuery();

			while ( resultset.next() )
			{
				AccountBalanceLog log = new AccountBalanceLog();
				
				log.setLogID(resultset.getString("log_id"));
				log.setAccountID(resultset.getString("account_id"));
				log.setOperationBalance((resultset.getDouble("operation_balance")));
				log.setLeftBalance(resultset.getDouble("left_balance"));
				log.setOperationTime(resultset.getTimestamp("operation_date"));
				
	
				String type;
				type = resultset.getString("operation_type");
				log.setOperationType(OperationType.getEnumFromString(type));
				
				logs.add(log);
			}
			
			connection.setAutoCommit(false);
			pstmt.close();
			resultset.close();
			connection.commit();
		}

		catch (SQLException ex)
		{
			System.err.println(ex.getMessage());
		}
		

		return logs;
	}
	
	public static void deleteLogTable( Connection connection ) throws SQLException
	{
		String deleteString;
		deleteString = "DELETE FROM AccountBalanceLog;";

		java.sql.Statement statement;

		statement = connection.createStatement();
		connection.setAutoCommit(false);
		
		statement.executeUpdate(deleteString);
		statement.close();

		connection.commit();
	}

}
