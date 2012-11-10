package system.element;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

import enumtype.OperationType;


public class Log 
{
	private String logID, staffID, accountID, clientID, operatedAccountID;
	private OperationType operationType;
	private Date logTime;
	private double operatedBalance;
	
	public Log()
	{
		
	}
	
	public String toString()
	{
		String s = new String();
		
		s += logID.toString();
		s += " " + staffID.toString();
		s += " " + accountID.toString();
		s += " " + clientID.toString();
		
		if ( operatedAccountID == null )
			s += " null";
		else
			s += " " + operatedAccountID.toString();
	
		s += " " + operationType.toString();
		s += " " + Double.valueOf(operatedBalance).toString();
		s += " " + logTime.toGMTString();
		return s;
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
