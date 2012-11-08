package system.element;

import enumtype.Position;

public class Staff 
{
	private String staffID, superiorID, password;
	private Position position;
	
	public Staff()
	{
	}
	
	public Staff( String staffID, String password, Position position, String superiorID )
	{
		this.setStaffID(staffID);
		this.setPosition(position);
		this.setSuperiorID(superiorID);
		this.setPassword(password);
	}
	
	public Staff( String staffID, String password, Position position)
	{
		this.setStaffID(staffID);
		this.setPosition(position);
		this.setPassword(password);
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Position getPosition() {
		return position;
	}

	public void setSuperiorID(String superiorID) {
		this.superiorID = superiorID;
	}

	public String getSuperiorID() {
		return superiorID;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}
}
