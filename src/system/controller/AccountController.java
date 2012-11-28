package system.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import enumtype.AccountType;
import enumtype.ClientType;
import enumtype.OperationType;
import exception.dboperation.AccountDBOperationException;

import exception.elmanager.AccountManagerException;
import system.element.Account;
import system.element.Client;
import system.element.Log;
import system.element.ReturnObjectSet;
import system.element.Staff;
import system.manager.AccountManager;
import system.manager.LogAccountManager;
import system.manager.LogManager;

public class AccountController 
{
	private Staff staff;
	
	public ReturnObjectSet executeCommand( String operation, String parameter, String type, Staff currentStaff ) throws ParseException, AccountManagerException, AccountDBOperationException
	{
		Account account = null;
		AccountManager accountManager = null;
		String returnMessage = null;
		ReturnObjectSet returnObject = new ReturnObjectSet();
		staff = currentStaff;
		
		String[] parameters = parameter.split(" ");
		String accountID = parameters[0];
		double balance;
		
		//System.out.println(parameter);
		
		
			if ( operation.equals("create") )
			{
				accountID = executeCreateAccount(account, parameters);
				returnObject.setReturnMessage("Create Account Successfully. AccountID " + accountID );
			}
			
			else
			{
				account = AccountManager.selectAccount(accountID);
				
				if ( account.getClientType().equals(ClientType.enterprise) && type.equals("individual") )
				{
					throw new AccountManagerException("This is Not Individual Account");
				}
				if ( !account.getClientType().equals(ClientType.enterprise) && type.equals("enterprise") )
				{
					throw new AccountManagerException("This is Not Enterprise Account");
				}
			
				if ( operation.equals("deposit") )
				{
					executeDepositAccount(account, parameters);
					returnObject.setReturnMessage("Account " +  accountID + " Deposit Successfully");
				}
				
				else if ( operation.equals("withdraw") )
				{
					executeWithdrawAccount(account, parameters);
					returnObject.setReturnMessage("Account " +  accountID + " Withdraw Successfully");
				}
				
				else if ( operation.equals("chpasswd") )
				{
					executeChangePassword(account, parameters);
					returnObject.setReturnMessage("Account " +  accountID + " Change Password Successfully");
				}
				
				else if ( operation.equals("checkbalance") )
				{
					balance = executeCheckAccountBalance(account, parameters);
					returnObject.setReturnMessage("Account Balance " + Double.valueOf(balance).toString());
				}
				else if ( operation.equals("transfer") )
				{
					executeTransferAccount(account, parameters);
					returnObject.setReturnMessage("Account " +  accountID + " Transfer Successfully");
				}
				
				else if ( operation.equals("addoperator") )
				{
					//System.out.println(parameter);
					executeAddOperator(account, parameters);
					returnObject.setReturnMessage("Account " +  accountID + " Add Operator Successfully");
				}
				
				else if ( operation.equals("checklog") )
					returnObject = executeCheckLog(account, parameters);
			}
			
		
		/*
		catch (AccountManagerException e) 
		{
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
			System.out.println(AccountManagerException.class.toString());
		} 
		catch (AccountDBOperationException e) 
		{
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
		} 
		catch (ParseException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
		}
		*/
			
		return returnObject;
		
	}

	private ReturnObjectSet executeCheckLog(Account account, String[] parameters) throws AccountManagerException, AccountDBOperationException 
	{
		ReturnObjectSet returnObject = new ReturnObjectSet();
		Client client;
		Date sDate = new Date();
		Date eDate = new Date();
		boolean hasDate = false;
	
		if ( !account.getClientType().equals(ClientType.enterprise))
		{
			client = new Client( account.getAccountID(), account.getClientID(), parameters[1]);
			
			if ( parameters.length >= 4 )
			{
				SimpleDateFormat simFormat = new SimpleDateFormat("yyyy.MM.dd-HH:mm:ss");
				try 
				{
					sDate = simFormat.parse(parameters[2]);
					eDate = simFormat.parse(parameters[3]);
					
					hasDate = true;
				} 
				catch (ParseException e) 
				{
					throw new AccountManagerException("Date Formate Wrong");
				}
			}
		}
		else
		{
			client = new Client( account.getAccountID(), parameters[1], parameters[2]);
			
			if ( parameters.length >= 5 )
			{
				SimpleDateFormat simFormat = new SimpleDateFormat("yyyy.MM.dd-HH:mm:ss");
				try 
				{
					sDate = simFormat.parse(parameters[3]);
					eDate = simFormat.parse(parameters[4]);
					
					hasDate = true;
				} 
				catch (ParseException e) {
				
					throw new AccountManagerException("Date Formate Wrong");
				}
			}
		}
		
		LogAccountManager logAccountManager = new LogAccountManager();
		
		if ( !hasDate )
			returnObject = logAccountManager.checkAccountLog(account, client);
		else
			returnObject = logAccountManager.checkAccountLog(account, client, sDate, eDate);
		
		return returnObject;
		
	}

