package test.dboperation;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import system.element.Log;

import database.connection.JDBCConnection;
import database.operation.TbLogOperation;
import enumtype.OperationType;



public class TbLogTest 
{
	public static void insertLogTest() throws ClassNotFoundException, SQLException, ParseException
	{
		Connection connection;
		
		connection = JDBCConnection.getCommonConnection();
		
		TbLogOperation.deleteLogTable(connection);
		
		Log log1 = new Log();
		Date date1 = new Date();
		log1.setLogID("L0001");
		log1.setAccountID("A0001");
		log1.setClientID("C0001");
		log1.setStaffID("S0004");
		log1.setOperationType(OperationType.create);
		log1.setLogTime(date1);
		TbLogOperation.createLog(connection, log1);
		
		
		Log log2 = new Log();
		Date date2 = new Date();
		log2.setLogID("L0002");
		log2.setAccountID("A0002");
		log2.setClientID("C0002");
		log2.setStaffID("S0004");
		log2.setOperationType(OperationType.deposit);
		log2.setOperatedBalance(100);
		log2.setLogTime(date2);
		TbLogOperation.createLog(connection, log2);
		
		Log log3 = new Log();
		Date date3 = new Date();
		log3.setLogID("L0003");
		log3.setAccountID("A0003");
		log3.setClientID("C0003");
		log3.setStaffID("S0005");
		log3.setOperationType(OperationType.withdraw);
		log3.setOperatedBalance(500);
		log3.setLogTime(date3);
		TbLogOperation.createLog(connection, log3);
		
		Log log4 = new Log();
		Date date4 = new Date();
		log4.setLogID("L0004");
		log4.setAccountID("A0002");
		log4.setClientID("C0002");
		log4.setStaffID("S0005");
		log4.setOperationType(OperationType.transfer);
		log4.setOperatedBalance(1000);
		log4.setOperatedAccountID("A0003");
		log4.setLogTime(date4);
		TbLogOperation.createLog(connection, log4);
		
		Log log5 = new Log();
		Date date5 = new Date();
		log5.setLogID("L0005");
		log5.setAccountID("A0002");
		log5.setClientID("C0002");
		log5.setStaffID("S0007");
		log5.setOperationType(OperationType.chpassword);
		log5.setLogTime(date5);
		TbLogOperation.createLog(connection, log5);
		
		SimpleDateFormat simFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss"); 
		
		Date date6 = simFormat.parse("2012.10.28 11:11:11");
		log1.setLogTime(date6);
		TbLogOperation.createLog(connection, log1);
		
		
		Date date7 = simFormat.parse("2012.10.28 12:12:12");
		log2.setLogTime(date7);
		TbLogOperation.createLog(connection, log2);
		
		Date date8 = simFormat.parse("2012.11.1 11:11:11");
		log3.setLogTime(date8);
		TbLogOperation.createLog(connection, log3);
		
		Date date9 = simFormat.parse("2012.11.2 11:11:11");
		log4.setLogTime(date9);
		TbLogOperation.createLog(connection, log4);
		
		Date date10 = simFormat.parse("2012.11.3 11:11:11");
		log5.setLogTime(date10);
		TbLogOperation.createLog(connection, log5);
		
		
		connection.close();
	}
	
	public static void selectLogsByStaffIDTest() throws ClassNotFoundException, SQLException
	{
		Connection connection = JDBCConnection.getCommonConnection();
		
		ArrayList<Log> logs = new ArrayList<Log>();
		
		logs = TbLogOperation.selectLogsByOperatorID(connection, "S0002");
		
		for ( Log log: logs)
		{
			printLog(log);
		}
		
		connection.close();
	}
	
	public static void selectLogsByAccountIDTest() throws ClassNotFoundException, SQLException
	{
		Connection connection = JDBCConnection.getCommonConnection();
		
		ArrayList<Log> logs = new ArrayList<Log>();
		
		logs = TbLogOperation.selectLogsByAccountID(connection, "A0002");
		
		for ( Log log: logs)
		{
			printLog(log);
		}
		
		connection.close();
	}
	
