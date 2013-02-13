package ths;

public class PlayerTriggerEvent extends Event {

	private PlayerTriggerType type;
	private String triggerID;
	
	public enum PlayerTriggerType
	{
		ENTER, LEAVE
	}
	
	public PlayerTriggerEvent() {
		// TODO Auto-generated constructor stub
		super("");
	}
	
	public PlayerTriggerEvent(String line)
	{
		super(line);
		parse();
	}

	public PlayerTriggerType getType() {
		return type;
	}

	public void setType(PlayerTriggerType type) {
		this.type = type;
	}

	public String getTriggerID() {
		return triggerID;
	}

	public void setTriggerID(String triggerID) {
		this.triggerID = triggerID;
	}
	
	@Override
	protected void parse() {
		// TODO Auto-generated method stub
		super.parse();
		try {
			parseTrigger();
		} catch (IncorrectTagException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void parseTrigger() throws IncorrectTagException
	{
		if(this.tag.contains("PLAYER_TRIGGER"))
		{
			// set trigger event type
			if(this.tag.equals("PLAYER_TRIGGER_ENTER"))
				setType(PlayerTriggerType.ENTER);
			else if(this.tag.equals("PLAYER_TRIGGER_LEAVE"))
				setType(PlayerTriggerType.LEAVE);
			else
				throw new IncorrectTagException();
				
			setTriggerID(parseTriggerID());
		}
		else
		{
			throw new IncorrectTagException();
		}
	}
	
	private String parseTriggerID()
	{
		String trigID = "";
		int startIndex, endIndex, searchIndex;
		searchIndex = line.indexOf('[');
		
		if(searchIndex >= 0)
		{
			startIndex = line.indexOf('(') + 1;
			endIndex = line.indexOf(')');
			trigID = line.substring(startIndex, endIndex);
//			line = line.substring(endIndex + 4);
		}
		
		return trigID;
	}
	
	@Override
	public String toString() {
		String str = "";
		str = str.concat(super.toString());
		str = str.concat("Type: " + this.type.toString() + "\n");
		str = str.concat("TriggerID " + this.triggerID + "\n");
		str = str.concat("------------------\n");
		
		return str;
	}

}
