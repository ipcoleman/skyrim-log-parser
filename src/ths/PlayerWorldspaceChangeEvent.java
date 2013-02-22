package src.ths;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerWorldspaceChangeEvent extends Event {

	private String worldspaceName;
	private String worldspaceID;
	
	public PlayerWorldspaceChangeEvent()
	{
		super("");
	}
	
	public PlayerWorldspaceChangeEvent(String line) {
		super(line);
		parse();
	}
	
	public String getWorldspaceName() {
		return worldspaceName;
	}

	public void setWorldspaceName(String worldspaceName) {
		this.worldspaceName = worldspaceName;
	}

	public String getWorldspaceID() {
		return worldspaceID;
	}

	public void setWorldspaceID(String worldspaceID) {
		this.worldspaceID = worldspaceID;
	}

	@Override
	protected void parse() {	
		super.parse();
		try {
			parseWorldspaceChange();
		} catch (IncorrectTagException e) {
			e.printStackTrace();
		}
	}
	
	private void parseWorldspaceChange() throws IncorrectTagException
	{
		if(this.tag.equals("PLAYER_WORLDSPACE_CHANGE"))
		{
			setWorldspaceID(parseWorldspaceID());
			setWorldspaceName(parseWorldspaceName());
		}
		else
		{
			throw new IncorrectTagException();
		}
	}
	
	private String parseWorldspaceID()
	{
		String form = "";		
		String searchRegex = "(\\[{1})WorldSpace(\\s*)(\\<{1})([_a-zA-Z0-9\\s]+)(\\({1})([a-zA-Z0-9]+)(\\){1}\\>{1}\\]{1})"; // e.g. [Cell <Wilderness (02050A4C)>]
		Pattern pattern = Pattern.compile(searchRegex);
	    Matcher matcher = pattern.matcher(line);
	    
		if(matcher.find())
		{
			form = line.substring(matcher.start(), matcher.end()).replaceFirst(searchRegex, "$6");
			// chop off formID from line
			line = line.substring(matcher.end());	
		}
		
		return form;
	}
	
	private String parseWorldspaceName()
	{
		String form = "";		
		String searchRegex = "(\\\"{1})([_a-zA-Z0-9'\\s]*)(\\\"{1})"; //e.g. "Cell's Name"
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
	
	@Override
	public String toString() {
		String str = "";
		str = str.concat(super.toString());
		str = str.concat("WorldspaceID: " + this.worldspaceID + "\n");
		str = str.concat("WorldspaceName: " + this.worldspaceName + "\n");	
		str = str.concat("------------------\n");
		
		return str;
	}

}
