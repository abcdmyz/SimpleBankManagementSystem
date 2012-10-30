package Main;

import java.sql.SQLException;
import java.text.ParseException;

import exception.dboperation.AccountDBOperationException;

import system.element.manager.AccountManager;
import test.dboperation.TbAccountTest;
import test.dboperation.TbLogTest;
import test.dboperation.TbStaffTest;
import test.elementmanager.AccountEnterpriseManagerTest;
import test.elementmanager.AccountOrdinaryManagerTest;
import test.elementmanager.AccountVIPManagerTest;


public class Main 
{
	public static void main( String args[] ) throws ClassNotFoundException, SQLException, ParseException, AccountDBOperationException
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
		
		//TbLogTest.insertLogTest();
		//TbLogTest.selectLogsByStaffIDTest();
		//TbLogTest.selectLogsByAccountIDTest();
		//TbLogTest.selectLogsByStaffIDInTimeTest();
		//TbLogTest.selectLogsByAccountIDInTimeTest();
		//TbLogTest.selectLogsByClientIDInTimeTest();
		//TbLogTest.selectLogsInTimeTest();
		//TbLogTest.selectLogsBySuperiorIDInTimeTest();
		
		/*
		AccountManagerTest.createAccountTest();	
		AccountOrdinaryManagerTest.depositAccountTest();
		AccountOrdinaryManagerTest.transferAccountTest();
		AccountOrdinaryManagerTest.transferAccountTest();
		*/
		
		//AccountVIPManagerTest.createAccountTest();
		//AccountVIPManagerTest.depositAccountTest();
		//AccountVIPManagerTest.withdrawAccountTest();
		//AccountVIPManagerTest.transferAccountTest();
		
		//AccountEnterpriseManagerTest.depositAccountTest();
		//AccountEnterpriseManagerTest.withdrawAccountTest();
		AccountEnterpriseManagerTest.transferAccountTest();
	}
}
