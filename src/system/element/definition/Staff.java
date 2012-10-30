package system.element.definition;

import enumtype.Position;

public class Staff 
{
	private String staffID, superiorID;
	private Position position;
	
	public Staff()
	{
	}
	
	public Staff( String staffID, Position position, String superiorID )
	{
		this.setStaffID(staffID);
		this.setPosition(position);
		this.setSuperiorID(superiorID);
	}
	
	public Staff( String staffID, Position position)
	{
		this.setStaffID(staffID);
		this.setPosition(position);
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
}
