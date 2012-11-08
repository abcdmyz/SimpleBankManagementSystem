package system.manager;

import java.sql.Connection;
import java.util.Random;

import database.connection.JDBCConnection;
import database.operation.TbLogOperation;
import database.operation.TbStaffOperation;
import exception.dboperation.StaffDBOperationException;
import system.element.Log;

public class LogManager 
{
	private static long logIDNumForTest = 0;
	
	public static void initial() throws ClassNotFoundException, StaffDBOperationException
	{
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
		logID = String.format("S" + "%06d", logIDNumForTest);
			
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

}
