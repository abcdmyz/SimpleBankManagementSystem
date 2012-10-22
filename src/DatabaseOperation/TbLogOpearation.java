package DatabaseOperation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import BaseElement.Account;
import BaseElement.Log;
import Configuration.AccountType;
import Configuration.ClientType;
import Configuration.OperationType;

public class TbLogOpearation 
{
	public static void createLog( Connection connection, Log log )
	{
		try
		{	
			PreparedStatement pstmt;
			pstmt = connection.prepareStatement ("INSERT INTO log (log_id, staff_id, account_id, client_id, operation_type, operated_account_id, operated_balance, log_time) values ( ?, ?, ?, ?, ?, ?, ?, ?)");
			
			pstmt.setString(1, log.getLogID());
			pstmt.setString(2, log.getStaffID());
			pstmt.setString(3, log.getAccountID());
			pstmt.setString(4, log.getClientID());
			pstmt.setString(5, log.getOperationType().toString());
			pstmt.setString(6, log.getOperatedAccountID());
			pstmt.setDouble(7, log.getOperatedBalance());
			pstmt.setObject(8, log.getLogTime());
			
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
	
	public static Log selectLogByStaffID( Connection connection, String staffID )
	{
		PreparedStatement pstmt;
		ResultSet resultset;	

		Log log = new Log();

		try
		{	
			pstmt = connection.prepareStatement ("SELECT * FROM log WHERE staff_id = ? ;");

			pstmt.setString(1, staffID);
			resultset = pstmt.executeQuery();

			if ( resultset.next() )
			{
				log.setLogID(resultset.getString("log_id"));
				log.setStaffID(resultset.getString("staff_id"));
				log.setAccountID(resultset.getString("account_id"));
				log.setClientID(resultset.getString("client_id"));
				log.setOperatedAccountID(resultset.getString("operated_account_id"));
				log.setOperatedBalance(resultset.getDouble("operated_balance"));
				log.setLogTime(resultset.getDate("log_time"));

				
				String type;
				type = resultset.getString("operation_type");
				log.setOperationType(OperationType.getEnumFromString(type));
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

		return log;
	}
	
	public static Log selectLogByAccountID( Connection connection, String accountID )
	{
		PreparedStatement pstmt;
		ResultSet resultset;	

		Log log = new Log();

		try
		{	
			pstmt = connection.prepareStatement ("SELECT * FROM log WHERE staff_id = ? ;");

			pstmt.setString(1, accountID);
			resultset = pstmt.executeQuery();

			if ( resultset.next() )
			{
				log.setLogID(resultset.getString("log_id"));
				log.setStaffID(resultset.getString("staff_id"));
				log.setAccountID(resultset.getString("account_id"));
				log.setClientID(resultset.getString("client_id"));
				log.setOperatedAccountID(resultset.getString("operated_account_id"));
				log.setOperatedBalance(resultset.getDouble("operated_balance"));
				log.setLogTime(resultset.getDate("log_time"));

				
				String type;
				type = resultset.getString("operation_type");
				log.setOperationType(OperationType.getEnumFromString(type));
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

		return log;
	}
	
	public static void deleteLogTable( Connection connection ) throws SQLException
	{
		String deleteString;
		deleteString = "DELETE FROM log;";

		java.sql.Statement statement;

		statement = connection.createStatement();
		connection.setAutoCommit(false);
		
		statement.executeUpdate(deleteString);
		statement.close();

		connection.commit();
	}
}
