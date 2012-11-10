package database.operation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import enumtype.AccountType;
import enumtype.ClientType;
import enumtype.OperationType;

import system.element.Account;
import system.element.Log;



public class TbLogOperation 
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
	
	public static ArrayList<Log> selectAllLogs (Connection connection )
	{
		ArrayList<Log> logs = new ArrayList();
		
		PreparedStatement pstmt;
		ResultSet resultset;	

		try
		{	
			pstmt = connection.prepareStatement ("SELECT * FROM log;");
			resultset = pstmt.executeQuery();

			while ( resultset.next() )
			{
				Log log = new Log();
				
				log.setLogID(resultset.getString("log_id"));
				log.setStaffID(resultset.getString("staff_id"));
				log.setAccountID(resultset.getString("account_id"));
				log.setClientID(resultset.getString("client_id"));
				log.setOperatedAccountID(resultset.getString("operated_account_id"));
				log.setOperatedBalance(resultset.getDouble("operated_balance"));
				log.setLogTime(resultset.getTimestamp("log_time"));

				
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
	
	public static ArrayList<Log> selectAllLogsInTime( Connection connection, Date startDate, Date endDate )
	{
		ArrayList<Log> logs = new ArrayList();
		
		PreparedStatement pstmt;
		ResultSet resultset;	
		
		try
		{	
			pstmt = connection.prepareStatement ("SELECT * FROM log WHERE log_time >= ? AND log_time <= ?;");

			pstmt.setObject(1, startDate);
			pstmt.setObject(2, endDate);
			
			resultset = pstmt.executeQuery();

			while ( resultset.next() )
			{
				Log log = new Log();
				
				log.setLogID(resultset.getString("log_id"));
				log.setStaffID(resultset.getString("staff_id"));
				log.setAccountID(resultset.getString("account_id"));
				log.setClientID(resultset.getString("client_id"));
				log.setOperatedAccountID(resultset.getString("operated_account_id"));
				log.setOperatedBalance(resultset.getDouble("operated_balance"));
				log.setLogTime(resultset.getTimestamp("log_time"));

				
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
	
	public static ArrayList<Log> selectLogsByOperatorID( Connection connection, String staffID )
	{
		ArrayList<Log> logs = new ArrayList();
		
		PreparedStatement pstmt;
		ResultSet resultset;	

		try
		{	
			//System.out.println(staffID);
			pstmt = connection.prepareStatement ("SELECT * FROM log WHERE staff_id = ? ;");

			pstmt.setString(1, staffID);
			resultset = pstmt.executeQuery();

			while ( resultset.next() )
			{
				Log log = new Log();
				
				log.setLogID(resultset.getString("log_id"));
				log.setStaffID(resultset.getString("staff_id"));
				log.setAccountID(resultset.getString("account_id"));
				log.setClientID(resultset.getString("client_id"));
				log.setOperatedAccountID(resultset.getString("operated_account_id"));
				log.setOperatedBalance(resultset.getDouble("operated_balance"));
				log.setLogTime(resultset.getTimestamp("log_time"));

				
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
	
	public static ArrayList<Log> selectLogsByManagerID( Connection connection, String staffID )
	{
		ArrayList<Log> logs = new ArrayList();
		
		PreparedStatement pstmt;
		ResultSet resultset;	

		try
		{	
			pstmt = connection.prepareStatement ("SELECT * FROM Log l, Staff s WHERE l.staff_id=s.staff_id AND ( l.staff_id=? OR s.superior_id=? )");

			pstmt.setString(1, staffID);
			pstmt.setString(2, staffID);
			
			
			resultset = pstmt.executeQuery();

			while ( resultset.next() )
			{
				Log log = new Log();
				
				log.setLogID(resultset.getString("log_id"));
				log.setStaffID(resultset.getString("staff_id"));
				log.setAccountID(resultset.getString("account_id"));
				log.setClientID(resultset.getString("client_id"));
				log.setOperatedAccountID(resultset.getString("operated_account_id"));
				log.setOperatedBalance(resultset.getDouble("operated_balance"));
				log.setLogTime(resultset.getTimestamp("log_time"));

				
				String type;
				type = resultset.getString("operation_type");
				log.setOperationType(OperationType.getEnumFromString(type));
				
				logs.add(log);
				
				//System.out.println(log);
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
	
	public static ArrayList<Log> selectLogsByDirectorID( Connection connection, String staffID )
	{
		ArrayList<Log> logs = new ArrayList();
		
		PreparedStatement pstmt;
		ResultSet resultset;	

		
		
		try
		{	
			pstmt = connection.prepareStatement ("select * from log l, staff s1, staff s2 where ( l.staff_id = s2.staff_id) AND (s1.superior_id=? OR s1.staff_id=? ) AND (s2.superior_id=s1.staff_id OR (s1.staff_id=? AND s2.staff_id=s1.staff_id));");

			pstmt.setString(1, staffID);
			pstmt.setString(2, staffID);
			pstmt.setString(3, staffID);
			
			resultset = pstmt.executeQuery();

			while ( resultset.next() )
			{
				Log log = new Log();
				
				log.setLogID(resultset.getString("log_id"));
				log.setStaffID(resultset.getString("staff_id"));
				log.setAccountID(resultset.getString("account_id"));
				log.setClientID(resultset.getString("client_id"));
				log.setOperatedAccountID(resultset.getString("operated_account_id"));
				log.setOperatedBalance(resultset.getDouble("operated_balance"));
				log.setLogTime(resultset.getTimestamp("log_time"));

				
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
	
	public static ArrayList<Log> selectLogsByAccountID( Connection connection, String accountID )
	{
		ArrayList<Log> logs = new ArrayList();
		
		PreparedStatement pstmt;
		ResultSet resultset;	

		
		
		try
		{	
			pstmt = connection.prepareStatement ("SELECT * FROM log WHERE account_id = ? || operated_account_id = ? ;");

			pstmt.setString(1, accountID);
			pstmt.setString(2, accountID);
			resultset = pstmt.executeQuery();

			while ( resultset.next() )
			{
				Log log = new Log();
				
				log.setLogID(resultset.getString("log_id"));
				log.setStaffID(resultset.getString("staff_id"));
				log.setAccountID(resultset.getString("account_id"));
				log.setClientID(resultset.getString("client_id"));
				log.setOperatedAccountID(resultset.getString("operated_account_id"));
				log.setOperatedBalance(resultset.getDouble("operated_balance"));
				log.setLogTime(resultset.getTimestamp("log_time"));

				
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
	
	public static ArrayList<Log> selectLogsByAccountIDInTime( Connection connection, String accountID, Date startDate, Date endDate )
	{
		ArrayList<Log> logs = new ArrayList();
		
		PreparedStatement pstmt;
		ResultSet resultset;	
		
		try
		{	
			pstmt = connection.prepareStatement ("SELECT * FROM log WHERE (account_id = ? || operated_account_id = ? )AND log_time >= ? AND log_time <= ?;");

			pstmt.setString(1, accountID);
			pstmt.setString(2, accountID);
			pstmt.setObject(3, startDate);
			pstmt.setObject(4, endDate);
			
			resultset = pstmt.executeQuery();

			while ( resultset.next() )
			{
				Log log = new Log();
				
				log.setLogID(resultset.getString("log_id"));
				log.setStaffID(resultset.getString("staff_id"));
				log.setAccountID(resultset.getString("account_id"));
				log.setClientID(resultset.getString("client_id"));
				log.setOperatedAccountID(resultset.getString("operated_account_id"));
				log.setOperatedBalance(resultset.getDouble("operated_balance"));
				log.setLogTime(resultset.getTimestamp("log_time"));

				
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
	
	/*
	public static ArrayList<Log> selectLogsByStaffIDInTime( Connection connection, String staffID, Date startDate, Date endDate )
	{
		ArrayList<Log> logs = new ArrayList();
		
		PreparedStatement pstmt;
		ResultSet resultset;	
		
		try
		{	
			pstmt = connection.prepareStatement ("SELECT * FROM log WHERE staff_id = ? AND log_time >= ? AND log_time <= ?;");

			pstmt.setString(1, staffID);
			pstmt.setObject(2, startDate);
			pstmt.setObject(3, endDate);
			
			resultset = pstmt.executeQuery();

			while ( resultset.next() )
			{
				Log log = new Log();
				
				log.setLogID(resultset.getString("log_id"));
				log.setStaffID(resultset.getString("staff_id"));
				log.setAccountID(resultset.getString("account_id"));
				log.setClientID(resultset.getString("client_id"));
				log.setOperatedAccountID(resultset.getString("operated_account_id"));
				log.setOperatedBalance(resultset.getDouble("operated_balance"));
				log.setLogTime(resultset.getTimestamp("log_time"));

				
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
	
	public static ArrayList<Log> selectLogsByClientIDInTime( Connection connection, String clientID, Date startDate, Date endDate )
	{
		ArrayList<Log> logs = new ArrayList();
		
		PreparedStatement pstmt;
		ResultSet resultset;	
		
		try
		{	
			pstmt = connection.prepareStatement ("SELECT * FROM log WHERE client_id = ? AND log_time >= ? AND log_time <= ?;");

			pstmt.setString(1, clientID);
			pstmt.setObject(2, startDate);
			pstmt.setObject(3, endDate);
			
			resultset = pstmt.executeQuery();

			while ( resultset.next() )
			{
				Log log = new Log();
				
				log.setLogID(resultset.getString("log_id"));
				log.setStaffID(resultset.getString("staff_id"));
				log.setAccountID(resultset.getString("account_id"));
				log.setClientID(resultset.getString("client_id"));
				log.setOperatedAccountID(resultset.getString("operated_account_id"));
				log.setOperatedBalance(resultset.getDouble("operated_balance"));
				log.setLogTime(resultset.getTimestamp("log_time"));

				
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
	

	
	public static ArrayList<Log> selectLogsBySuperiorID( Connection connection, String superiorID )
	{
		ArrayList<Log> logs = new ArrayList();
		
		PreparedStatement pstmt;
		ResultSet resultset;	

		
		
		try
		{	
			pstmt = connection.prepareStatement ("SELECT * FROM log l, staff s WHERE s.superior_id = ? AND l.staff_id = s.staff_id;");

			pstmt.setString(1, superiorID);
			resultset = pstmt.executeQuery();

			while ( resultset.next() )
			{
				Log log = new Log();
				
				log.setLogID(resultset.getString("log_id"));
				log.setStaffID(resultset.getString("staff_id"));
				log.setAccountID(resultset.getString("account_id"));
				log.setClientID(resultset.getString("client_id"));
				log.setOperatedAccountID(resultset.getString("operated_account_id"));
				log.setOperatedBalance(resultset.getDouble("operated_balance"));
				log.setLogTime(resultset.getTimestamp("log_time"));

				
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
	
	public static ArrayList<Log> selectLogsBySuperiorIDInTime( Connection connection, String superiorID, Date startDate, Date endDate )
	{
		ArrayList<Log> logs = new ArrayList();
		
		PreparedStatement pstmt;
		ResultSet resultset;	
		
		try
		{	
			pstmt = connection.prepareStatement ("SELECT * FROM log l, staff s WHERE s.superior_id = ? AND l.log_time >= ? AND l.log_time <= ? AND l.staff_id = s.staff_id;");

			pstmt.setString(1, superiorID);
			pstmt.setObject(2, startDate);
			pstmt.setObject(3, endDate);
			
			resultset = pstmt.executeQuery();

			while ( resultset.next() )
			{
				Log log = new Log();
				
				log.setLogID(resultset.getString("log_id"));
				log.setStaffID(resultset.getString("staff_id"));
				log.setAccountID(resultset.getString("account_id"));
				log.setClientID(resultset.getString("client_id"));
				log.setOperatedAccountID(resultset.getString("operated_account_id"));
				log.setOperatedBalance(resultset.getDouble("operated_balance"));
				log.setLogTime(resultset.getTimestamp("log_time"));

				
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
	*/
	
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