	private void executeAddOperator(Account account, String[] parameters) throws AccountManagerException, AccountDBOperationException 
	{		
		Client superClient = new Client();
		Client client = new Client();
		Date date = new Date();
		
		superClient.setAccountID(account.getAccountID());
		superClient.setClientID(parameters[1]);
		superClient.setPassword(parameters[2]);
		superClient.setSuperClient("Y");
		
		client.setAccountID(account.getAccountID());
		client.setClientID(parameters[3]);
		client.setPassword(parameters[4]);
		client.setSuperClient("N");
		
		//System.out.println(parameters[1] + " " + parameters[2]);
		//System.out.println(parameters[3] + " " + parameters[4]);
		
		AccountManagerFactory accountManagerFactory = new AccountManagerFactory();
		AccountManager accountManager = accountManagerFactory.getAccountManager(account.getClientType());
		
		if ( parameters.length >= 6 )
		{
			SimpleDateFormat simFormat = new SimpleDateFormat("yyyy.MM.dd-HH:mm:ss");
			try 
			{
				date = simFormat.parse(parameters[5]);
			} 
			catch (ParseException e) {
			
				throw new AccountManagerException("Date Formate Wrong");
			}
		}
		
		/*
		 * Log Part
		 */
		Log log = new Log();
		log.setStaffID(staff.getStaffID());
		log.setAccountID(account.getAccountID());
		log.setClientID(superClient.getClientID());
		log.setLogTime(date);
		
		log.setOperatedAccountID(client.getClientID());
		log.setOperationType(OperationType.addoperator);
		
		LogManager logManager = new LogManager();
		logManager.addLog(log);
		
		accountManager.addOperator(account, superClient, client);
		
	}
	
	/*
	 * Log log = new Log();
		log.setStaffID(staff.getStaffID());
		log.setAccountID(account.getAccountID());
		log.setClientID(client.getClientID());
		log.setLogTime(date);
		
		log.setOperatedAccountID(operation.getOperationAccountID());
		log.setOperatedBalance(operation.getOperationBalance());
		log.setOperationType(operation.getOperationType());
		
		LogManager logManager = new LogManager();
		logManager.addLog(log);
	 */

	private String executeCreateAccount(Account account, String[] parameters) throws ParseException, AccountManagerException, AccountDBOperationException
	{		
		/*
		 * Account Part
		 */
		account = new Account();
		
		account.setClientID(parameters[0]);
		account.setPassword(parameters[1]);
		
		ClientType clientType = ClientType.getEnumFromString(parameters[2]);
		AccountType accountType = AccountType.getEnumFromString(parameters[3]+"_deposit");
		
		account.setClientType(clientType);
		account.setAccountType(accountType);
		
		account.setBalance(Double.valueOf(parameters[4]).doubleValue());
	
		Date openDate = new Date();
		if ( parameters.length >= 6 )
		{
			SimpleDateFormat simFormat = new SimpleDateFormat("yyyy.MM.dd-HH:mm:ss");
			openDate = simFormat.parse(parameters[5]);
		}
		account.setOpenDate(openDate);
		
		AccountManagerFactory accountManagerFactory = new AccountManagerFactory();
		AccountManager accountManager = accountManagerFactory.getAccountManager(clientType);
		
		String accountID = accountManager.createAccount(account);
		
		/*
		 * Log Part
		 */
		Log log = new Log();
		
		log.setStaffID(staff.getStaffID());
		log.setAccountID(account.getAccountID());
		log.setClientID(account.getClientID());
		log.setLogTime(openDate);
		
		log.setOperatedBalance(account.getBalance());
		log.setOperationType(OperationType.create);
		
		LogManager logManager = new LogManager();
		logManager.addLog(log);
		
		return accountID;
	}
	
