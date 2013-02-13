package ths;

public class PlayerWorldspaceChangeEvent extends Event {

	private String worldspaceName;
	private String worldspaceID;
	
	public PlayerWorldspaceChangeEvent(String line) {
		super(line);
		// TODO Auto-generated constructor stub
	}
	
	public String getWorldspaceName() {
		return worldspaceName;
	}

	public void setWorldspaceName(String worldspaceName) {
		this.worldspaceName = worldspaceName;
	}

	public String getWorldspaceID() {
		return worldspaceID;
	}

	public void setWorldspaceID(String worldspaceID) {
		this.worldspaceID = worldspaceID;
	}

	@Override
	protected void parse() {	
		super.parse();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "";
	}

}
