package system.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import enumtype.AccountType;
import enumtype.ClientType;
import exception.dboperation.AccountDBOperationException;

import exception.elmanager.AccountManagerException;
import system.element.Account;
import system.element.Client;
import system.manager.AccountManager;

public class AccountController 
{
	public String executeCommand( String operation, String parameter ) throws ParseException, AccountManagerException, AccountDBOperationException
	{
		Account account = null;
		AccountManager accountManager = null;
		String returnMessage = null;
		
		String[] parameters = parameter.split(" ");
		String accountID = parameters[0];
		double balance;
		
			if ( operation.equals("create") )
			{
				accountID = executeCreateAccount(account, parameters);
			}
			
			else
			{
				account = AccountManager.selectAccount(accountID);
			
				if ( operation.equals("deposit") )
					executeDepositAccount(account, parameters);
				
				else if ( operation.equals("withdraw") )
					executeWithdrawAccount(account, parameters);
				
				else if ( operation.equals("chpasswd") )
					executeChangePassword(account, parameters);
				
				else if ( operation.equals("checkbalance") )
				{
					balance = executeCheckAccountBalance(account, parameters);
					returnMessage = "Account Balance " + Double.valueOf(balance).toString();
				}
				else if ( operation.equals("transfer") )
					executeTransferAccount(account, parameters);
				
				else if ( operation.equals("addoperator") )
					executeAddOperator(account, parameters);
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
			
		return returnMessage;
		
	}

	private void executeAddOperator(Account account, String[] parameters) throws AccountManagerException, AccountDBOperationException 
	{		
		Client superClient = new Client();
		Client client = new Client();
		
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
		
		accountManager.addOperator(account, superClient, client);
		
	}

	private String executeCreateAccount(Account account, String[] parameters) throws ParseException, AccountManagerException, AccountDBOperationException
	{		
		account = new Account();
		
		account.setClientID(parameters[0]);
		account.setPassword(parameters[1]);
		
		ClientType clientType = ClientType.getEnumFromString(parameters[2]);
		AccountType accountType = AccountType.getEnumFromString(parameters[3]+"_deposit");
		
		account.setClientType(clientType);
		account.setAccountType(accountType);
		
		account.setBalance(Double.valueOf(parameters[4]).doubleValue());
	
		SimpleDateFormat simFormat = new SimpleDateFormat("yyyy.MM.dd-HH:mm:ss");
		Date openDate = simFormat.parse(parameters[5]);
		account.setOpenDate(openDate);
		
		AccountManagerFactory accountManagerFactory = new AccountManagerFactory();
		AccountManager accountManager = accountManagerFactory.getAccountManager(clientType);
		
		String accountID = accountManager.createAccount(account);
		
		return accountID;
	}
	
	private void executeDepositAccount(Account account, String[] parameters) throws AccountManagerException, AccountDBOperationException
	{
		Client client = null;
		double balance;
		
		AccountManagerFactory accountManagerFactory = new AccountManagerFactory();
		AccountManager accountManager = accountManagerFactory.getAccountManager(account.getClientType());
		
		if ( !account.getClientType().equals(ClientType.enterprise))
		{
			client = new Client( account.getAccountID(), account.getClientID(), parameters[1]);
			balance = Double.valueOf(parameters[2]).doubleValue();
		}
		else
		{
			client = new Client( account.getAccountID(), parameters[1], parameters[2]);
			balance = Double.valueOf(parameters[3]).doubleValue();
		}
		
		accountManager.depositAccount(account, client, balance);
		
	}
	
	private void executeWithdrawAccount(Account account, String[] parameters) throws AccountManagerException, AccountDBOperationException
	{
		Client client = null;
		double balance;
		
		AccountManagerFactory accountManagerFactory = new AccountManagerFactory();
		AccountManager accountManager = accountManagerFactory.getAccountManager(account.getClientType());
		
		if ( !account.getClientType().equals(ClientType.enterprise))
		{
			client = new Client( account.getAccountID(), account.getClientID(), parameters[1]);
			balance = Double.valueOf(parameters[2]).doubleValue();
		}
		else
		{
			client = new Client( account.getAccountID(), parameters[1], parameters[2]);
			balance = Double.valueOf(parameters[3]).doubleValue();
		}
		
		accountManager.withdrawAccount(account, client, balance);
	}
	
	private void executeChangePassword(Account account, String[] parameters) throws AccountManagerException, AccountDBOperationException
	{
		Client client = null;
		String newPassword = null;
		
		AccountManagerFactory accountManagerFactory = new AccountManagerFactory();
		AccountManager accountManager = accountManagerFactory.getAccountManager(account.getClientType());
		
		if ( !account.getClientType().equals(ClientType.enterprise))
		{
			client = new Client( account.getAccountID(), account.getClientID(), parameters[1]);
			newPassword = parameters[2];
		}
		else
		{
			client = new Client( account.getAccountID(), parameters[1], parameters[2]);
			newPassword = parameters[3];
		}
		
		accountManager.changePassword(account, client, newPassword);
	}

	private double executeCheckAccountBalance(Account account, String[] parameters) throws AccountManagerException, AccountDBOperationException
	{
		Client client = null;

		AccountManagerFactory accountManagerFactory = new AccountManagerFactory();
		AccountManager accountManager = accountManagerFactory.getAccountManager(account.getClientType());
		
		if ( !account.getClientType().equals(ClientType.enterprise))
		{
			client = new Client( account.getAccountID(), account.getClientID(), parameters[1]);
			
		}
		else
		{
			client = new Client( account.getAccountID(), parameters[1], parameters[2]);
		}
		
		double balance = accountManager.checkAccountBalance(account, client);
		
		return balance;
	}

	
	
	private void executeTransferAccount(Account account, String[] parameters) throws AccountManagerException, AccountDBOperationException
	{
		Client client = null;
		double balance;
		String accountID2 = null;
		
		AccountManagerFactory accountManagerFactory = new AccountManagerFactory();
		AccountManager accountManager = accountManagerFactory.getAccountManager(account.getClientType());
		
		if ( !account.getClientType().equals(ClientType.enterprise))
		{
			client = new Client( account.getAccountID(), account.getClientID(), parameters[1]);
			accountID2 = parameters[2];
			balance = Double.valueOf(parameters[3]).doubleValue();
		}
		else
		{
			client = new Client( account.getAccountID(), parameters[1], parameters[2]);
			accountID2 = parameters[3];
			balance = Double.valueOf(parameters[4]).doubleValue();
		}
		
		accountManager.transferAccount(account, client, accountID2, balance);
	}
}