	private void executeDepositAccount(Account account, String[] parameters) throws AccountManagerException, AccountDBOperationException
	{
		/*
		 * Account Part
		 */
		Client client = null;
		double balance;
		Date date = new Date();
		
		AccountManagerFactory accountManagerFactory = new AccountManagerFactory();
		AccountManager accountManager = accountManagerFactory.getAccountManager(account.getClientType());
		
		if ( !account.getClientType().equals(ClientType.enterprise))
		{
			client = new Client( account.getAccountID(), account.getClientID(), parameters[1]);
			balance = Double.valueOf(parameters[2]).doubleValue();
			
			if ( parameters.length >= 4 )
			{
				SimpleDateFormat simFormat = new SimpleDateFormat("yyyy.MM.dd-HH:mm:ss");
				try 
				{
					date = simFormat.parse(parameters[3]);
				} 
				catch (ParseException e) {
				
					throw new AccountManagerException("Date Formate Wrong");
				}
			}
		}
		else
		{
			client = new Client( account.getAccountID(), parameters[1], parameters[2]);
			balance = Double.valueOf(parameters[3]).doubleValue();
			
			if ( parameters.length >= 5 )
			{
				SimpleDateFormat simFormat = new SimpleDateFormat("yyyy.MM.dd-HH:mm:ss");
				try 
				{
					date = simFormat.parse(parameters[4]);
				} 
				catch (ParseException e) {
				
					throw new AccountManagerException("Date Formate Wrong");
				}
			}
		}
		
		accountManager.depositAccount(account, client, balance);
		
		
		/*
		 * Log Part
		 */
		Log log = new Log();
		
		log.setStaffID(staff.getStaffID());
		log.setAccountID(account.getAccountID());
		log.setClientID(client.getClientID());
		log.setLogTime(date);
		
		log.setOperatedBalance(balance);
		log.setOperationType(OperationType.deposit);
		
		LogManager logManager = new LogManager();
		logManager.addLog(log);
	}
	
	private void executeWithdrawAccount(Account account, String[] parameters) throws AccountManagerException, AccountDBOperationException
	{
		/*
		 * Account Part
		 */
		Client client = null;
		double balance;
		Date date = new Date();
		
		AccountManagerFactory accountManagerFactory = new AccountManagerFactory();
		AccountManager accountManager = accountManagerFactory.getAccountManager(account.getClientType());
		
		if ( !account.getClientType().equals(ClientType.enterprise))
		{
			client = new Client( account.getAccountID(), account.getClientID(), parameters[1]);
			balance = Double.valueOf(parameters[2]).doubleValue();
			
			if ( parameters.length >= 4 )
			{
				SimpleDateFormat simFormat = new SimpleDateFormat("yyyy.MM.dd-HH:mm:ss");
				try 
				{
					date = simFormat.parse(parameters[3]);
				} 
				catch (ParseException e) {
				
					throw new AccountManagerException("Date Formate Wrong");
				}
			}
		}
		else
		{
			client = new Client( account.getAccountID(), parameters[1], parameters[2]);
			balance = Double.valueOf(parameters[3]).doubleValue();
			
			if ( parameters.length >= 5 )
			{
				SimpleDateFormat simFormat = new SimpleDateFormat("yyyy.MM.dd-HH:mm:ss");
				try 
				{
					date = simFormat.parse(parameters[4]);
				} 
				catch (ParseException e) {
				
					throw new AccountManagerException("Date Formate Wrong");
				}
			}
		}
		
		accountManager.withdrawAccount(account, client, balance);
		
		/*
		 * Log Part
		 */
		Log log = new Log();
		
		log.setStaffID(staff.getStaffID());
		log.setAccountID(account.getAccountID());
		log.setClientID(client.getClientID());
		log.setLogTime(date);
		
		log.setOperatedBalance(balance);
		log.setOperationType(OperationType.withdraw);
		
		LogManager logManager = new LogManager();
		logManager.addLog(log);
	}
	
	private void executeChangePassword(Account account, String[] parameters) throws AccountManagerException, AccountDBOperationException
	{
		Client client = null;
		String newPassword = null;
		Date date = new Date();
		
		AccountManagerFactory accountManagerFactory = new AccountManagerFactory();
		AccountManager accountManager = accountManagerFactory.getAccountManager(account.getClientType());
		
		if ( !account.getClientType().equals(ClientType.enterprise))
		{
			client = new Client( account.getAccountID(), account.getClientID(), parameters[1]);
			newPassword = parameters[2];
			
			if ( parameters.length >= 4 )
			{
				SimpleDateFormat simFormat = new SimpleDateFormat("yyyy.MM.dd-HH:mm:ss");
				try 
				{
					date = simFormat.parse(parameters[3]);
				} 
				catch (ParseException e) {
				
					throw new AccountManagerException("Date Formate Wrong");
				}
			}
		}
		else
		{
			client = new Client( account.getAccountID(), parameters[1], parameters[2]);
			newPassword = parameters[3];
			
			if ( parameters.length >= 5 )
			{
				SimpleDateFormat simFormat = new SimpleDateFormat("yyyy.MM.dd-HH:mm:ss");
				try 
				{
					date = simFormat.parse(parameters[4]);
				} 
				catch (ParseException e) {
				
					throw new AccountManagerException("Date Formate Wrong");
				}
			}
		}

		accountManager.changePassword(account, client, newPassword);
		
		/*
		 * Log Part
		 */
		Log log = new Log();
		
		log.setStaffID(staff.getStaffID());
		log.setAccountID(account.getAccountID());
		log.setClientID(client.getClientID());
		log.setLogTime(date);
		
		log.setOperationType(OperationType.chpassword);
		
		LogManager logManager = new LogManager();
		logManager.addLog(log);
	}

