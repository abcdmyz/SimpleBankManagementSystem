package test.dboperation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import system.element.Account;
import system.element.Client;
import database.connection.JDBCConnection;
import database.operation.TbAccountOperation;
import database.operation.TbEnterpriseOperatorOperation;
import enumtype.AccountType;
import enumtype.ClientType;
import exception.dboperation.AccountDBOperationException;

public class TbEnterpriseOperatorTest 
{
	public static void createAccount() throws ClassNotFoundException, SQLException, AccountDBOperationException
	{
		Connection connection = JDBCConnection.getCommonConnection();
		
		TbAccountOperation.deleteAccountTable(connection);
		
		Date openDate1 = new Date();
		Account account1 = new Account("A0001","C0001","123");
		account1.setAccountType(AccountType.fixed_deposit);
		account1.setClientType(ClientType.ordinary);
		account1.setBalance(100);
		account1.setOpenDate(openDate1);
		TbAccountOperation.createAccount(connection, account1);
		
		Date openDate2 = new Date();
		Account account2 = new Account("A0002","C0002","123");
		account2.setAccountType(AccountType.fixed_deposit);
		account2.setClientType(ClientType.ordinary);
		account2.setBalance(100);
		account2.setOpenDate(openDate1);
		TbAccountOperation.createAccount(connection, account2);
		
		Date openDate3 = new Date();
		Account account3 = new Account("A0003","C0003","123");
		account3.setAccountType(AccountType.current_deposit);
		account3.setClientType(ClientType.vip);
		account3.setBalance(100);
		account3.setOpenDate(openDate1);
		TbAccountOperation.createAccount(connection, account3);
		
		Date openDate4 = new Date();
		Account account4 = new Account("A0004","C0004","123");
		account4.setAccountType(AccountType.current_deposit);
		account4.setClientType(ClientType.vip);
		account4.setBalance(100);
		account4.setOpenDate(openDate1);
		TbAccountOperation.createAccount(connection, account4);
		
		Date openDate5 = new Date();
		Account account5 = new Account("A0005","C0005","123");
		account5.setAccountType(AccountType.current_deposit);
		account5.setClientType(ClientType.enterprise);
		account5.setBalance(100);
		account5.setOpenDate(openDate1);
		TbAccountOperation.createAccount(connection, account5);
		
		Date openDate6 = new Date();
		Account account6 = new Account("A0006","C0006","123");
		account6.setAccountType(AccountType.current_deposit);
		account6.setClientType(ClientType.enterprise);
		account6.setBalance(100);
		account6.setOpenDate(openDate1);
		TbAccountOperation.createAccount(connection, account6);
		
		JDBCConnection.closeConnection(connection);
	}
	
	public static void createEnterpriseOperatorTest() throws ClassNotFoundException, AccountDBOperationException
	{
		Connection connection = JDBCConnection.getCommonConnection();
		
		Client operator1 = new Client("A0006", "C0010", "999");
		operator1.setSuperClient("Y");
		TbEnterpriseOperatorOperation.createEnterpriseOperator(connection, operator1);
		
		Client operator2 = new Client("A0006", "C0010", "999");
		operator1.setSuperClient("Y");
		TbEnterpriseOperatorOperation.createEnterpriseOperator(connection, operator2);
		
		Client operator3 = new Client("A0006", "C0010", "999");
		operator1.setSuperClient("Y");
		TbEnterpriseOperatorOperation.createEnterpriseOperator(connection, operator3);
	}
}
