package database.operation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

import enumtype.OperationType;
import enumtype.Position;

import system.element.definition.Log;
import system.element.definition.Staff;



public class TbStaffOperation 
{
	public static void newStaff( Connection connection, Staff staff )
	{
		try
		{	
			PreparedStatement pstmt;
			pstmt = connection.prepareStatement ("INSERT INTO staff ( staff_id, position, superior_id ) values ( ?, ?, ? )");
			
			pstmt.setString(1, staff.getStaffID());
			pstmt.setString(2, staff.getPosition().toString());
			pstmt.setString(3, staff.getSuperiorID());
			
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
	
	public static ArrayList<Staff> selectStaffsByPosition( Connection connection, Position position )
	{
		ArrayList<Staff> staffs = new ArrayList<Staff>();
		
		PreparedStatement pstmt;
		ResultSet resultset; 	

		try
		{	
			pstmt = connection.prepareStatement ("SELECT * FROM staff WHERE position = ? ;");

			pstmt.setString(1, position.toString());
			resultset = pstmt.executeQuery();

			while ( resultset.next() )
			{
				Staff staff = new Staff();
				
				staff.setStaffID(resultset.getString("staff_id"));
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
	
	public static ArrayList<Staff> selectStaffsBySuperiorID( Connection connection, String superiorID )
	{
		ArrayList<Staff> staffs = new ArrayList<Staff>();
		
		PreparedStatement pstmt;
		ResultSet resultset; 	
		
		

		try
		{	
			pstmt = connection.prepareStatement ("SELECT * FROM staff WHERE superior_id = ? ;");

			pstmt.setString(1, superiorID);
			resultset = pstmt.executeQuery();

			while ( resultset.next() )
			{
				Staff staff = new Staff();
				
				staff.setStaffID(resultset.getString("staff_id"));
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
	
	public static Staff selectStaffByStaffID( Connection connection, String staffID )
	{
		PreparedStatement pstmt;
		ResultSet resultset; 	
		
		Staff staff = new Staff();

		try
		{	
			pstmt = connection.prepareStatement ("SELECT * FROM staff WHERE staff_id = ? ;");

			pstmt.setString(1, staffID);
			resultset = pstmt.executeQuery();

			while ( resultset.next() )
			{
				staff.setStaffID(resultset.getString("staff_id"));
				staff.setSuperiorID(resultset.getString("superior_id"));
				
				String type;
				type = resultset.getString("position");
				staff.setPosition(Position.getEnumFromString(type));
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
		
		
		return staff;
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
}
