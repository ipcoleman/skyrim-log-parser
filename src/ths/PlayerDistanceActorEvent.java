package src.ths;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerDistanceActorEvent extends Event {

	private String actorID;
	private String actorName;
	private double distance;
	
	public PlayerDistanceActorEvent(String line) {
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

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	@Override
	protected void parse() {
		super.parse();
		try {
			parsePlayerDistanceActor();
		} catch (IncorrectTagException e) {
			e.printStackTrace();
		}
	}
	
	private void parsePlayerDistanceActor() throws IncorrectTagException
	{
		if(this.tag.equals("PLAYER_DISTANCE_ACTOR"))
		{
			setActorID(parseActorID());
			setActorName(parseActorName());
			setDistance(parseDistance());		
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
		String searchRegex = "(\\\"{1})([a-zA-Z0-9\\s]+)(\\\"{1})"; //e.g. "Form Name"
		Pattern pattern = Pattern.compile(searchRegex);
		Matcher matcher = pattern.matcher(line);
		if(matcher.find())
		{
			fName = line.substring(matcher.start(), matcher.end()).replaceFirst(searchRegex, "$2");
			line = line.substring(matcher.end());
		}
				
		return fName;
	}
	
	private double parseDistance()
	{
		double fName = 0.0;
		String searchRegex = "([0-9]+)([.]{1})([0-9]+)"; //e.g. 1160.196533
		Pattern pattern = Pattern.compile(searchRegex);
		Matcher matcher = pattern.matcher(line);
		if(matcher.find())
		{
			fName = Double.parseDouble(line.substring(matcher.start(), matcher.end()).replaceFirst(searchRegex, "$1$2$3"));
			fName = Math.round(fName);
			line = line.substring(matcher.end());
		}
				
		return fName;
	}
	
	@Override
	public String toString() {
		String str = "";
		str = str.concat(super.toString());
		str = str.concat("ActorID: " + this.actorID + "\n");
		str = str.concat("ActorName: " + this.actorName + "\n");
		str = str.concat("Distance: " + this.distance + "\n");
		str = str.concat("------------------\n");
		
		return str;
	}

}

