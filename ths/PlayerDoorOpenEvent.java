package ths;

public class PlayerDoorOpenEvent extends Event {

	private String doorID;
	private String doorName;
	
	public PlayerDoorOpenEvent(String line) {
		super(line);
		// TODO Auto-generated constructor stub
	}

	public String getDoorID() {
		return doorID;
	}

	public void setDoorID(String doorID) {
		this.doorID = doorID;
	}

	public String getDoorName() {
		return doorName;
	}

	public void setDoorName(String doorName) {
		this.doorName = doorName;
	}

	@Override
	protected void parse() {
		// TODO Auto-generated method stub
		super.parse();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "";
	}

}