	private double executeCheckAccountBalance(Account account, String[] parameters) throws AccountManagerException, AccountDBOperationException
	{
		Client client = null;
		Date date = new Date();

		AccountManagerFactory accountManagerFactory = new AccountManagerFactory();
		AccountManager accountManager = accountManagerFactory.getAccountManager(account.getClientType());
		
		if ( !account.getClientType().equals(ClientType.enterprise))
		{
			client = new Client( account.getAccountID(), account.getClientID(), parameters[1]);
			
			if ( parameters.length >= 3 )
			{
				SimpleDateFormat simFormat = new SimpleDateFormat("yyyy.MM.dd-HH:mm:ss");
				try 
				{
					date = simFormat.parse(parameters[2]);
				} 
				catch (ParseException e) {
				
					throw new AccountManagerException("Date Formate Wrong");
				}
			}
			
		}
		else
		{
			client = new Client( account.getAccountID(), parameters[1], parameters[2]);
			
			if ( parameters.length >= 4 )
			{
				SimpleDateFormat simFormat = new SimpleDateFormat("yyyy.MM.dd-HH:mm:ss");
				try 
				{
					date = simFormat.parse(parameters[3]);
				} 
				catch (ParseException e) {
				
					throw new AccountManagerException("Date Formate Wrong");
				}
			}
		}
		
		double balance = accountManager.checkAccountBalance(account, client);
		
		/*
		 * Log Part
		 */
		Log log = new Log();
		
		log.setStaffID(staff.getStaffID());
		log.setAccountID(account.getAccountID());
		log.setClientID(client.getClientID());
		log.setLogTime(date);
		
		log.setOperationType(OperationType.checkbalance);
		
		LogManager logManager = new LogManager();
		logManager.addLog(log);
		
		return balance;
	}

	
	
	private void executeTransferAccount(Account account, String[] parameters) throws AccountManagerException, AccountDBOperationException
	{
		Client client = null;
		double balance;
		String accountID2 = null;
		Date date = new Date();
		
		AccountManagerFactory accountManagerFactory = new AccountManagerFactory();
		AccountManager accountManager = accountManagerFactory.getAccountManager(account.getClientType());
		
		if ( !account.getClientType().equals(ClientType.enterprise))
		{
			client = new Client( account.getAccountID(), account.getClientID(), parameters[1]);
			accountID2 = parameters[2];
			balance = Double.valueOf(parameters[3]).doubleValue();
			
			if ( parameters.length >= 5 )
			{
				SimpleDateFormat simFormat = new SimpleDateFormat("yyyy.MM.dd-HH:mm:ss");
				try 
				{
					date = simFormat.parse(parameters[4]);
				} 
				catch (ParseException e) {
				
					throw new AccountManagerException("Date Formate Wrong");
				}
			}
		}
		else
		{
			client = new Client( account.getAccountID(), parameters[1], parameters[2]);
			accountID2 = parameters[3];
			balance = Double.valueOf(parameters[4]).doubleValue();
			
			if ( parameters.length >= 6 )
			{
				SimpleDateFormat simFormat = new SimpleDateFormat("yyyy.MM.dd-HH:mm:ss");
				try 
				{
					date = simFormat.parse(parameters[5]);
				} 
				catch (ParseException e) {
				
					throw new AccountManagerException("Date Formate Wrong");
				}
			}
		}
		
		accountManager.transferAccount(account, client, accountID2, balance);
		
		/*
		 * Log Part
		 */
		Log log = new Log();
		
		log.setStaffID(staff.getStaffID());
		log.setAccountID(account.getAccountID());
		log.setClientID(client.getClientID());
		log.setLogTime(date);
		
		log.setOperatedAccountID(accountID2);
		log.setOperatedBalance(balance);
		log.setOperationType(OperationType.transfer);
		
		LogManager logManager = new LogManager();
		logManager.addLog(log);
	}
}
