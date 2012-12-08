package database.operation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

import enumtype.OperationType;
import enumtype.Position;
import exception.dboperation.AccountDBOperationException;
import exception.dboperation.StaffDBOperationException;

import system.element.Log;
import system.element.Staff;



public class TbStaffOperation 
{
	public static void newStaff( Connection connection, Staff staff )
	{
		try
		{	
			PreparedStatement pstmt;
			pstmt = connection.prepareStatement ("INSERT INTO staff ( staff_id, password, position, superior_id ) values ( ?, ?, ?, ? )");
			
			pstmt.setString(1, staff.getStaffID());
			pstmt.setString(2, staff.getPassword());
			pstmt.setString(3, staff.getPosition().toString());
			pstmt.setString(4, staff.getSuperiorID());
			
			//System.out.println("add " + staff.getStaffID());
			
			
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
	
	public static ArrayList<Staff> selectSubordinateByDirectorID( Connection connection, String staffID )
	{
		ArrayList<Staff> staffs = new ArrayList();
		
		PreparedStatement pstmt;
		ResultSet resultset;	

		try
		{	
			pstmt = connection.prepareStatement ("select s2.staff_id, s2.position, s2.superior_id, s2.password from staff s1, staff s2 where (s1.superior_id=? OR s1.staff_id=? ) AND (s2.superior_id=s1.staff_id OR (s1.staff_id=? AND s2.staff_id=s1.staff_id));");

			pstmt.setString(1, staffID);
			pstmt.setString(2, staffID);
			pstmt.setString(3, staffID);
			
			resultset = pstmt.executeQuery();

			while ( resultset.next() )
			{
				Staff staff = new Staff();
				
				staff.setStaffID(resultset.getString("staff_id"));
				staff.setPassword(resultset.getString("password"));
				staff.setSuperiorID(resultset.getString("superior_id"));
				
				String type;
				type = resultset.getString("position");
				staff.setPosition(Position.getEnumFromString(type));
				
				staffs.add(staff);
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
		

		return staffs;
	}
	
	public static ArrayList<Staff> selectSubordinateByManager( Connection connection, String staffID )
	{
		ArrayList<Staff> staffs = new ArrayList();
		
		PreparedStatement pstmt;
		ResultSet resultset;	

		try
		{	
			pstmt = connection.prepareStatement ("select * from staff WHERE superior_id=? OR staff_id=? ");

			pstmt.setString(1, staffID);
			pstmt.setString(2, staffID);
			
			resultset = pstmt.executeQuery();

			while ( resultset.next() )
			{
				Staff staff = new Staff();
				
				staff.setStaffID(resultset.getString("staff_id"));
				staff.setPassword(resultset.getString("password"));
				staff.setSuperiorID(resultset.getString("superior_id"));
				
				String type;
				type = resultset.getString("position");
				staff.setPosition(Position.getEnumFromString(type));
				staffs.add(staff);
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
		

		return staffs;
	}
	
	public static ArrayList<Staff> selectStaffsByPosition( Connection connection, Position position )
	{
		ArrayList<Staff> staffs = new ArrayList<Staff>();
		
		PreparedStatement pstmt;
		ResultSet resultset; 	

		try
		{	
			pstmt = connection.prepareStatement ("SELECT * FROM staff WHERE position = ? ;");

			pstmt.setString(1, position.toString());
			
			connection.setAutoCommit(false);
			resultset = pstmt.executeQuery();

			while ( resultset.next() )
			{
				Staff staff = new Staff();
				
				staff.setStaffID(resultset.getString("staff_id"));
				staff.setPassword(resultset.getString("password"));
				staff.setSuperiorID(resultset.getString("superior_id"));
				
				String type;
				type = resultset.getString("position");
				staff.setPosition(Position.getEnumFromString(type));
				
				staffs.add(staff);
			}
			
			
			pstmt.close();
			resultset.close();
			connection.commit();
		}

		catch (SQLException ex)
		{
			System.err.println(ex.getMessage());
		}
		
		return staffs;
	}
	
	public static ArrayList<Staff> selectStaffsBySuperiorID( Connection connection, String superiorID ) throws StaffDBOperationException
	{
		ArrayList<Staff> staffs = new ArrayList<Staff>();
		
		PreparedStatement pstmt;
		ResultSet resultset; 	
		
		staffs.clear();

		try
		{	
			pstmt = connection.prepareStatement ("SELECT * FROM staff WHERE superior_id = ? ;");

			pstmt.setString(1, superiorID);
			
			connection.setAutoCommit(false);
			resultset = pstmt.executeQuery();

			while ( resultset.next() )
			{
				Staff staff = new Staff();
				
				staff.setStaffID(resultset.getString("staff_id"));
				staff.setPassword(resultset.getString("password"));
				staff.setSuperiorID(resultset.getString("superior_id"));
				
				String type;
				type = resultset.getString("position");
				staff.setPosition(Position.getEnumFromString(type));
				
				staffs.add(staff);
			}			
			
			pstmt.close();
			resultset.close();
			connection.commit();
			
			if ( staffs.isEmpty() )
			{
				throw new StaffDBOperationException("Staff " + superiorID + " Don't Exist");
			}
		}

		catch (SQLException ex)
		{
			System.err.println(ex.getMessage());
		}
		
		return staffs;
	}
	
	public static Staff selectStaffByStaffID( Connection connection, String staffID ) throws StaffDBOperationException
	{
		PreparedStatement pstmt;
		ResultSet resultset; 	
		
		Staff staff = new Staff();

		try
		{	
			pstmt = connection.prepareStatement ("SELECT * FROM staff WHERE staff_id = ? ;");

			pstmt.setString(1, staffID);
			
			connection.setAutoCommit(false);
			resultset = pstmt.executeQuery();
			
			if ( resultset.next() )
			{
				staff.setStaffID(resultset.getString("staff_id"));
				staff.setPassword(resultset.getString("password"));
				staff.setSuperiorID(resultset.getString("superior_id"));
				
				String type;
				type = resultset.getString("position");
				staff.setPosition(Position.getEnumFromString(type));
			}
			else
			{
				pstmt.close();
				resultset.close();
				connection.commit();
				
				throw new StaffDBOperationException("Staff " + staffID + " Don't Exist");
			}
			
			
			pstmt.close();
			resultset.close();
			connection.commit();
		}

		catch (SQLException ex)
		{
			System.err.println(ex.getMessage());
		}
		
		
		return staff;
	}
	
	public static ArrayList<Staff> selectAllStaffs( Connection connection ) throws StaffDBOperationException
	{
		PreparedStatement pstmt;
		ResultSet resultset; 	
		ArrayList<Staff> staffs = new ArrayList<Staff>();
		staffs.clear();

		try
		{	
			pstmt = connection.prepareStatement ("SELECT * FROM staff;");
			
			connection.setAutoCommit(false);
			resultset = pstmt.executeQuery();
			
			while ( resultset.next() )
			{
				Staff staff = new Staff();
				
				staff.setStaffID(resultset.getString("staff_id"));
				staff.setPassword(resultset.getString("password"));
				staff.setSuperiorID(resultset.getString("superior_id"));
				
				String type;
				type = resultset.getString("position");
				staff.setPosition(Position.getEnumFromString(type));
				
				staffs.add(staff);
				
				//System.out.println("Hello");
			}
			
			
			pstmt.close();
			resultset.close();
			connection.commit();
		}

		catch (SQLException ex)
		{
			System.err.println(ex.getMessage());
		}
		
		return staffs;
	}
	
	public static void updateStaffPassword( Connection connection, String staffID, String newPassword )
	{
		PreparedStatement pstmt;
		ResultSet resultset; 	
		
		Staff staff = new Staff();

		try
		{	
			pstmt = connection.prepareStatement ("UPDATE staff SET password =? WHERE staff_id = ? ;");

			pstmt.setString(1, newPassword);
			pstmt.setString(2, staffID);
			
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
	
	public static void deleteStaffTable( Connection connection ) throws SQLException
	{
		String deleteString;
		deleteString = "DELETE FROM staff;";

		java.sql.Statement statement;

		statement = connection.createStatement();
		
		connection.setAutoCommit(false);
		
		statement.executeUpdate(deleteString);
		statement.close();

		connection.commit();
	}
	
	public static void deleteStaffByStaffID( Connection connection, String staffID ) throws SQLException
	{
		PreparedStatement pstmt;
		ResultSet resultset; 	
		
		Staff staff = new Staff();

		try
		{	
			pstmt = connection.prepareStatement ("DELETE FROM staff  WHERE staff_id = ? ;");

			pstmt.setString(1, staffID);
			
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
	
	public static HashSet<String> loadStaffID( Connection connection ) throws StaffDBOperationException 
	{
		HashSet<String> staffSet = new HashSet<String>();
		staffSet.clear();
		
		PreparedStatement pstmt;
		ResultSet resultset;	
		String staffID;

		try
		{	
			pstmt = connection.prepareStatement ("SELECT * FROM staff;");
			
			connection.setAutoCommit(false);
			resultset = pstmt.executeQuery();

			while ( resultset.next() )
			{
				staffID = resultset.getString("staff_id");
				staffSet.add(staffID);
			}
			
			pstmt.close();
			resultset.close();
			connection.commit();
		}

		catch (SQLException ex)
		{
			System.err.println(ex.getMessage());
			
			throw new StaffDBOperationException("Load StaffID Fail");		
		}
		
		return staffSet;
		
	}
}
