package system.manager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import database.connection.JDBCConnection;
import database.operation.TbAccountOperation;
import database.operation.TbStaffOperation;
import enumtype.Position;
import exception.dboperation.AccountDBOperationException;
import exception.dboperation.StaffDBOperationException;
import exception.elmanager.StaffManagerException;
import system.element.Staff;

public class StaffManager 
{
	protected static HashSet<String>  staffIDSet = new HashSet<String>();
	private static long staffNumForTest = 0;
	
	public static void initial() throws ClassNotFoundException, StaffDBOperationException
	{
		staffIDSet.clear();
		staffNumForTest = 0;
		
		Connection connection = JDBCConnection.getCommonConnection();
		
		try 
		{
			staffIDSet = TbStaffOperation.loadStaffID(connection);
		}
		catch (StaffDBOperationException e) 
		{
			System.out.println(e.getMessage());
		}
	}
	
	protected boolean staffIDExist( String accountID )
	{
		return staffIDSet.contains(accountID);
	}
	
	protected String staffIDGenerator()
	{
		String staffID = new String();
		long randomNum;
		
		Random random =new Random(); 
		
		do
		{
			/*
			 * This apart for true System
			randomNum = Math.abs(random.nextInt()) % 999999;
			staffID = String.format("A" + "%06d", randomNum);
			*/
			
			//This part for test
			staffNumForTest++;
			staffID = String.format("S" + "%03d", staffNumForTest);
			
			//System.out.println(staffID);
			//System.out.println(staffIDSet.contains(staffID));
			
		}while ( staffIDSet.contains(staffID) );
		
		staffIDSet.add(staffID);
			
		return staffID;
	}
	
	public static void deleteAllStaffForTest()
	{
		
		try 
		{
			Connection connection = JDBCConnection.getCommonConnection();
			TbStaffOperation.deleteStaffTable(connection);
		} 
		catch (ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void addAdminStaffForTest()
	{
		Staff staff = new Staff();
		staff.setStaffID("S001");
		staff.setPassword("123");
		staff.setPosition(Position.admin);
		staff.setSuperiorID("S000");
		
		try 
		{
			Connection connection = JDBCConnection.getCommonConnection();
			TbStaffOperation.newStaff(connection, staff);
		} 
		catch (ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Staff selectStaffForTest( String staffID ) throws StaffDBOperationException
	{
		Staff supStaff = new Staff();
		
		try 
		{
			Connection connection = JDBCConnection.getCommonConnection();
			supStaff = TbStaffOperation.selectStaffByStaffID(connection, staffID);
		}
		catch (ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return supStaff;
			
	}
	
	public void newStaff( Staff upStaff, Staff lowStaff ) throws StaffDBOperationException, StaffManagerException
	{
		Staff supStaff = new Staff();
		
		try 
		{
			Connection connection = JDBCConnection.getCommonConnection();
			supStaff = TbStaffOperation.selectStaffByStaffID(connection, upStaff.getStaffID());
			
			if ( !supStaff.getPassword().equals(upStaff.getPassword()) )
			{
				throw new StaffManagerException("Staff " + upStaff.getStaffID() + " Password Not Match");
			}
			if ( supStaff.getPosition().equals(Position.operator) )
			{
				throw new StaffManagerException("Operator Staff Can't New Other Staff");
			}
			
			lowStaff.setStaffID(staffIDGenerator());
			lowStaff.setSuperiorID(supStaff.getStaffID());
			
			if ( supStaff.getPosition().equals(Position.manager) )
				lowStaff.setPosition(Position.operator);
			else if ( supStaff.getPosition().equals(Position.director) )
				lowStaff.setPosition(Position.manager);
			else if ( supStaff.getPosition().equals(Position.admin) )
				lowStaff.setPosition(Position.director);
			
			TbStaffOperation.newStaff(connection, lowStaff);
		} 
		catch (ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public Staff selectStaffByID( String staffID ) throws StaffDBOperationException
	{
		Staff supStaff = new Staff();
		
		try 
		{
			Connection connection = JDBCConnection.getCommonConnection();
			supStaff = TbStaffOperation.selectStaffByStaffID(connection, staffID);
		}
		catch (ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return supStaff;
			
	}
	
	public void deleteStaff(Staff upStaff, Staff lowStaff ) throws StaffDBOperationException, StaffManagerException
	{
		Staff supStaff = new Staff();
		
		try 
		{
			Connection connection = JDBCConnection.getCommonConnection();
			supStaff = TbStaffOperation.selectStaffByStaffID(connection, upStaff.getStaffID());
			
			if ( !supStaff.getPassword().equals(upStaff.getPassword()) )
			{
				throw new StaffManagerException("Staff " + upStaff.getStaffID() + " Password Not Match");
			}
			if ( !supStaff.getStaffID().equals(lowStaff.getSuperiorID()) )
			{
				throw new StaffManagerException(upStaff.getStaffID() + " Is Not Superior of " + lowStaff.getStaffID());
			}
			
			TbStaffOperation.deleteStaffByStaffID(connection, lowStaff.getStaffID());
		} 
		catch (ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public ArrayList<Staff> getSubordinateList( Staff staff ) throws StaffDBOperationException, StaffManagerException
	{
		Staff supStaff = new Staff();
		ArrayList<Staff> staffs = new ArrayList<Staff>();
		staffs.clear();
		
		try 
		{
			Connection connection = JDBCConnection.getCommonConnection();
			supStaff = TbStaffOperation.selectStaffByStaffID(connection, staff.getStaffID());
			
			if ( !supStaff.getPassword().equals(staff.getPassword()) )
			{
				throw new StaffManagerException("Staff " + staff.getStaffID() + " Password Not Match");
			}
			
			staffs = TbStaffOperation.selectStaffsBySuperiorID(connection, staff.getStaffID());
		} 
		catch (ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return staffs;
	}
	
	public boolean checkManagerSubordinate( String managerID, String staffID )
	{
		ArrayList<Staff> staffs = new ArrayList<Staff>();
		
		Connection connection;
		try 
		{
			connection = JDBCConnection.getCommonConnection();
			staffs = TbStaffOperation.selectSubordinateByManager(connection, managerID);
			
			for ( Staff staff : staffs )
			{
				//System.out.println(staff.getStaffID());
				if ( staff.getStaffID().equals(staffID) )
					return true;
			}
		} 
		catch (ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return false;
	}
	
	public boolean checkDirectorSubordinate( String directorID, String staffID )
	{
		ArrayList<Staff> staffs = new ArrayList<Staff>();
		
		Connection connection;
		try 
		{
			connection = JDBCConnection.getCommonConnection();
			staffs = TbStaffOperation.selectSubordinateByDirectorID(connection, directorID);
			
			for ( Staff staff : staffs )
			{
				//System.out.println(staff.getStaffID());
				if ( staff.getStaffID().equals(staffID) )
					return true;
			}
		} 
		catch (ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return false;
	}
}
