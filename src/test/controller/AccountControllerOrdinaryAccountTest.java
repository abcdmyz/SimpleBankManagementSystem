package test.controller;


import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import enumtype.ClientType;
import enumtype.Position;
import exception.dboperation.AccountDBOperationException;
import exception.elmanager.AccountManagerException;

import system.controller.AccountController;
import system.element.Account;
import system.element.Log;
import system.element.ReturnObjectSet;
import system.element.Staff;
import system.manager.AccountManager;
import system.manager.LogManager;

public class AccountControllerOrdinaryAccountTest 
{
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	Staff staff = new Staff("S001","123",Position.operator);
	
	

	@Before
	public void setUp() throws Exception 
	{
		AccountManager.deleteAllAccountForTest();
		AccountManager.initial();
		LogManager.deleteAllLogForTest();
		LogManager.initial();
	}
	/*
	@Test
	public void createOrdinaryAccountTestNormal() throws Exception
	{
		AccountController accountController =  new AccountController();
		
		accountController.executeCommand("create", "C001 123 ordinary fixed 100 2012.10.10-00:00:00",staff);
		accountController.executeCommand("create", "C001 123 ordinary current 100 2012.10.10-00:00:00",staff);
	}
	
	@Test
	public void createAccountTestBalanceNeagtive() throws ParseException, AccountManagerException, AccountDBOperationException
	{
		expectedEx.expect(AccountManagerException.class);
	    expectedEx.expectMessage("Ordinary Account Initial Balance Must Greater Than 0");
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("create", "C001 123 ordinary current -100 2012.10.10-00:00:00",staff);
	}
	
	public void setUpAccount() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException
	{		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("create", "C001 123 ordinary fixed 100 2012.10.10-00:00:00",staff);
		accountController.executeCommand("create", "C001 123 ordinary current 500 2012.10.10-00:00:00",staff);
		accountController.executeCommand("create", "C002 123 ordinary fixed 100 2012.10.10-00:00:00",staff);
		accountController.executeCommand("create", "C002 123 ordinary current 100 2012.10.10-00:00:00",staff);
		
		accountController.executeCommand("create", "C001 123 vip current 1000010 2012.10.10-00:00:00",staff);
		accountController.executeCommand("create", "C001 123 vip current 1000010 2012.10.10-00:00:00",staff);
		accountController.executeCommand("create", "C002 123 vip current 1000010 2012.10.10-00:00:00",staff);
		accountController.executeCommand("create", "C002 123 vip current 1000010 2012.10.10-00:00:00",staff);
	}
	
	
	public void setUpAccountAndOperation() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException
	{
		AccountManager.deleteAllAccountForTest();
		AccountManager.initial();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("create", "C001 123 ordinary fixed 100 2012.10.10-00:00:00",staff);
		accountController.executeCommand("create", "C001 123 ordinary current 500 2012.10.10-00:00:00",staff);
		accountController.executeCommand("create", "C002 123 ordinary fixed 100 2012.10.10-00:00:00",staff);
		accountController.executeCommand("create", "C002 123 ordinary current 100 2012.10.10-00:00:00",staff);
		
		accountController.executeCommand("create", "C001 123 vip current 1000010 2012.10.10-00:00:00",staff);
		accountController.executeCommand("create", "C001 123 vip current 1000010 2012.10.10-00:00:00",staff);
		accountController.executeCommand("create", "C002 123 vip current 1000010 2012.10.10-00:00:00",staff);
		accountController.executeCommand("create", "C002 123 vip current 1000010 2012.10.10-00:00:00",staff);
		
		
		accountController.executeCommand("deposit", "A001 123 300",staff);
		accountController.executeCommand("withdraw", "A001 123 50",staff);
		ReturnObjectSet balance = accountController.executeCommand("checkbalance", "A001 123",staff);
		accountController.executeCommand("transfer", "A002 123 A001 300",staff);
		accountController.executeCommand("chpasswd", "A001 123 999",staff);
	}
	
	
	
	@Test
	public void depositAccountTestAccountNotExist() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		setUpAccount();
		
		expectedEx.expect(AccountDBOperationException.class);
	    expectedEx.expectMessage("Account A011 Don't Exist");
			
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("deposit", "A011 123 300",staff);
	}
	
	@Test
	public void depositAccountTestNormal() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		setUpAccount();
			
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("deposit", "A001 123 300",staff);
		
		Account account = new Account();
		account = AccountManager.selectAccount("A001");
		assertEquals((double)400, account.getBalance(),(double)0);		
	}
	
	@Test
	public void depositAccountTestBalanceNeagtive() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		expectedEx.expect(AccountManagerException.class);
	    expectedEx.expectMessage("Deposit Balance Can't be Negative");
		
		setUpAccount();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("deposit", "A001 123 -300",staff);
		
		Account account = new Account();
		account = AccountManager.selectAccount("A001");
		assertEquals((double)400, account.getBalance(),(double)0);		
	}
	
	
	
	@Test
	public void depositAccountTestPasswordNotMatche() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		expectedEx.expect(AccountManagerException.class);
	    expectedEx.expectMessage("Account A001 Password Not Match");
		
		setUpAccount();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("deposit", "A001 999 300",staff);
		
		Account account = new Account();
		account = AccountManager.selectAccount("A001");
		assertEquals((double)400, account.getBalance(),(double)0);		
	}
	
	@Test
	public void withdrawAccountTestNormal() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{		
		setUpAccount();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("withdraw", "A001 123 50",staff);
		
		Account account = new Account();
		account = AccountManager.selectAccount("A001");
		assertEquals((double)50, account.getBalance(),(double)0);		
	}
	
	@Test
	public void withdrawAccountTestBalanceNeagtive() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		expectedEx.expect(AccountManagerException.class);
	    expectedEx.expectMessage("Withdraw Balance Can't be Negative");
		
		setUpAccount();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("withdraw", "A001 123 -300",staff);
		
		Account account = new Account();
		account = AccountManager.selectAccount("A001");
		assertEquals((double)400, account.getBalance(),(double)0);		
	}
	
	@Test
	public void withdrawAccountTestPasswordNotMatch() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		expectedEx.expect(AccountManagerException.class);
	    expectedEx.expectMessage("Account A001 Password Not Match");
		
		setUpAccount();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("withdraw", "A001 999 50",staff);
		
		Account account = new Account();
		account = AccountManager.selectAccount("A001");
		assertEquals((double)50, account.getBalance(),(double)0);		
	}
	

	@Test
	public void withdrawAccountTestNoEnoughMoney() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		expectedEx.expect(AccountManagerException.class);
	    expectedEx.expectMessage("Ordianry Account A001 Has No Enough Money in Account");
		
		setUpAccount();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("withdraw", "A001 123 300",staff);
		
		Account account = new Account();
		account = AccountManager.selectAccount("A001");
		assertEquals((double)50, account.getBalance(),(double)0);		
	}
	
	@Test
	public void checkAccountBalanceTestNormal() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		setUpAccount();
		
		AccountController accountController =  new AccountController();	
		ReturnObjectSet balance = accountController.executeCommand("checkbalance", "A001 123",staff);
		
		String expectBalance = "Account Balance " + Double.valueOf(100).toString();
		assertEquals(expectBalance, balance.getReturnMessage());		
	}
	
	@Test
	public void checkAccountBalanceTestPasswordNotMatch() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		expectedEx.expect(AccountManagerException.class);
	    expectedEx.expectMessage("Account A001 Password Not Match");
		
		setUpAccount();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("checkbalance", "A001 999",staff);	
	}
	
	@Test
	public void transferAccountBalanceTestNormal() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		setUpAccount();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("transfer", "A002 123 A001 300",staff);
		
		Account account1 = AccountManager.selectAccount("A002");
		Account account2 = AccountManager.selectAccount("A001");
		
		assertEquals((double)200, account1.getBalance(), (double)0);
		assertEquals((double)400, account2.getBalance(), (double)0);
	}
	
	
	@Test
	public void transferAccountBalanceTestPasswordNotMatch() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		expectedEx.expect(AccountManagerException.class);
	    expectedEx.expectMessage("Account A002 Password Not Match");
		
	    setUpAccount();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("transfer", "A002 999 A001 300",staff);
		
		Account account1 = AccountManager.selectAccount("A002");
		Account account2 = AccountManager.selectAccount("A001");
		
		assertEquals((double)500, account1.getBalance(), (double)0);
		assertEquals((double)100, account2.getBalance(), (double)0);
	}
	
	
	@Test
	public void transferAccountBalanceTestAccountNotExist() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		expectedEx.expect(AccountDBOperationException.class);
	    expectedEx.expectMessage("Account A999 Don't Exist");
		
	    setUpAccount();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("transfer", "A002 123 A999 300",staff);
		
		Account account1 = AccountManager.selectAccount("A002");	
		assertEquals((double)500, account1.getBalance(), (double)0);
	}
	
	@Test
	public void transferAccountBalanceTestBalanceNegative() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		expectedEx.expect(AccountManagerException.class);
	    expectedEx.expectMessage("Balance Can't be Negative");
		
	    setUpAccount();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("transfer", "A002 123 A001 -300",staff);
		
		Account account1 = AccountManager.selectAccount("A002");
		Account account2 = AccountManager.selectAccount("A001");
		
		assertEquals((double)500, account1.getBalance(), (double)0);
		assertEquals((double)100, account2.getBalance(), (double)0);
	}
	
	@Test
	public void transferAccountBalanceTestNoEnoughMoney() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		expectedEx.expect(AccountManagerException.class);
	    expectedEx.expectMessage("Ordianry Account A002 Has No Enough Money in Account");
		
	    setUpAccount();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("transfer", "A002 123 A001 3000",staff);
		
		Account account1 = AccountManager.selectAccount("A002");
		Account account2 = AccountManager.selectAccount("A001");
		
		assertEquals((double)500, account1.getBalance(), (double)0);
		assertEquals((double)100, account2.getBalance(), (double)0);
	}
	
	@Test
	public void transferAccountBalanceTestTransferToOtherClient() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		expectedEx.expect(AccountManagerException.class);
	    expectedEx.expectMessage("Ordinary Account Can't Transfer to Other Client's Account");
		
	    setUpAccount();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("transfer", "A002 123 A003 300",staff);
		
		Account account1 = AccountManager.selectAccount("A002");
		Account account2 = AccountManager.selectAccount("A003");
		
		assertEquals((double)500, account1.getBalance(), (double)0);
		assertEquals((double)100, account2.getBalance(), (double)0);
	}
	
	@Test
	public void transferAccountBalanceTestTransferToOrdinaryAccount() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		expectedEx.expect(AccountManagerException.class);
	    expectedEx.expectMessage("Ordinary Account Can Only Transfer to Ordinary Account");
		
	    setUpAccount();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("transfer", "A002 123 A005 300",staff);
		
		Account account1 = AccountManager.selectAccount("A002");
		Account account2 = AccountManager.selectAccount("A005");
		
		assertEquals((double)500, account1.getBalance(), (double)0);
		assertEquals((double)100, account2.getBalance(), (double)0);
	}
	
	@Test
	public void changPasswordTesNormal() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
	    setUpAccount();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("chpasswd", "A001 123 999",staff);
		
		ReturnObjectSet balance = accountController.executeCommand("checkbalance", "A001 123",staff);
		
		String expectBalance = "Account Balance " + Double.valueOf(100).toString();
		assertEquals(expectBalance, balance.getReturnMessage());	
	}
	
	@Test
	public void changPasswordTestPasswordNotMatch() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		expectedEx.expect(AccountManagerException.class);
	    expectedEx.expectMessage("Account A001 Password Not Match");
		
	    setUpAccount();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("chpasswd", "A001 999 123",staff);
		
		ReturnObjectSet balance = accountController.executeCommand("checkbalance", "A001 123",staff);
		
		String expectBalance = "Account Balance " + Double.valueOf(100).toString();
		assertEquals(expectBalance, balance.getReturnMessage());	
	}
	
	
	@Test
	public void checkLog() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
	    setUpAccountAndOperation();
	   
		AccountController accountController =  new AccountController();	
		ReturnObjectSet returnObject = accountController.executeCommand("checklog", "A001 123",staff);
		
		ArrayList<Log> logList = returnObject.getLogList();
		
		for ( Log log: logList )
		{
			System.out.println(log.toString());
		}
		
	}
	*/
	
}
