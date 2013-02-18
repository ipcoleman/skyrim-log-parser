package ths;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActorMoveToPlayerEvent extends Event {

	private String actorID;
	private String actorName;
	
	public ActorMoveToPlayerEvent(String line) {
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
		super.parse();
		try {
			parseActorMoveToPlayer();
		} catch (IncorrectTagException e) {
			e.printStackTrace();
		}
	}
	
	private void parseActorMoveToPlayer() throws IncorrectTagException
	{
		if(this.tag.equals("ACTOR_MOVE_TO_PLAYER"))
		{
			setActorID(parseActorID());
			setActorName(parseActorName());
		}
		else
		{
			throw new IncorrectTagException();
		}
	}
	
	private String parseActorID()
	{
		String aID = "";
		String searchRegex = "(\\[{1})([a-zA-Z]+)(\\s*)(\\<{1})(\\s*)(\\({1})([a-zA-Z0-9]+)(\\){1}\\>{1}\\]{1})"; // e.g. [Form < (02050A4C)>]
		Pattern pattern = Pattern.compile(searchRegex);
	    Matcher matcher = pattern.matcher(line);
	    
		if(matcher.find())
		{
			aID = line.substring(matcher.start(), matcher.end()).replaceFirst(searchRegex, "$7");
			// chop off formID from line
			line = line.substring(matcher.end());	
		}
		
		return aID;
	}
	
	private String parseActorName()
	{
		String aName = "";
		String searchRegex = "(\\\"{1})([a-zA-Z0-9\\s]+)(\\\"{1})"; //e.g. "Form Name"
		Pattern pattern = Pattern.compile(searchRegex);
	    Matcher matcher = pattern.matcher(line);
	    
		if(matcher.find())
		{
			aName = line.substring(matcher.start(), matcher.end()).replaceFirst(searchRegex, "$7");
			// chop off formID from line
			line = line.substring(matcher.end());	
		}
		
		return aName;
	}
	
	
	@Override
	public String toString() {
		String str = "";
		str = str.concat(super.toString());
		str = str.concat("ActorID: " + this.actorID + "\n");
		str = str.concat("ActorName: " + this.actorName + "\n");
		str = str.concat("------------------\n");
		
		return str;
	}

}


