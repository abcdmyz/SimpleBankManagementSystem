package test.dboperation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

import system.element.Staff;

import database.connection.JDBCConnection;
import database.operation.TbStaffOperation;
import enumtype.Position;



public class TbStaffTest 
{
	public static void newStaffTets() throws ClassNotFoundException, SQLException
	{
		Connection connection = JDBCConnection.getCommonConnection();
		
		TbStaffOperation.deleteStaffTable(connection);
		
		
		Staff staff1 = new Staff("S0001", Position.director);
		TbStaffOperation.newStaff(connection, staff1);
		
		Staff staff2 = new Staff("S0002", Position.manager, "S0001");
		TbStaffOperation.newStaff(connection, staff2);
		
		Staff staff3 = new Staff("S0003", Position.manager, "S0001");
		TbStaffOperation.newStaff(connection, staff3);
		
		Staff staff4 = new Staff("S0004", Position.operator,"S0002");
		TbStaffOperation.newStaff(connection, staff4);
		
		Staff staff5 = new Staff("S0005", Position.operator, "S0002");
		TbStaffOperation.newStaff(connection, staff5);
		
		Staff staff6 = new Staff("S0006", Position.operator, "S0002");
		TbStaffOperation.newStaff(connection, staff6);
		
		Staff staff7 = new Staff("S0007", Position.operator,"S0003");
		TbStaffOperation.newStaff(connection, staff7);
		
		Staff staff8 = new Staff("S0008", Position.operator, "S0003");
		TbStaffOperation.newStaff(connection, staff8);
		
		Staff staff9 = new Staff("S0009", Position.operator, "S0003");
		TbStaffOperation.newStaff(connection, staff9);
		
		connection.close();
		
	}
	
	public static void selectStaffByStaffIDTest() throws ClassNotFoundException, SQLException
	{
		Connection connection = JDBCConnection.getCommonConnection();
		
		Staff staff = new Staff();
		
		staff = TbStaffOperation.selectStaffByStaffID(connection, "S0001");
		printStaff(staff);
		
		staff = TbStaffOperation.selectStaffByStaffID(connection, "S0005");
		printStaff(staff);
		
		connection.close();
	}
	
	public static void selectStaffsByPositionTest() throws ClassNotFoundException, SQLException
	{
		ArrayList<Staff> staffs = new ArrayList<Staff>();
		
		Connection connection = JDBCConnection.getCommonConnection();
		
		staffs = TbStaffOperation.selectStaffsByPosition(connection, Position.operator);
		
		for ( Staff st: staffs )
		{
			printStaff(st);
		}
		
		connection.close();
	}
	
	public static void selectStaffsBySuperioIDTest() throws ClassNotFoundException, SQLException
	{
		ArrayList<Staff> staffs = new ArrayList<Staff>();
		
		Connection connection = JDBCConnection.getCommonConnection();
		
		staffs = TbStaffOperation.selectStaffsBySuperiorID(connection, "S0002");
		
		for ( Staff st: staffs )
		{
			printStaff(st);
		}
		
		connection.close();
	}
	
	public static void printStaff( Staff staff )
	{
		System.out.println(staff.getStaffID() + " " + staff.getPosition().toString() + " " + staff.getSuperiorID());
	}

}
