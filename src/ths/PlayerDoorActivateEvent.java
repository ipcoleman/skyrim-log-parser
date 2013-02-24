package src.ths;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerDoorActivateEvent extends Event {
	
	private String questID;
	private String aliasName;
	
	public PlayerDoorActivateEvent() {
		super("");
	}
	
	public PlayerDoorActivateEvent(String line) {
		super(line);
		parse();
	}
	
	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public String getQuestID() {
		return questID;
	}

	public void setQuestID(String questID) {
		this.questID = questID;
	}

	@Override
	protected void parse() {
		super.parse();
		try {
			parsePlayerDoorActivate();
		} catch (IncorrectTagException e) {
			e.printStackTrace();
		}
	}
	
	private void parsePlayerDoorActivate() throws IncorrectTagException
	{
		if(this.tag.equals("PLAYER_DOOR_ACTIVATE"))
		{
			setQuestID(parseQuestID());
			setAliasName(parseAliasName());
		}
		else
		{
			throw new IncorrectTagException();
		}
	}
	
	private String parseQuestID()
	{
		String form = "";		
		String searchRegex = "(\\[{1})([_a-zA-Z0-9\\s]+)(\\<{1})alias([_a-zA-Z0-9\\s]+)on quest([_a-zA-Z0-9\\s]+)(\\({1})([a-zA-Z0-9]+)(\\){1})(\\>{1})(\\]{1})"; // e.g. [ths1_COQRescueOrcScript <alias OrcCageDoor on quest ths1_CaptiveOrc (0201A9F8)>]
		Pattern pattern = Pattern.compile(searchRegex);
	    Matcher matcher = pattern.matcher(line);
	    
		if(matcher.find())
		{
			form = line.substring(matcher.start(), matcher.end()).replaceFirst(searchRegex, "$5$6$7$8");
			// chop off formID from line
			line = line.substring(matcher.end());	
		}
		
		return form;
	}
	
	private String parseAliasName()
	{
		String fName = "";
		String searchRegex = "(\\\"{1})([_a-zA-Z0-9\\s]+)(\\\"{1})"; //e.g. "Form Name"
		Pattern pattern = Pattern.compile(searchRegex);
		Matcher matcher = pattern.matcher(line);
		if(matcher.find())
		{
			fName = line.substring(matcher.start(), matcher.end()).replaceFirst(searchRegex, "$2");
			line = line.substring(matcher.end());
		}
				
		return fName;
	}
	
	@Override
	public String toString() {
		String str = "";
		str = str.concat(super.toString());
		str = str.concat("QuestID: " + this.questID + "\n");
		str = str.concat("AliasName: " + this.aliasName + "\n");
		str = str.concat("------------------\n");
		
		return str;
	}

}
