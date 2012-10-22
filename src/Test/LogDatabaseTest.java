package Test;

import java.sql.Connection;
import java.sql.SQLException;

import BaseElement.Log;
import DatabaseOperation.JDBCConnection;

public class LogDatabaseTest 
{
	public static void insertLogTest() throws ClassNotFoundException, SQLException
	{
		Connection connection;
		
		connection = JDBCConnection.getCommonConnection();
		
		Log log1 = new Log();
		log1.setLogID("L0001");
		log1.setAccountID("A0001");
		log1.setClientID("C0001");
		log1.setStaffID("S0001");
		
		connection.close();
	}

}
