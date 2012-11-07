package test.controller;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import system.controller.AccountController;
import system.element.Account;
import system.manager.AccountManager;
import exception.dboperation.AccountDBOperationException;
import exception.elmanager.AccountManagerException;


public class AccountControllerEnterpriseAccountTest 
{
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	

	@Before
	public void setUp() throws Exception 
	{
		AccountManager.deleteAllAccountForTest();
		AccountManager.deleteAllClientForTest();
		AccountManager.initial();
	}
	
	
	@Test
	public void createeAccountTestNormal() throws ParseException, AccountManagerException, AccountDBOperationException
	{
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("create", "C001 123 enterprise current 10000 2012.10.10-00:00:00");
		
		Account account = new Account();
		account = AccountManager.selectAccount("A001");
		assertEquals((double)10000, account.getBalance(),(double)0);	
	}
	
	@Test
	public void createAccountTestBalanceNeagtive() throws ParseException, AccountManagerException, AccountDBOperationException
	{
		expectedEx.expect(AccountManagerException.class);
	    expectedEx.expectMessage("Enterprise Account Initial Balance Must Greater Than 0");
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("create", "C002 123 enterprise current -100 2012.10.10-00:00:00");
	}
	
	
	public void setUpAccount() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException
	{
		AccountManager.deleteAllAccountForTest();
		AccountManager.deleteAllClientForTest();
		AccountManager.initial();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("create", "C001 123 enterprise fixed 10000 2012.10.10-00:00:00");
		accountController.executeCommand("create", "C001 123 enterprise current 50000 2012.10.10-00:00:00");
		accountController.executeCommand("create", "C002 123 enterprise fixed 10000 2012.10.10-00:00:00");
		accountController.executeCommand("create", "C002 123 enterprise current 50000 2012.10.10-00:00:00");
		
		accountController.executeCommand("create", "C003 123 enterprise current 10000 2012.10.10-00:00:00");
		accountController.executeCommand("create", "C003 123 enterprise current 10000 2012.10.10-00:00:00");
		accountController.executeCommand("create", "C004 123 enterprise current 10000 2012.10.10-00:00:00");
		accountController.executeCommand("create", "C004 123 enterprise current 10000 2012.10.10-00:00:00");
		
		accountController.executeCommand("create", "C005 123 ordinary fixed 100 2012.10.10-00:00:00");
		accountController.executeCommand("create", "C006 123 vip current 1000010 2012.10.10-00:00:00");
	}
	
	public void setUpAccountAndOperator() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException
	{
		AccountManager.deleteAllAccountForTest();
		AccountManager.deleteAllClientForTest();
		AccountManager.initial();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("create", "C001 123 enterprise fixed 10000 2012.10.10-00:00:00");
		accountController.executeCommand("create", "C001 123 enterprise current 50000 2012.10.10-00:00:00");
		accountController.executeCommand("create", "C002 123 enterprise fixed 10000 2012.10.10-00:00:00");
		accountController.executeCommand("create", "C002 123 enterprise current 50000 2012.10.10-00:00:00");
		
		accountController.executeCommand("create", "C003 123 enterprise current 10000 2012.10.10-00:00:00");
		accountController.executeCommand("create", "C003 123 enterprise current 10000 2012.10.10-00:00:00");
		accountController.executeCommand("create", "C004 123 enterprise current 10000 2012.10.10-00:00:00");
		accountController.executeCommand("create", "C004 123 enterprise current 10000 2012.10.10-00:00:00");
		
		accountController.executeCommand("create", "C005 123 ordinary fixed 100 2012.10.10-00:00:00");
		accountController.executeCommand("create", "C006 123 vip current 1000010 2012.10.10-00:00:00");
		
		accountController.executeCommand("addoperator", "A001 C001 123 OA1001 999");
		accountController.executeCommand("addoperator", "A001 C001 123 OA1002 999");
		accountController.executeCommand("addoperator", "A003 C002 123 OA2001 999");
		accountController.executeCommand("addoperator", "A003 C002 123 OA2002 999");
	}
	
	
	@Test
	public void depositAccountTestNormal_1() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		setUpAccountAndOperator();
			
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("deposit", "A001 C001 123 30000");
		
