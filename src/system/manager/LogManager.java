package system.manager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import database.connection.JDBCConnection;
import database.operation.TbLogOperation;
import database.operation.TbStaffOperation;
import exception.dboperation.StaffDBOperationException;
import system.element.Account;
import system.element.Log;
import system.element.LogList;

public class LogManager 
{
	private static long logIDNumForTest = 0;
	
	public static void initial() throws ClassNotFoundException, StaffDBOperationException
	{
		deleteAllLogForTest();
		logIDNumForTest = 0;
		
		Connection connection = JDBCConnection.getCommonConnection();
	}
	
	protected String staffIDGenerator()
	{
		String logID = new String();
		long randomNum;
		
		Random random =new Random(); 
		
		
			/*
			 * This apart for true System
			randomNum = Math.abs(random.nextInt()) % 999999;
			staffID = String.format("A" + "%06d", randomNum);
			*/
			
			//This part for test
		logIDNumForTest++;
		logID = String.format("L" + "%06d", logIDNumForTest);
			
			//System.out.println(staffID);
			//System.out.println(staffIDSet.contains(staffID));
			
		return logID;
	}
	
	
	public void addLog( Log log )
	{
		log.setLogID(staffIDGenerator());
		
		try 
		{
			Connection connection = JDBCConnection.getCommonConnection();
			TbLogOperation.createLog(connection, log);
		} 
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public static LogList checkAllLogForTest( Account account )
	{
		ArrayList<Log> logs = new ArrayList<Log>();
		LogList logList = new LogList();
		
		Connection connection;
		try 
		{
			connection = JDBCConnection.getCommonConnection();
			logs = TbLogOperation.selectAllLogs(connection);
			
			logList.setLogList(logs);
		} 
		catch (ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return logList;
	}
	
	public static void deleteAllLogForTest()
	{		
		Connection connection;
		try 
		{
			connection = JDBCConnection.getCommonConnection();
			TbLogOperation.deleteLogTable(connection);
		} 
		catch (ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	public LogList checkAccountLog( Account account )
	{
		ArrayList<Log> logs = new ArrayList<Log>();
		LogList logList = new LogList();
		
		Connection connection;
		try 
		{
			connection = JDBCConnection.getCommonConnection();
			logs = TbLogOperation.selectLogsByAccountID(connection, account.getAccountID());
			
			logList.setLogList(logs);
		} 
		catch (ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return logList;
	}
	
	public LogList checkAccountLogInTime( Account account, Date startTime, Date endTime )
	{
		ArrayList<Log> logs = new ArrayList<Log>();
		LogList logList = new LogList();
		
		Connection connection;
		try 
		{
			connection = JDBCConnection.getCommonConnection();
			logs = TbLogOperation.selectLogsByAccountIDInTime(connection, account.getAccountID(), startTime, endTime);
			
			logList.setLogList(logs);
		} 
		catch (ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return logList;
	}

}
