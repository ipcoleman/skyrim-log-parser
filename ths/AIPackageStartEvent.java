package ths;

public class AIPackageStartEvent extends Event {

	private String aliasID;
	private String packageID;
	
	public AIPackageStartEvent(String line) {
		super(line);
		// TODO Auto-generated constructor stub
	}

	public String getAliasID() {
		return aliasID;
	}

	public void setAliasID(String aliasID) {
		this.aliasID = aliasID;
	}

	public String getPackageID() {
		return packageID;
	}

	public void setPackageID(String packageID) {
		this.packageID = packageID;
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


