package Main;

import java.sql.SQLException;
import java.text.ParseException;

import Test.TbAccountTest;
import Test.TbLogTest;
import Test.TbStaffTest;

public class Main 
{
	public static void main( String args[] ) throws ClassNotFoundException, SQLException, ParseException
	{
		/*
		TbAccountTest.createAccountTest();
		TbAccountTest.transferAccountTest();
		TbAccountTest.updateAccountTest();
		*/
		
		/*
		TbStaffTest.newStaffTets();
		TbStaffTest.selectStaffByStaffIDTest();
		TbStaffTest.selectStaffsByPositionTest();
		TbStaffTest.selectStaffsBySuperioIDTest();
		*/
		
		TbLogTest.insertLogTest();
		//TbLogTest.selectLogsByStaffIDTest();
		//TbLogTest.selectLogsByAccountIDTest();
		//TbLogTest.selectLogsByStaffIDInTimeTest();
		//TbLogTest.selectLogsByAccountIDInTimeTest();
		//TbLogTest.selectLogsByClientIDInTimeTest();
		//TbLogTest.selectLogsInTimeTest();
		TbLogTest.selectLogsBySuperiorIDInTimeTest();
	}
}
