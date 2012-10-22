package DatabaseOperation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import BaseElement.Log;
import BaseElement.Staff;
import Configuration.Position;

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
		
		return staffs;
	}
	
	public static ArrayList<Staff> selectStaffByStaffID( Connection connection, String staffID )
	{
		ArrayList<Staff> staffs = new ArrayList<Staff>();
		
		return staffs;
	}
}
