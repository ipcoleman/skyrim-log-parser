package src.ths;

public class PlayerWeaponWithdrawnEvent extends Event {

	private String weaponID;
	private String weaponName;
	
	public PlayerWeaponWithdrawnEvent(String line) {
		super(line);
		// TODO Auto-generated constructor stub
	}

	public String getWeaponID() {
		return weaponID;
	}

	public void setWeaponID(String weaponID) {
		this.weaponID = weaponID;
	}

	public String getWeaponName() {
		return weaponName;
	}

	public void setWeaponName(String weaponName) {
		this.weaponName = weaponName;
	}
	
	@Override
	protected void parse() {
		// TODO Auto-generated method stub
		super.parse();
		try {
			parseWeaponWithdrawn();
		} catch (IncorrectTagException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void parseWeaponWithdrawn() throws IncorrectTagException
	{
		if(this.tag.contains("PLAYER_WEAPON_WITHDRAWN"))
		{			
			setWeaponID(parseWeaponID());
			setWeaponName(parseWeaponName());
		}
		else
		{
			throw new IncorrectTagException();
		}
	}
	
	private String parseWeaponID()
	{
		String wHand = "";
		int startIndex, endIndex, searchIndex;
		String searchTerm = "[WEAPON";
		searchIndex = line.indexOf(searchTerm);
		
		if(searchIndex >= 0)
		{
			startIndex = searchIndex + searchTerm.length() + 4;
			endIndex = line.indexOf(')', startIndex);
			wHand = line.substring(startIndex, endIndex);
			line = line.substring(endIndex + 4);
		}
		
		return wHand;
	}
	
	private String parseWeaponName()
	{
		String wName = "";
		int startIndex, endIndex, searchIndex;
		String searchTerm = "\"";
		searchIndex = line.indexOf(searchTerm);
		
		if(searchIndex >= 0)
		{
			startIndex = searchIndex + 1;
			endIndex = line.indexOf("\"", startIndex);
			wName = line.substring(startIndex, endIndex);
//			line = line.substring(endIndex + 2);
		}
				
		return wName;
	}
	
	@Override
	public String toString() {
		String str = "";
		str = str.concat(super.toString());
		str = str.concat("WeaponID: " + this.weaponID + "\n");
		str = str.concat("WeaponName: " + this.weaponName + "\n");
		str = str.concat("------------------\n");
		
		return str;
	}
}
