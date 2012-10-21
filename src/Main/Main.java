package Main;

import java.sql.SQLException;

import Test.AccountDatabaseTest;

public class Main 
{
	public static void main( String args[] ) throws ClassNotFoundException, SQLException
	{
		AccountDatabaseTest.createAccountTest();
		AccountDatabaseTest.transferAccountTest();
		AccountDatabaseTest.updateAccountTest();
		
	}
}
