package src.ths;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerHitEvent extends Event {

	String aliasName;
	String questID;
	
	public PlayerHitEvent()
	{
		super("");
	}
	
	public PlayerHitEvent(String line) {
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
		// TODO Auto-generated method stub
		super.parse();
		try {
			parsePlayerHit();
		} catch (IncorrectTagException e) {
			e.printStackTrace();
		}
	}
	
	private void parsePlayerHit() throws IncorrectTagException
	{
		if(this.tag.equals("PLAYER_HIT"))
		{
			// sets aliasName AND questID
			setAliasName(parseAliasName());		
		}
		else
		{
			throw new IncorrectTagException();
		}
	}
	
	private String parseAliasName()
	{
		String form = "";	
		// e.g. [ths1_MDQDogAliasSCRIPT <alias CapturedDog on quest ths1_MissingDog (02009344)>]
		String searchRegex = "(\\[{1})([_a-zA-Z0-9]+)(\\s*)(\\<{1})alias([a-zA-Z0-9]+)(\\s+)on quest(\\s+)([_a-zA-Z0-9\\s]+)(\\({1})([a-zA-Z0-9]+)(\\){1})(\\>{1})(\\]{1})"; 
		Pattern pattern = Pattern.compile(searchRegex);
	    Matcher matcher = pattern.matcher(line);
	    
		if(matcher.find())
		{
			form = line.substring(matcher.start(), matcher.end()).replaceFirst(searchRegex, "$5");
			// also set quest info while we're parsing this line
			this.questID = line.substring(matcher.start(), matcher.end()).replaceFirst(searchRegex, "$8$9$10$11");
			
			// chop off formID from line
			line = line.substring(matcher.end());	
		}
		
		return form;
	}
	
	@Override
	public String toString() {
		String str = "";
		str = str.concat(super.toString());
		str = str.concat("AliasName: " + this.aliasName + "\n");
		str = str.concat("QuestID: " + this.questID + "\n");
		str = str.concat("------------------\n");
		
		return str;
	}

}
