package Main;

import java.sql.SQLException;
import java.text.ParseException;

import exception.dboperation.AccountDBOperationException;
import exception.dboperation.StaffDBOperationException;

import system.manager.AccountManager;
import system.manager.LogManager;
import system.manager.StaffManager;
import system.ui.LogInMenu;
import test.controller.AccountControllerOrdinaryAccountTest;
import test.dboperation.TbAccountTest;
import test.dboperation.TbLogTest;
import test.dboperation.TbStaffTest;
import test.manager.AccountEnterpriseManagerTest;
import test.manager.AccountOrdinaryManagerTest;
import test.manager.AccountVIPManagerTest;


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
		//AccountEnterpriseManagerTest.transferAccountTest();
		
		//AccountControllerTest.createAccountTest();
		/*
		AccountManager.deleteAllAccountForTest();
		AccountManager.deleteAllClientForTest();
		AccountManager.initial();
		
		StaffManager.deleteAllStaffForTest();
		StaffManager.addAdminStaffForTest();
		try {
			StaffManager.initial();
		} catch (StaffDBOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		LogManager.deleteAllLogForTest();
		try {
			LogManager.initial();
		} catch (StaffDBOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		LogInMenu logInMenu = new LogInMenu();
		logInMenu.OperatorLogInPage();
		
		
	}
}
