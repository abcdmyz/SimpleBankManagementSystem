package system.controller;

import system.manager.AccountEnterpriseManager;
import system.manager.AccountManager;
import system.manager.AccountOrdinaryManager;
import system.manager.AccountVIPManager;
import enumtype.AccountType;
import enumtype.ClientType;

public class AccountManagerFactory 
{
	public AccountManager getAccountManager( ClientType clientType )
	{
		AccountManager accountManager = null;
		
		if ( clientType.equals(ClientType.ordinary) )
			accountManager = new AccountOrdinaryManager();
		
		if ( clientType.equals(ClientType.vip) )
			accountManager = new AccountVIPManager();
		
		if ( clientType.equals(ClientType.enterprise) )
			accountManager = new AccountEnterpriseManager();
		
		return accountManager;
	}
}
