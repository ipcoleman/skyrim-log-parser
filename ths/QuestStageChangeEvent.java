package ths;

public class QuestStageChangeEvent extends Event {

	private String questID;
	private String questName;
	private int questStage;
	
	public QuestStageChangeEvent(String line) {
		super(line);
		// TODO Auto-generated constructor stub
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
		String questID = "";		
		int startIndex, endIndex;
		startIndex = line.indexOf('[');
		endIndex = line.indexOf(']') + 1;
		
//		System.out.println(this.line);
		
		if(startIndex >= 0)
		{
			String searchRegex = "(\\[{1})([a-zA-Z]+)(\\s*)(\\<{1})(\\s*)(\\({1})([a-zA-Z0-9]+)(\\){1}\\>{1}\\]{1})";
	//		searchIndex = line.indexOf(searchTerm);
			if(line.substring(startIndex, endIndex).matches(searchRegex))
			{
				startIndex = line.indexOf('<') + 1;
				endIndex = line.indexOf('>', startIndex);
				questID = line.substring(startIndex, endIndex);
				// chop off questName/ID from line
				line = line.substring(endIndex + 4);
			}
		}
		
		return questID;
	}
	
	private String parseQuestName()
	{
		String questName = "";		
		int startIndex, endIndex;
		startIndex = line.indexOf('[');
		endIndex = line.indexOf(']') + 1;
		
//		System.out.println(this.line);
		
		if(startIndex >= 0)
		{
			String searchRegex = "(\\[{1})([a-zA-Z]+)(\\s*)(\\<{1})(\\s*)(\\({1})([a-zA-Z0-9]+)(\\){1}\\>{1}\\]{1})";
	//		searchIndex = line.indexOf(searchTerm);
			if(line.substring(startIndex, endIndex).matches(searchRegex))
			{
				startIndex = line.indexOf('<') + 1;
				endIndex = line.indexOf('>', startIndex);
				questName = line.substring(startIndex, endIndex);
				// chop off questName/ID from line
				line = line.substring(endIndex + 4);
			}
		}
		
		return questName;
	}
	
	private int parseQuestStage()
	{
		int questStage = -1;	
		String questStageStr = "";
		int startIndex, endIndex;
		startIndex = line.indexOf('[');
		endIndex = line.indexOf(']') + 1;
		
//		System.out.println(this.line);
		
		if(startIndex >= 0)
		{
			String searchRegex = "(\\[{1})([a-zA-Z]+)(\\s*)(\\<{1})(\\s*)(\\({1})([a-zA-Z0-9]+)(\\){1}\\>{1}\\]{1})";
	//		searchIndex = line.indexOf(searchTerm);
			if(line.substring(startIndex, endIndex).matches(searchRegex))
			{
				startIndex = line.indexOf('<') + 1;
				endIndex = line.indexOf('>', startIndex);
				questStageStr = line.substring(startIndex, endIndex);
				questStage = Integer.parseInt(questStageStr);
				// chop off questName/ID from line
				line = line.substring(endIndex + 4);
			}
		}
		
		return questStage;
	}
	
	@Override
	public String toString() {
		String str = "";
		str = str.concat(super.toString());
		str = str.concat("Quest ID: " + this.getQuestID() + "\n");
		str = str.concat("Quest Name: " + this.getQuestName() + "\n");	
		str = str.concat("Quest Stage: " + this.getQuestStage() + "\n");		
		str = str.concat("------------------\n");
		
		return str;
		
	}
	
}
