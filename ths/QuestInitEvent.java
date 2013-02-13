package ths;

public class QuestInitEvent extends Event {

	private String questName;
	
	public QuestInitEvent()
	{
		super("");
	}
	
	public QuestInitEvent(String line) {
		super(line);
		parse();		
	}
	
	public String getQuestName() {
		return questName;
	}
	
	public void setQuestName(String questName) {
		this.questName = questName;
	}
	
	@Override
	protected void parse() {
//		super.parse();
		try {
			parseQuest();
		} catch (IncorrectTagException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void parseQuest() throws IncorrectTagException
	{
		if(this.tag.contains("QUEST_INIT"))
		{			
//			System.out.println(parseQuestName());
			setQuestName(parseQuestName());
		}
		else
		{
			throw new IncorrectTagException();
		}
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
	
	@Override
	public String toString() {
		String str = "";
		str = str.concat(super.toString());
		str = str.concat("Quest Name: " + this.getQuestName() + "\n");		
		str = str.concat("------------------\n");
		
		return str;
		
	}

}
