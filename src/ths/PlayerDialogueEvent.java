package src.ths;

import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerDialogueEvent extends Event {

	private String topicID;
	private String actorID;
	private String questName;
	private String questID;
//	private String actorName;
	
	public PlayerDialogueEvent()
	{
		super("");
	}
	
	public PlayerDialogueEvent(String line) {
		super(line);
		parse();
	}
	
	public String getActorID() {
		return actorID;
	}

	public void setActorID(String actorID) {
		this.actorID = actorID;
	}

//	public String getActorName() {
//		return actorName;
//	}
//
//	public void setActorName(String actorName) {
//		this.actorName = actorName;
//	}

	public String getTopicID() {
		return topicID;
	}

	public void setTopicID(String topicID) {
		this.topicID = topicID;
	}

	public String getQuestName() {
		return questName;
	}

	public void setQuestName(String questName) {
		this.questName = questName;
	}

	public String getQuestID() {
		return questID;
	}

	public void setQuestID(String questID) {
		this.questID = questID;
	}

	@Override
	protected void parse() {
		//PLAYER_DIALOGUE [TIF__01009353 <topic info 02009353 on quest ths1_MissingDog (02009344)>] NPC_SPEAKER [Actor < (02089359)>]
		super.parse();
		try {
			parsePlayerDialogue();
		} catch (IncorrectTagException e) {
			e.printStackTrace();
		}
	}
	
	private void parsePlayerDialogue() throws IncorrectTagException
	{
		if(this.tag.equals("PLAYER_DIALOGUE"))
		{
			setTopicID(parseTopicID());
			setActorID(parseActorID());
		}
		else
		{
			throw new IncorrectTagException();
		}
	}
	
	private String parseTopicID()
	{
		String form = "";		
		String searchRegex = "(\\[{1})TIF__([a-zA-Z0-9]+)(\\s*)(\\<{1})topic info(\\s*)([a-zA-Z0-9]+)(\\s+)on quest(\\s+)([_a-zA-Z0-9\\s]+)(\\({1})([a-zA-Z0-9]+)(\\){1}\\>{1}\\]{1})"; // e.g. [TIF__01009353 <topic info 02009353 on quest ths1_MissingDog (02009344)>]
		Pattern pattern = Pattern.compile(searchRegex);
	    Matcher matcher = pattern.matcher(line);
	    
		if(matcher.find())
		{
			form = line.substring(matcher.start(), matcher.end()).replaceFirst(searchRegex, "$6");
			// also set quest info while we're parsing this line
			this.questName = line.substring(matcher.start(), matcher.end()).replaceFirst(searchRegex, "$9");
			this.questID = line.substring(matcher.start(), matcher.end()).replaceFirst(searchRegex, "$11");
			
			// chop off formID from line
			line = line.substring(matcher.end());	
		}
		
		return form;
	}
	
	private String parseActorID()
	{
		String form = "";		
		String searchRegex = "(\\[{1})Actor(\\s*)(\\<{1})(\\s*)(\\({1})([a-zA-Z0-9]+)(\\){1}\\>{1}\\]{1})"; // e.g. [Actor < (02089359)>]
		Pattern pattern = Pattern.compile(searchRegex);
	    Matcher matcher = pattern.matcher(line);
	    
		if(matcher.find())
		{
			form = line.substring(matcher.start(), matcher.end()).replaceFirst(searchRegex, "$6");
			// chop off formID from line
			line = line.substring(matcher.end());	
		}
		
		return form;
	}
	
	@Override
	public void outputToCSV(PrintWriter writer) {
		super.outputToCSV(writer);
		writer.println("ACTOR_DIALOGUE_"+this.actorID + "," + "TOPIC_"+this.topicID);
	}
	
	@Override
	public String toString() {
		String str = "";
		str = str.concat(super.toString());
		str = str.concat("TopicID: " + this.topicID + "\n");		
		str = str.concat("QuestName: " + this.questName + "\n");
		str = str.concat("QuestID: " + this.questID + "\n");
		str = str.concat("ActorID: " + this.actorID + "\n");
		str = str.concat("------------------\n");
		
		return str;
	}

}
