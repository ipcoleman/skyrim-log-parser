package src.ths;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerDoorOpenEvent extends Event {

	private String doorID;
	private String questName;
	
	public PlayerDoorOpenEvent() {
		super("");
	}
	
	public PlayerDoorOpenEvent(String line) {
		super(line);
		parse();
	}

	public String getDoorID() {
		return doorID;
	}

	public void setDoorID(String doorID) {
		this.doorID = doorID;
	}

	public String getQuestName() {
		return questName;
	}

	public void setQuestName(String questName) {
		this.questName = questName;
	}

	@Override
	protected void parse() {
		// PLAYER_DOOR_OPEN [ths1_MDQRescueDogScript <alias ths1_DogCageDoorAlias on quest ths1_MissingDog (02009344)>] "ths1_DogCageDoorAlias"
		super.parse();
		try {
			parsePlayerItemObtain();
		} catch (IncorrectTagException e) {
			e.printStackTrace();
		}
	}
	
	private void parsePlayerItemObtain() throws IncorrectTagException
	{
		if(this.tag.equals("PLAYER_DOOR_OPEN"))
		{
			setDoorID(parseDoorID());	// also parses questName		
		}
		else
		{
			throw new IncorrectTagException();
		}
	}
	
	private String parseDoorID()
	{
		String form = "";		
		String searchRegex = "(\\[{1})([_a-zA-Z0-9\\s]+)(\\<{1})alias([_a-zA-Z0-9\\s]+)on quest([_a-zA-Z0-9\\s]+)(\\({1})([a-zA-Z0-9]+)(\\){1})(\\>{1})(\\]{1})";
		Pattern pattern = Pattern.compile(searchRegex);
	    Matcher matcher = pattern.matcher(line);
	    
		if(matcher.find())
		{
			form = line.substring(matcher.start(), matcher.end()).replaceFirst(searchRegex, "$5");
			setQuestName(line.substring(matcher.start(), matcher.end()).replaceFirst(searchRegex, "$6$7$8$9"));
			// chop off formID from line
			line = line.substring(matcher.end());	
		}
		
		return form;
	}
	
	@Override
	public String toString() {
		String str = "";
		str = str.concat(super.toString());
		str = str.concat("DoorID: " + this.doorID + "\n");
		str = str.concat("QuestName: " + this.questName + "\n");
		str = str.concat("------------------\n");
		
		return str;
	}

}