		Account account = new Account();
		account = AccountManager.selectAccount("A001");
		assertEquals((double)40000, account.getBalance(),(double)0);		
	}
	
	@Test
	public void depositAccountTestNormal_2() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		setUpAccountAndOperator();
			
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("deposit", "A001 OA1001 999 30000");
		
		Account account = new Account();
		account = AccountManager.selectAccount("A001");
		assertEquals((double)40000, account.getBalance(),(double)0);		
	}
	
	@Test
	public void depositAccountTestAccountNotExist() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		setUpAccountAndOperator();
		
		expectedEx.expect(AccountDBOperationException.class);
	    expectedEx.expectMessage("Account A011 Don't Exist");
			
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("deposit", "A011 C001 123 300");
	}
	
	@Test
	public void depositAccountTestBalanceNeagtive() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		expectedEx.expect(AccountManagerException.class);
	    expectedEx.expectMessage("Deposit Balance Can't be Negative");
		
	    setUpAccountAndOperator();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("deposit", "A001 C001 123 -300");
		
		Account account = new Account();
		account = AccountManager.selectAccount("A001");
		assertEquals((double)10000, account.getBalance(),(double)0);		
	}
	
	
	@Test
	public void depositAccountTestOperatorNotExist() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		expectedEx.expect(AccountDBOperationException.class);
	    expectedEx.expectMessage("Accout A001 Don't Have Operator C111");
		
	    setUpAccountAndOperator();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("deposit", "A001 C111 123 300");	
	}
	
	@Test
	public void depositAccountTestOperatorNotMatch() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		expectedEx.expect(AccountDBOperationException.class);
	    expectedEx.expectMessage("Accout A001 Don't Have Operator OA2001");
		
	    setUpAccountAndOperator();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("deposit", "A001 OA2001 999 300");
	}	
	
	@Test
	public void depositAccountTestPasswordNotMatch_1() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		expectedEx.expect(AccountManagerException.class);
	    expectedEx.expectMessage("Operator C001 Password Not Match");
		
	    setUpAccountAndOperator();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("deposit", "A001 C001 000 300");		
	}
	
	@Test
	public void depositAccountTestPasswordNotMatch_2() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		expectedEx.expect(AccountManagerException.class);
	    expectedEx.expectMessage("Operator OA1001 Password Not Match");
		
	    setUpAccountAndOperator();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("deposit", "A001 OA1001 000 300");		
	}
	
	@Test
	public void withdrawAccountTestNormal_1() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		setUpAccountAndOperator();
			
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("withdraw", "A001 C001 123 5000");
		
		Account account = new Account();
		account = AccountManager.selectAccount("A001");
		assertEquals((double)5000, account.getBalance(),(double)0);		
	}
	
	@Test
	public void withdrawAccountTestNormal_2() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		setUpAccountAndOperator();
			
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("withdraw", "A001 OA1001 999 5000");
		
		Account account = new Account();
		account = AccountManager.selectAccount("A001");
		assertEquals((double)5000, account.getBalance(),(double)0);		
	}
	
	@Test
	public void withdrawAccountTestAccountNotExist() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		setUpAccountAndOperator();
		
		expectedEx.expect(AccountDBOperationException.class);
	    expectedEx.expectMessage("Account A011 Don't Exist");
			
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("withdraw", "A011 C001 123 300");
	}
	
	@Test
	public void withdrawAccountTestBalanceNeagtive() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		expectedEx.expect(AccountManagerException.class);
	    expectedEx.expectMessage("Withdraw Balance Can't be Negative");
		
	    setUpAccountAndOperator();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("withdraw", "A001 C001 123 -300");
		
		Account account = new Account();
		account = AccountManager.selectAccount("A001");
		assertEquals((double)100, account.getBalance(),(double)0);		
	}
	
	
	@Test
	public void withdrawAccountTestOperatorNotExist() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		expectedEx.expect(AccountDBOperationException.class);
	    expectedEx.expectMessage("Accout A001 Don't Have Operator C111");
		
	    setUpAccountAndOperator();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("withdraw", "A001 C111 123 300");	
	}
	
	@Test
	public void withdrawAccountTestOperatorNotMatch() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		expectedEx.expect(AccountDBOperationException.class);
	    expectedEx.expectMessage("Accout A001 Don't Have Operator OA2001");
		
	    setUpAccountAndOperator();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("withdraw", "A001 OA2001 999 300");
	}	
	
	@Test
	public void withdrawAccountTestPasswordNotMatch_1() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		expectedEx.expect(AccountManagerException.class);
	    expectedEx.expectMessage("Operator C001 Password Not Match");
		
	    setUpAccountAndOperator();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("withdraw", "A001 C001 000 300");		
	}
	
	@Test
	public void withdrawAccountTestPasswordNotMatch_2() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		expectedEx.expect(AccountManagerException.class);
	    expectedEx.expectMessage("Operator OA1001 Password Not Match");
		
	    setUpAccountAndOperator();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("withdraw", "A001 OA1001 000 300");		
	}
	
	@Test
	public void withdrawAccountTestNoEnoughMoney() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		expectedEx.expect(AccountManagerException.class);
	    expectedEx.expectMessage("Enterprise Account A001 Has No Enough Money");
		
	    setUpAccountAndOperator();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("withdraw", "A001 OA1001 999 30000");		
	}
	
	
	
	@Test
	public void checkAccountBalanceTestNormal_1() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		setUpAccountAndOperator();
		
		AccountController accountController =  new AccountController();	
		String balance = accountController.executeCommand("checkbalance", "A001 C001 123");
		
		String expectBalance = "Account Balance " + Double.valueOf(10000).toString();
		assertEquals(expectBalance, balance);		
	}
	
	@Test
	public void checkAccountBalanceTestNormal_2() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		setUpAccountAndOperator();
		
		AccountController accountController =  new AccountController();	
		String balance = accountController.executeCommand("checkbalance", "A001 OA1001 999");
		
		String expectBalance = "Account Balance " + Double.valueOf(10000).toString();
		assertEquals(expectBalance, balance);		
	}
	
	@Test
	public void checkAccountBalanceTestPasswordNotMatch() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		expectedEx.expect(AccountManagerException.class);
	    expectedEx.expectMessage("Operator C001 Password Not Match");
		
	    setUpAccountAndOperator();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("checkbalance", "A001 C001 999");	
	}
	
	@Test
	public void checkAccountBalanceTestPasswordNotMatch_2() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		expectedEx.expect(AccountManagerException.class);
	    expectedEx.expectMessage("Operator OA1001 Password Not Match");
		
	    setUpAccountAndOperator();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("checkbalance", "A001 OA1001 000");	
	}
	
	
	@Test
	public void transferAccountBalanceTestNormal_1() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		setUpAccountAndOperator();
		
		AccountController accountController =  new AccountController();	
		String balance = accountController.executeCommand("transfer", "A001 C001 123 A002 5000");
		
		Account account1 = AccountManager.selectAccount("A001");
		Account account2 = AccountManager.selectAccount("A002");
		
		assertEquals((double)5000, account1.getBalance(), (double)0);
		assertEquals((double)55000, account2.getBalance(), (double)0);
	}
	
	@Test
	public void transferAccountBalanceTestNormal_2() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		setUpAccountAndOperator();
		
		AccountController accountController =  new AccountController();	
		String balance = accountController.executeCommand("transfer", "A001 OA1001 999 A002 5000");
		
		Account account1 = AccountManager.selectAccount("A001");
		Account account2 = AccountManager.selectAccount("A002");
		
		assertEquals((double)5000, account1.getBalance(), (double)0);
		assertEquals((double)55000, account2.getBalance(), (double)0);
	}
	
	
	
	@Test
	public void transferAccountBalanceTestSuperOperatorPasswordNotMatch() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		expectedEx.expect(AccountManagerException.class);
	    expectedEx.expectMessage("Operator C001 Password Not Match");
		
	    setUpAccountAndOperator();
		
		AccountController accountController =  new AccountController();	
		String balance = accountController.executeCommand("transfer", "A001 C001 999 A002 1000");
	}
	
	@Test
	public void transferAccountBalanceTestOperatorPasswordNotMatch() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		expectedEx.expect(AccountManagerException.class);
	    expectedEx.expectMessage("Operator OA1001 Password Not Match");
		
	    setUpAccountAndOperator();
		
		AccountController accountController =  new AccountController();	
		String balance = accountController.executeCommand("transfer", "A001 OA1001 123 A002 1000");
	}
	
	
	@Test
	public void transferAccountBalanceTestOperatorPasswordNotMatchNotExist() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		expectedEx.expect(AccountDBOperationException.class);
	    expectedEx.expectMessage("Accout A001 Don't Have Operator C999");
		
	    setUpAccountAndOperator();
		
		AccountController accountController =  new AccountController();	
		String balance = accountController.executeCommand("transfer", "A001 C999 123 A002 1000");
	}
	
	
	
	@Test
	public void transferAccountBalanceTestAccountNotExist() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		expectedEx.expect(AccountDBOperationException.class);
	    expectedEx.expectMessage("Account A999 Don't Exist");
		
	    setUpAccountAndOperator();
		
		AccountController accountController =  new AccountController();	
		String balance = accountController.executeCommand("transfer", "A001 C001 123 A999 300");
	}
	
	
	@Test
	public void transferAccountBalanceTestBalanceNegative() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		expectedEx.expect(AccountManagerException.class);
	    expectedEx.expectMessage("Balance Can't be Negative");
		
	    setUpAccountAndOperator();
		
		AccountController accountController =  new AccountController();	
		String balance = accountController.executeCommand("transfer", "A001 C001 123 A001 -300");
	}
	
	@Test
	public void transferAccountBalanceTestNoEnoughMoney() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		expectedEx.expect(AccountManagerException.class);
	    expectedEx.expectMessage("Enterprise Account A001 Has No Enough Money in Account");
		
	    setUpAccountAndOperator();
		
		AccountController accountController =  new AccountController();	
		String balance = accountController.executeCommand("transfer", "A001 C001 123 A002 300000");
	}
	
	@Test
	public void transferAccountBalanceTestTransferToOrdinaryClient() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		expectedEx.expect(AccountManagerException.class);
	    expectedEx.expectMessage("Enterprise Account Can't Transfer to Non-Enterprise's Account");
		
	    setUpAccountAndOperator();
		
		AccountController accountController =  new AccountController();	
		String balance = accountController.executeCommand("transfer", "A001 C001 123 A009 300");
	}
	

	@Test
	public void transferAccountBalanceTestTransferToVIPClient() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		expectedEx.expect(AccountManagerException.class);
	    expectedEx.expectMessage("Enterprise Account Can't Transfer to Non-Enterprise's Account");
		
	    setUpAccountAndOperator();
		
		AccountController accountController =  new AccountController();	
		String balance = accountController.executeCommand("transfer", "A001 C001 123 A010 300");
	}
	
	
	
	@Test
	public void changPasswordTesNormal_1() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		setUpAccountAndOperator();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("chpasswd", "A001 C001 123 999");
		
		String balance = accountController.executeCommand("checkbalance", "A001 C001 999");
		
		String expectBalance = "Account Balance " + Double.valueOf(10000).toString();
		assertEquals(expectBalance, balance);	
	}
	
	@Test
	public void changPasswordTesNormal_2() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		setUpAccountAndOperator();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("chpasswd", "A001 OA1001 999 123");
		
		String balance = accountController.executeCommand("checkbalance", "A001 OA1001 123");
		
		String expectBalance = "Account Balance " + Double.valueOf(10000).toString();
		assertEquals(expectBalance, balance);	
	}
	
	@Test
	public void changPasswordTestPasswordNotMatch() throws ParseException, AccountManagerException, AccountDBOperationException, ClassNotFoundException 
	{
		expectedEx.expect(AccountManagerException.class);
	    expectedEx.expectMessage("Operator OA1001 Password Not Match");
		
	    setUpAccountAndOperator();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("chpasswd", "A001 OA1001 222 123");	
	}
	
	
	@Test
	public void addOperatorTestNormal() throws ClassNotFoundException, ParseException, AccountManagerException, AccountDBOperationException
	{
		setUpAccount();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("addoperator", "A001 C001 123 OA1001 999");
		
		String balance = accountController.executeCommand("checkbalance", "A001 OA1001 999");
		String expectBalance = "Account Balance " + Double.valueOf(10000).toString();
		assertEquals(expectBalance, balance);	
	}
	
	@Test
	public void addOperatorTestSuperOperatorNotExist() throws ClassNotFoundException, ParseException, AccountManagerException, AccountDBOperationException
	{
		expectedEx.expect(AccountDBOperationException.class);
	    expectedEx.expectMessage("Accout A001 Don't Have Operator C002");
		
		setUpAccount();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("addoperator", "A001 C002 123 OA1001 999");	
	}
	
	@Test
	public void addOperatorTestSuperOperatorPasswordNotMatcht() throws ClassNotFoundException, ParseException, AccountManagerException, AccountDBOperationException
	{
		expectedEx.expect(AccountManagerException.class);
	    expectedEx.expectMessage("Super Operator C001 Password Not Match");
		
		setUpAccount();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("addoperator", "A001 C001 999 OA1001 999");
	}
	
	@Test
	public void addOperatorTestSOperatorNotSuper() throws ClassNotFoundException, ParseException, AccountManagerException, AccountDBOperationException
	{
		expectedEx.expect(AccountManagerException.class);
	    expectedEx.expectMessage("Operator OA1001 is Not a Super Operator");
		
		setUpAccount();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("addoperator", "A001 C001 123 OA1001 999");
		accountController.executeCommand("addoperator", "A001 OA1001 999 OA1002 999");	
	}
	
	@Test
	public void accountNotExistTest() throws ClassNotFoundException, ParseException, AccountManagerException, AccountDBOperationException
	{
		expectedEx.expect(AccountDBOperationException.class);
	    expectedEx.expectMessage("Account A111 Don't Exist");
		
		setUpAccount();
		
		AccountController accountController =  new AccountController();	
		accountController.executeCommand("addoperator", "A111 C001 123 OA1001 999");	
	}
}
