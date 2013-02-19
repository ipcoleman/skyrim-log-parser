package ths;

public class PlayerDisplayCaseOpenEvent extends Event {

	private String displayCaseScriptID;
	
	public PlayerDisplayCaseOpenEvent(String line) {
		super(line);
		// TODO Auto-generated constructor stub
	}

	public String getDisplayCaseScriptID() {
		return displayCaseScriptID;
	}

	public void setDisplayCaseScriptID(String displayCaseScriptID) {
		this.displayCaseScriptID = displayCaseScriptID;
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

