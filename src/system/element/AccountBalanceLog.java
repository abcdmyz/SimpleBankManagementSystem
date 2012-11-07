package system.element;

import java.util.Date;

import enumtype.OperationType;

public class AccountBalanceLog 
{
	private String logID, accountID;
	private double operationBalance, leftBalance;
	private OperationType operationType;
	private Date operationTime;
	
	public void setLogID(String logID) {
		this.logID = logID;
	}
	public String getLogID() {
		return logID;
	}
	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}
	public String getAccountID() {
		return accountID;
	}

	public void setLeftBalance(double leftBalance) {
		this.leftBalance = leftBalance;
	}
	public double getLeftBalance() {
		return leftBalance;
	}

	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}
	public OperationType getOperationType() {
		return operationType;
	}
	public void setOperationBalance(double operationBalance) {
		this.operationBalance = operationBalance;
	}
	public double getOperationBalance() {
		return operationBalance;
	}
	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}
	public Date getOperationTime() {
		return operationTime;
	}


}
