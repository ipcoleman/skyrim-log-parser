package ths;

public class QuestInitEvent extends Event {

	private String questName;
	
	public QuestInitEvent(String line) {
		super("");
		
		
	}
	
	public String getQuestName() {
		return questName;
	}
	
	public void setQuestName(String questName) {
		this.questName = questName;
	}
	
	@Override
	public String toString() {
		String str = "";
		str = str.concat(super.toString());
		str = str.concat("Quest Name: " + this.questName + "\n");		
		str = str.concat("------------------\n");
		
		return str;
		
	}

}
