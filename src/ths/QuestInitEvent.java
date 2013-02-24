package src.ths;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuestInitEvent extends Event {

	private String questID;
	private String questName;
	
	public QuestInitEvent() {
		super("");			
	}
	
	public QuestInitEvent(String line) {
		super(line);	
		parse();
	}
	
	public String getQuestID() {
		return questID;
	}
	
	public void setQuestID(String questID) {
		this.questID = questID;
	}
	
	public String getQuestName() {
		return questName;
	}
	
	public void setQuestName(String questName) {
		this.questName = questName;
	}
	
	@Override
	protected void parse() {
		super.parse();
		try {
			parseQuestInit();
		} catch (IncorrectTagException e) {
			e.printStackTrace();
		}
	}
	
	private void parseQuestInit() throws IncorrectTagException
	{
		if(this.tag.equals("QUEST_INIT"))
		{
			setQuestID(parseQuestID());
			setQuestName(parseQuestName());
		}
		else
		{
			throw new IncorrectTagException();
		}
	}
	
	private String parseQuestID()
	{
		String form = "";		
		String searchRegex = "(\\[{1})([_a-zA-Z0-9\\s]+)(\\<{1})([_a-zA-Z0-9\\s]+)(\\({1})([a-zA-Z0-9]+)(\\){1})(\\>{1})(\\]{1})"; // e.g. [ths1_CCQScript <ths1_CapturedCatQUEST (0203B916)>]
		Pattern pattern = Pattern.compile(searchRegex);
	    Matcher matcher = pattern.matcher(line);
	    
		if(matcher.find())
		{
			form = line.substring(matcher.start(), matcher.end()).replaceFirst(searchRegex, "$4$5$6$7");
			// chop off formID from line
			line = line.substring(matcher.end());	
		}
		
		return form;
	}
	
	private String parseQuestName()
	{
		String fName = "";
		String searchRegex = "(\\\"{1})([,'_a-zA-Z0-9\\s]+)(\\\"{1})"; //e.g. "Form Name"
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
		str = str.concat("QuestName: " + this.questName + "\n");		
		str = str.concat("------------------\n");
		
		return str;
		
	}

}
