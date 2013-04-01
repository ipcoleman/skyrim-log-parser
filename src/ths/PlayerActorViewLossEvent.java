package src.ths;

import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerActorViewLossEvent extends Event {

	private String actorID;
	private String actorName;
	
	public PlayerActorViewLossEvent() {
		super("");
	}
	
	public PlayerActorViewLossEvent(String line) {
		super(line);
		parse();
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
			parsePlayerActorViewLoss();
		} catch (IncorrectTagException e) {
			e.printStackTrace();
		}
	}
	
	private void parsePlayerActorViewLoss() throws IncorrectTagException
	{
		if(this.tag.equals("PLAYER_ACTOR_VIEW_LOSS"))
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
		String form = "";		
		String searchRegex = "(\\[{1})([a-zA-Z]+)(\\s*)(\\<{1})(\\s*)(\\({1})([a-zA-Z0-9]+)(\\){1}\\>{1}\\]{1})"; // e.g. [ObjectReference < (02089359)>]
		Pattern pattern = Pattern.compile(searchRegex);
	    Matcher matcher = pattern.matcher(line);
	    
		if(matcher.find())
		{
			form = line.substring(matcher.start(), matcher.end()).replaceFirst(searchRegex, "$7");
			// chop off formID from line
			line = line.substring(matcher.end());	
		}
		
		return form;
	}
	
	private String parseActorName()
	{
		String fName = "";
		String searchRegex = "(\\\"{1})(['a-zA-Z0-9\\s]+)(\\\"{1})"; //e.g. "Form Name"
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
	public void outputToCSV(PrintWriter writer) {
		super.outputToCSV(writer);
		writer.println("VIEW_LOSS," + this.actorName);
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
