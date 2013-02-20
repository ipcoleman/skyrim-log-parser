package src.ths;

public class QuestCompletedEvent extends Event {

	private String questID;
	private String questName;
	
	public QuestCompletedEvent(String line) {
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

	@Override
	protected void parse() {
		// TODO Auto-generated method stub
		super.parse();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "";
	}

}

