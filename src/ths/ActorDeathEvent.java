package src.ths;

import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActorDeathEvent extends Event {

	// ACTOR_DEATH [ths1_MDQDogAliasSCRIPT <alias CapturedDog on quest ths1_MissingDog (02009344)>] "CapturedDog" KILLER [Actor < (00000014)>]
	
	private String aliasName;
	private String questID;
	private String killerID;
	
	public ActorDeathEvent()
	{
		super("");
	}
	
	public ActorDeathEvent(String line) {
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
	
	public String getKillerID() {
		return killerID;
	}

	public void setKillerID(String killerID) {
		this.killerID = killerID;
	}

	@Override
	protected void parse() {
		// TODO Auto-generated method stub
		super.parse();
		try {
			parseActorDeath();
		} catch (IncorrectTagException e) {
			e.printStackTrace();
		}
	}
	
	private void parseActorDeath() throws IncorrectTagException
	{
		if(this.tag.equals("ACTOR_DEATH"))
		{
			// sets aliasName AND questID
			setAliasName(parseAliasName());	
			setKillerID(parseKillerID());
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
		String searchRegex = "(\\[{1})([_a-zA-Z0-9]+)(\\s*)(\\<{1})alias ([_a-zA-Z0-9]+) on quest ([_a-zA-Z0-9\\s]+)(\\({1})([a-zA-Z0-9]+)(\\){1})(\\>{1})(\\]{1})"; 
		Pattern pattern = Pattern.compile(searchRegex);
	    Matcher matcher = pattern.matcher(line);
	    
		if(matcher.find())
		{
			form = line.substring(matcher.start(), matcher.end()).replaceFirst(searchRegex, "$5");
			// also set quest info while we're parsing this line
			this.questID = line.substring(matcher.start(), matcher.end()).replaceFirst(searchRegex, "$6$7$8$9");
			
			// chop off formID from line
			line = line.substring(matcher.end());	
		}
		
		return form;
	}
	
	private String parseKillerID()
	{
		String form = "";	
		// e.g. KILLER [Actor < (00000014)>]
		String searchRegex = "KILLER (\\[{1})([_a-zA-Z0-9]+)(\\s*)(\\<{1})(\\s*)(\\({1})([a-zA-Z0-9]+)(\\){1})(\\>{1})(\\]{1})"; 
		Pattern pattern = Pattern.compile(searchRegex);
	    Matcher matcher = pattern.matcher(line);
	    
		if(matcher.find())
		{
			form = line.substring(matcher.start(), matcher.end()).replaceFirst(searchRegex, "$7");			
			// chop off formID from line
			line = line.substring(matcher.end());	
		}
		
		return form;
	}
	
	@Override
	public void outputToCSV(PrintWriter writer) {
		super.outputToCSV(writer);
		writer.println("VICTIM_"+this.aliasName + "," + "KILLER_"+this.killerID);
	}
	
	@Override
	public String toString() {
		String str = "";
		str = str.concat(super.toString());
		str = str.concat("AliasName: " + this.aliasName + "\n");
		str = str.concat("QuestID: " + this.questID + "\n");
		str = str.concat("KillerID: " + this.killerID + "\n");
		str = str.concat("------------------\n");
		
		return str;
	}

}
