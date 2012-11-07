package test.controller;


import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import exception.dboperation.AccountDBOperationException;
import exception.elmanager.AccountManagerException;

import system.controller.AccountController;
import system.element.Account;
import system.manager.AccountManager;

public class AccountControllerVIPAccountTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	

	@Before
	public void setUp() throws Exception 
	{
		AccountManager.deleteAllAccountForTest();
		AccountManager.initial();
	}
	
	@Test
	public void createAccountTestBalanceNeagtive() throws ParseException, AccountManagerException, AccountDBOperationException
	{
		expectedEx.expect(AccountManagerException.class);
	    expectedEx.expectMessage("VIP Account Initial Balance Must Greater Than 0");
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("create", "C002 123 vip current -100 2012.10.10-00:00:00");
	}
	
	@Test
	public void createAccountTestBalanceLessThan10000() throws ParseException, AccountManagerException, AccountDBOperationException
	{
		expectedEx.expect(AccountManagerException.class);
	    expectedEx.expectMessage("VIP Account Initial Balance Must Greater Than 1000000");
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("create", "C002 123 vip current 100 2012.10.10-00:00:00");
	}

	public void setUpAccount() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException
	{
		AccountManager.deleteAllAccountForTest();
		AccountManager.initial();
		
		AccountController accountController =  new AccountController();			
		
		accountController.executeCommand("create", "C001 123 vip fixed 2000000 2012.10.10-00:00:00");
		accountController.executeCommand("create", "C001 123 vip current 1100000 2012.10.10-00:00:00");
		accountController.executeCommand("create", "C002 123 vip fixed 1100000 2012.10.10-00:00:00");
		accountController.executeCommand("create", "C002 123 vip current 20000000 2012.10.10-00:00:00");
		
		accountController.executeCommand("create", "C001 123 ordinary fixed 100 2012.10.10-00:00:00");
		accountController.executeCommand("create", "C001 123 ordinary current 500 2012.10.10-00:00:00");
		accountController.executeCommand("create", "C002 123 ordinary fixed 100 2012.10.10-00:00:00");
		accountController.executeCommand("create", "C002 123 ordinary current 100 2012.10.10-00:00:00");
		
		accountController.executeCommand("create", "C002 123 enterprise current 1000000 2012.10.10-00:00:00");
		
	
	}
	
	@Test
	public void depositAccountTestAccountNotExist() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		setUpAccount();
		
		expectedEx.expect(AccountDBOperationException.class);
	    expectedEx.expectMessage("Account A011 Don't Exist");
			
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("deposit", "A011 123 300");
	}
	
	@Test
	public void depositAccountTestNormal() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		setUpAccount();
			
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("deposit", "A001 123 90000");
		
		Account account = new Account();
		account = AccountManager.selectAccount("A001");
		assertEquals((double)2090000, account.getBalance(),(double)0);		
	}
	
	@Test
	public void depositAccountTestBalanceNeagtive() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		expectedEx.expect(AccountManagerException.class);
	    expectedEx.expectMessage("Deposit Balance Can't be Negative");
		
		setUpAccount();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("deposit", "A001 123 -300");
		
		Account account = new Account();
		account = AccountManager.selectAccount("A001");
		assertEquals((double)2000000, account.getBalance(),(double)0);		
	}
	
	
	
	@Test
	public void depositAccountTestPasswordNotMatche() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		expectedEx.expect(AccountManagerException.class);
	    expectedEx.expectMessage("Account A001 Password Not Match");
		
		setUpAccount();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("deposit", "A001 999 300");
		
		Account account = new Account();
		account = AccountManager.selectAccount("A001");
		assertEquals((double)2000000, account.getBalance(),(double)0);		
	}
	
	@Test
	public void withdrawAccountTestNormal_1() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{		
		setUpAccount();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("withdraw", "A001 123 100000");
		
		Account account = new Account();
		account = AccountManager.selectAccount("A001");
		assertEquals((double)1900000, account.getBalance(),(double)0);		
	}
	
	@Test
	public void withdrawAccountTestNormal_2() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{		
		setUpAccount();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("withdraw", "A001 123 2100000");
		
		Account account = new Account();
		account = AccountManager.selectAccount("A001");
		assertEquals((double)-100000, account.getBalance(),(double)0);		
	}
	
	@Test
	public void withdrawAccountTestBalanceNeagtive() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		expectedEx.expect(AccountManagerException.class);
	    expectedEx.expectMessage("Withdraw Balance Can't be Negative");
		
		setUpAccount();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("withdraw", "A001 123 -300");
		
		Account account = new Account();
		account = AccountManager.selectAccount("A001");
		assertEquals((double)2000000, account.getBalance(),(double)0);		
	}
	
	@Test
	public void withdrawVAccountTestPasswordNotMatch() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		expectedEx.expect(AccountManagerException.class);
	    expectedEx.expectMessage("Account A001 Password Not Match");
		
		setUpAccount();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("withdraw", "A001 999 50");
		
		Account account = new Account();
		account = AccountManager.selectAccount("A001");
		assertEquals((double)2000000, account.getBalance(),(double)0);		
	}
	

	@Test
	public void withdrawAccountTestNoEnoughMoney() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		expectedEx.expect(AccountManagerException.class);
	    expectedEx.expectMessage("VIP Account A001 Can't Overdraw More Than 10000");
		
		setUpAccount();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("withdraw", "A001 123 3000000");
		
		Account account = new Account();
		account = AccountManager.selectAccount("A001");
		assertEquals((double)2000000, account.getBalance(),(double)0);		
	}
	
	@Test
	public void checkAccountBalanceTestNormal() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		setUpAccount();
		
		AccountController accountController =  new AccountController();	
		String balance = accountController.executeCommand("checkbalance", "A001 123");
		
		String expectBalance = "Account Balance " + Double.valueOf(2000000).toString();
		assertEquals(expectBalance, balance);		
	}
	
	@Test
	public void checkAccountBalanceTestPasswordNotMatch() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		expectedEx.expect(AccountManagerException.class);
	    expectedEx.expectMessage("Account A001 Password Not Match");
		
		setUpAccount();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("checkbalance", "A001 999");	
	}
	
	@Test
	public void transferAccountBalanceTestToOwnAccountNormal_1() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		setUpAccount();
		
		AccountController accountController =  new AccountController();	
		String balance = accountController.executeCommand("transfer", "A001 123 A002 1000000");
		
		Account account1 = AccountManager.selectAccount("A001");
		Account account2 = AccountManager.selectAccount("A002");
		
		assertEquals((double)1000000, account1.getBalance(), (double)0);
		assertEquals((double)2100000, account2.getBalance(), (double)0);
	}
	
	@Test
	public void transferAccountBalanceTestToOwnAccountNormal_2() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		setUpAccount();
		
		AccountController accountController =  new AccountController();	
		String balance = accountController.executeCommand("transfer", "A001 123 A002 300000");
		
		Account account1 = AccountManager.selectAccount("A001");
		Account account2 = AccountManager.selectAccount("A002");
		
		assertEquals((double)1700000, account1.getBalance(), (double)0);
		assertEquals((double)1400000, account2.getBalance(), (double)0);
	}
	
	@Test
	public void transferAccountBalanceTestOtherAccountNorma_l() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		setUpAccount();
		
		AccountController accountController =  new AccountController();	
		String balance = accountController.executeCommand("transfer", "A001 123 A002 1000000");
		
		Account account1 = AccountManager.selectAccount("A001");
		Account account2 = AccountManager.selectAccount("A002");
		
		assertEquals((double)1000000, account1.getBalance(), (double)0);
		assertEquals((double)2100000, account2.getBalance(), (double)0);
	}
	
	
	@Test
	public void transferAccountBalanceTestPasswordNotMatch() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		expectedEx.expect(AccountManagerException.class);
	    expectedEx.expectMessage("Account A001 Password Not Match");
		
	    setUpAccount();
		
		AccountController accountController =  new AccountController();	
		String balance = accountController.executeCommand("transfer", "A001 999 A002 2100000");
		
		Account account1 = AccountManager.selectAccount("A001");
		Account account2 = AccountManager.selectAccount("A002");
		
		assertEquals((double)-100000, account1.getBalance(), (double)0);
		assertEquals((double)3100000, account2.getBalance(), (double)0);
	}
	
	
	@Test
	public void transferAccountBalanceTestAccountNotExist() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		expectedEx.expect(AccountDBOperationException.class);
	    expectedEx.expectMessage("Account A999 Don't Exist");
		
	    setUpAccount();
		
		AccountController accountController =  new AccountController();	
		String balance = accountController.executeCommand("transfer", "A001 123 A999 300");
		
		Account account1 = AccountManager.selectAccount("A001");	
		assertEquals((double)2000000, account1.getBalance(), (double)0);
	}
	
	@Test
	public void transferAccountBalanceTestBalanceNegative() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		expectedEx.expect(AccountManagerException.class);
	    expectedEx.expectMessage("Balance Can't be Negative");
		
	    setUpAccount();
		
		AccountController accountController =  new AccountController();	
		String balance = accountController.executeCommand("transfer", "A001 123 A002 -300");
		
		Account account1 = AccountManager.selectAccount("A001");
		Account account2 = AccountManager.selectAccount("A002");
		
		assertEquals((double)2000000, account1.getBalance(), (double)0);
		assertEquals((double)1000000, account2.getBalance(), (double)0);
	}
	
	@Test
	public void transferAccountBalanceTestNoEnoughMoney() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		expectedEx.expect(AccountManagerException.class);
	    expectedEx.expectMessage("VIP Account A001 Can't Overdraw More Than 100000");
		
	    setUpAccount();
		
		AccountController accountController =  new AccountController();	
		String balance = accountController.executeCommand("transfer", "A001 123 A002 3000000");
		
		Account account1 = AccountManager.selectAccount("A001");
		Account account2 = AccountManager.selectAccount("A002");
		
		assertEquals((double)2000000, account1.getBalance(), (double)0);
		assertEquals((double)1000000, account2.getBalance(), (double)0);
	}
	
	@Test
	public void transferAccountBalanceTestTransferToOrdinaryAccountNormal() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
	    setUpAccount();
		
		AccountController accountController =  new AccountController();	
		String balance = accountController.executeCommand("transfer", "A001 123 A005 1000000");
		
		Account account1 = AccountManager.selectAccount("A001");
		Account account2 = AccountManager.selectAccount("A005");
		
		assertEquals((double)1000000, account1.getBalance(), (double)0);
		assertEquals((double)1000100, account2.getBalance(), (double)0);
	}
	
	@Test
	public void transferAccountBalanceTestTransferToEnterpriseAccount() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		expectedEx.expect(AccountManagerException.class);
	    expectedEx.expectMessage("VIP Account Can't Transfer to Enterprise's Account");
		
	    setUpAccount();
		
		AccountController accountController =  new AccountController();	
		String balance = accountController.executeCommand("transfer", "A001 123 A009 1000000");
		
		Account account1 = AccountManager.selectAccount("A001");
		Account account2 = AccountManager.selectAccount("A009");
		
		assertEquals((double)2000000, account1.getBalance(), (double)0);
		assertEquals((double)1000000, account2.getBalance(), (double)0);
	}
	
	@Test
	public void transferAccountBalanceTestTransferToOtherVIPAccount() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
	    setUpAccount();
		
		AccountController accountController =  new AccountController();	
		String balance = accountController.executeCommand("transfer", "A001 123 A003 1000000");
		
		Account account1 = AccountManager.selectAccount("A001");
		Account account2 = AccountManager.selectAccount("A003");
		
		assertEquals((double)1000000, account1.getBalance(), (double)0);
		assertEquals((double)2100000, account2.getBalance(), (double)0);
	}
	
	@Test
	public void changPasswordTesNormal() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
	    setUpAccount();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("chpasswd", "A001 123 999");
		
		String balance = accountController.executeCommand("checkbalance", "A001 123");
		
		String expectBalance = "Account Balance " + Double.valueOf(2000000).toString();
		assertEquals(expectBalance, balance);	
	}
	
	@Test
	public void changPasswordTestPasswordNotMatch() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		expectedEx.expect(AccountManagerException.class);
	    expectedEx.expectMessage("Account A001 Password Not Match");
		
	    setUpAccount();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("chpasswd", "A001 999 123");
		
		String balance = accountController.executeCommand("checkbalance", "A001 123");
		
		String expectBalance = "Account Balance " + Double.valueOf(2000000).toString();
		assertEquals(expectBalance, balance);	
	}

}