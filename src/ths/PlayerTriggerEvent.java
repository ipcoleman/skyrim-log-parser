package ths;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		super.parse();
		try {
			parseTrigger();
		} catch (IncorrectTagException e) {
			e.printStackTrace();
		}
	}
	
	private void parseTrigger() throws IncorrectTagException
	{
		// set trigger event type
		if(this.tag.equals("PLAYER_TRIGGER_ENTER") || this.tag.equals("PLAYER_ENTER_TRIGGER"))
			setType(PlayerTriggerType.ENTER);
		else if(this.tag.equals("PLAYER_TRIGGER_LEAVE"))
			setType(PlayerTriggerType.LEAVE);
		else
			throw new IncorrectTagException();
			
		setTriggerID(parseTriggerID());
	}
	
	private String parseTriggerID()
	{
		//[11/15/2012 - 10:17:16AM] PLAYER_TRIGGER_ENTER [ths1_TriggerEnterSCRIPT < (0208C7A1)>]
		//[11/15/2012 - 10:21:43AM] PLAYER_ENTER_TRIGGER [ths1_CCQNearCatTriggerSCRIPT <alias NearCatTRIG on quest ths1_MissingDog (02009344)>]
		//[11/15/2012 - 10:17:31AM] PLAYER_TRIGGER_LEAVE [ths1_TriggerEnterSCRIPT < (0208CD80)>]
		
		String trigID = "";
		String searchRegex = "(\\[{1})([_a-zA-Z0-9\\s]+)(\\<{1})([_a-zA-Z0-9\\s]*)(\\({1})([a-zA-Z0-9]+)(\\){1}\\>{1}\\]{1})"; // e.g. [Form < (02050A4C)>]
		Pattern pattern = Pattern.compile(searchRegex);
		Matcher matcher = pattern.matcher(line);

		if(matcher.find())
		{
			trigID = line.substring(matcher.start(), matcher.end()).replaceFirst(searchRegex, "$7");
			line = line.substring(matcher.end());
		}
		
		if(this.tag.equals("PLAYER_ENTER_TRIGGER"))
			System.out.println("");
		return trigID;
	}
	
	@Override
	public String toString() {
		String str = "";
		str = str.concat(super.toString());
		str = str.concat("Type: " + this.type.toString() + "\n");
		str = str.concat("TriggerID: " + this.triggerID + "\n");
		str = str.concat("------------------\n");
		
		return str;
	}

}
