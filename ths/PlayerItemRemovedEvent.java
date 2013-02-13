package ths;

public class PlayerItemRemovedEvent extends Event {

	public PlayerItemRemovedEvent(String line) {
		super(line);
	}

	@Override
	void parse() {
		
	}
	
	@Override
	public String toString() {
		String str = "";
		str = str.concat(super.toString());
		str = str.concat("Type: " + this.type.toString() + "\n");
		str = str.concat("TriggerID " + this.triggerID + "\n");
		str = str.concat("------------------\n");
		
		return str;
	}
}
