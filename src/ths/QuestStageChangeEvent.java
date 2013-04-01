package src.ths;

import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuestStageChangeEvent extends Event {

	private String questID;
	private String questName;
	private int questStage;
	
	
	public QuestStageChangeEvent()
	{
		super("");
	}
	
	public QuestStageChangeEvent(String line) {
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
	
	public int getQuestStage() {
		return questStage;
	}

	public void setQuestStage(int questStage) {
		this.questStage = questStage;
	}

	@Override
	protected void parse() {
		super.parse();
		try {
			parseQuestStageChange();
		} catch (IncorrectTagException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void parseQuestStageChange() throws IncorrectTagException
	{
		if(this.tag.contains("QUEST_STAGE_CHANGE"))
		{			
//			System.out.println(parseQuestName());
			setQuestID(parseQuestID());
			setQuestName(parseQuestName());
			setQuestStage(parseQuestStage());
		}
		else
		{
			throw new IncorrectTagException();
		}
	}
	
	private String parseQuestID()
	{
		String form = "";		
		String searchRegex = "(\\[{1})([_a-zA-Z0-9]+)(\\s*)(\\<{1})([_a-zA-Z0-9\\s]+)(\\({1})([a-zA-Z0-9]+)(\\){1})(\\>{1})(\\]{1})"; // e.g. [ths1_CaptiveOrcQuestSCRIPT <ths1_CaptiveOrc (0201A9F8)>]
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
	
	private String parseQuestName()
	{
		String form = "";		
		String searchRegex = "(\\\"{1})(['a-zA-Z0-9\\s]+)(\\\"{1})"; //e.g. "Form Name"
		Pattern pattern = Pattern.compile(searchRegex);
	    Matcher matcher = pattern.matcher(line);
	    
		if(matcher.find())
		{
			form = line.substring(matcher.start(), matcher.end()).replaceFirst(searchRegex, "$2");
			// chop off formID from line
			line = line.substring(matcher.end());	
		}
		
		return form;
	}
	
	private int parseQuestStage()
	{
		int form = -1;		
		String searchRegex = "([0-9]+)"; //e.g. 3, 10, etc
		Pattern pattern = Pattern.compile(searchRegex);
	    Matcher matcher = pattern.matcher(line);
	    
		if(matcher.find())
		{
			form = Integer.parseInt(line.substring(matcher.start(), matcher.end()).replaceFirst(searchRegex, "$1"));
			// chop off formID from line
			line = line.substring(matcher.end());	
		}
		
		return form;
	}
	
	@Override
	public void outputToCSV(PrintWriter writer) {
		super.outputToCSV(writer);
		writer.println(this.questName + "," + this.questStage);
	}
	
	@Override
	public String toString() {
		String str = "";
		str = str.concat(super.toString());
		str = str.concat("QuestID: " + this.questID+ "\n");
		str = str.concat("QuestName: " + this.questName + "\n");	
		str = str.concat("QuestStage: " + this.questStage + "\n");		
		str = str.concat("------------------\n");
		
		return str;
		
	}
	
}
