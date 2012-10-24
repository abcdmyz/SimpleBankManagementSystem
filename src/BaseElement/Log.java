package BaseElement;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

import Configuration.OperationType;

public class Log 
{
	private String logID, staffID, accountID, clientID, operatedAccountID;
	private OperationType operationType;
	private Date logTime;
	private double operatedBalance;
	
	public Log()
	{
		
	}
	
	public Log( String logID, String staffID, String accountID, String clientID )
	{
		this.setLogID(logID);
		this.setStaffID(staffID);
		this.setAccountID(accountID);
		this.setClientID(clientID);
	}

	public void setLogID(String logID) {
		this.logID = logID;
	}

	public String getLogID() {
		return logID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}

	public String getAccountID() {
		return accountID;
	}

	public void setClientID(String clientID) {
		this.clientID = clientID;
	}

	public String getClientID() {
		return clientID;
	}

	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}

	public OperationType getOperationType() {
		return operationType;
	}

	public void setOperatedAccountID(String operatedAccountID) {
		this.operatedAccountID = operatedAccountID;
	}

	public String getOperatedAccountID() {
		return operatedAccountID;
	}

	public void setOperatedBalance(double operatedBalance) {
		this.operatedBalance = operatedBalance;
	}

	public double getOperatedBalance() {
		return operatedBalance;
	}

	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}

	public Date getLogTime() {
		return logTime;
	}

	
	
}
