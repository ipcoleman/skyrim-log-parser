package src.ths;

public class PlayerFightEvent extends Event {

	private String actorID;
	private String actorName;
	
	public PlayerFightEvent(String line) {
		super(line);
		// TODO Auto-generated constructor stub
	}
			
	public String getActorID() {
		return actorID;
	}

	public void setActorID(String actorID) {
		this.actorID = actorID;
	}

	public String getActorName() {
		return actorName;
	}

	public void setActorName(String actorName) {
		this.actorName = actorName;
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
