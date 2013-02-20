package src.ths;

public class PlayerWeaponEvent extends Event {

	private PlayerWeaponHand hand;
	private String weaponID;
	private String weaponName;
	
	public enum PlayerWeaponHand
	{
		RIGHT, LEFT
	}
	
	public PlayerWeaponEvent() {
		// TODO Auto-generated constructor stub
		super("");
	}
	
	public PlayerWeaponEvent(String line)
	{
		super(line);
		parse();
	}

	public PlayerWeaponHand getHand() {
		return hand;
	}

	public void setHand(PlayerWeaponHand hand) {
		this.hand = hand;
	}
	
	public String getWeaponID() {
		return weaponID;
	}

	public void setWeaponID(String weaponID) {
		this.weaponID = weaponID;
	}

	@Override
	protected void parse() {
		// TODO Auto-generated method stub
		super.parse();
		try {
			parseWeapon();
		} catch (IncorrectTagException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getWeaponName() {
		return weaponName;
	}

	public void setWeaponName(String weaponName) {
		this.weaponName = weaponName;
	}

	private void parseWeapon() throws IncorrectTagException
	{
		if(this.tag.contains("PLAYER_WEAPON"))
		{
			// set trigger event type
			if(this.tag.equals("PLAYER_WEAPON_RIGHT"))
				setHand(PlayerWeaponHand.RIGHT);
			else if(this.tag.equals("PLAYER_WEAPON_LEFT"))
				setHand(PlayerWeaponHand.LEFT);
			else
				throw new IncorrectTagException();
				
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
		str = str.concat("Hand: " + this.hand.toString() + "\n");
		str = str.concat("WeaponID: " + this.weaponID + "\n");
		str = str.concat("WeaponName: " + this.weaponName + "\n");
		str = str.concat("------------------\n");
		
		return str;
	}

}