	public static void selectLogsByStaffIDInTimeTest() throws ClassNotFoundException, SQLException, ParseException
	{
		Connection connection = JDBCConnection.getCommonConnection();
		
		ArrayList<Log> logs = new ArrayList<Log>();
		
		SimpleDateFormat simFormat = new SimpleDateFormat("yyyy.MM.dd"); 
		
		Date date1 = simFormat.parse("2012.10.1");
		Date date2 = simFormat.parse("2012.10.30");
		
		logs = TbLogOperation.selectLogsByStaffIDInTime(connection, "S0002",date1,date2);
		
		for ( Log log: logs)
		{
			printLog(log);
		}
		
		connection.close();
	}
	
	public static void selectLogsByAccountIDInTimeTest() throws ClassNotFoundException, SQLException, ParseException
	{
		Connection connection = JDBCConnection.getCommonConnection();
		
		ArrayList<Log> logs = new ArrayList<Log>();
		
		SimpleDateFormat simFormat = new SimpleDateFormat("yyyy.MM.dd"); 
		
		Date date1 = simFormat.parse("2012.10.1");
		Date date2 = simFormat.parse("2012.10.30");
		
		logs = TbLogOperation.selectLogsByAccountIDInTime(connection, "A0002",date1,date2);
		
		for ( Log log: logs)
		{
			printLog(log);
		}
		
		connection.close();
	}
	
	
	public static void selectLogsByClientIDInTimeTest() throws ClassNotFoundException, SQLException, ParseException
	{
		Connection connection = JDBCConnection.getCommonConnection();
		
		ArrayList<Log> logs = new ArrayList<Log>();
		
		SimpleDateFormat simFormat = new SimpleDateFormat("yyyy.MM.dd"); 
		
		Date date1 = simFormat.parse("2012.10.1");
		Date date2 = simFormat.parse("2012.10.30");
		
		logs = TbLogOperation.selectLogsByClientIDInTime(connection, "C0002",date1,date2);
		
		for ( Log log: logs)
		{
			printLog(log);
		}
		
		connection.close();
	}
	
	public static void selectLogsInTimeTest() throws ClassNotFoundException, SQLException, ParseException
	{
		Connection connection = JDBCConnection.getCommonConnection();
		
		ArrayList<Log> logs = new ArrayList<Log>();
		
		SimpleDateFormat simFormat = new SimpleDateFormat("yyyy.MM.dd"); 
		
		Date date1 = simFormat.parse("2012.10.1");
		Date date2 = simFormat.parse("2012.10.30");
		
		logs = TbLogOperation.selectLogsInTime(connection, date1,date2);
		
		for ( Log log: logs)
		{
			printLog(log);
		}
		
		connection.close();
	}
	
	public static void selectLogsBySuperiorIDTest() throws ClassNotFoundException, SQLException, ParseException
	{
		Connection connection = JDBCConnection.getCommonConnection();
		
		ArrayList<Log> logs = new ArrayList<Log>();
		
		logs = TbLogOperation.selectLogsBySuperiorID(connection, "S0002");
		
		for ( Log log: logs)
		{
			printLog(log);
		}
		
		connection.close();
	}
	
	public static void selectLogsBySuperiorIDInTimeTest() throws ClassNotFoundException, SQLException, ParseException
	{
		Connection connection = JDBCConnection.getCommonConnection();
		
		ArrayList<Log> logs = new ArrayList<Log>();
		
		SimpleDateFormat simFormat = new SimpleDateFormat("yyyy.MM.dd"); 
		
		Date date1 = simFormat.parse("2012.10.1");
		Date date2 = simFormat.parse("2012.10.30");
		
		logs = TbLogOperation.selectLogsBySuperiorIDInTime(connection, "S0002",date1,date2);
		
		for ( Log log: logs)
		{
			printLog(log);
		}
		
		connection.close();
	}
	
	
	public static void printLog( Log log )
	{
		System.out.print(log.getLogID() + " ");
		System.out.print(log.getAccountID() + " ");
		System.out.print(log.getClientID() + " ");
		System.out.print(log.getStaffID() + " ");
		System.out.print(log.getOperationType() + " ");
		System.out.print(log.getOperatedAccountID() + " ");
		System.out.print(log.getOperatedBalance() + " ");
		System.out.print(log.getLogTime() + " ");
		System.out.println();
		
	}

}
