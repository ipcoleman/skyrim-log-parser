package src.ths;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerDisplayCaseOpenEvent extends Event {

	private String displayCaseID;
	
	public PlayerDisplayCaseOpenEvent(String line) {
		super(line);
		parse();
	}

	public String getDisplayCaseID() {
		return displayCaseID;
	}

	public void setDisplayCaseID(String displayCaseID) {
		this.displayCaseID = displayCaseID;
	}

	@Override
	protected void parse() {
		super.parse();
		try {
			parseDisplayCaseOpen();
		} catch (IncorrectTagException e) {
			e.printStackTrace();
		}
	}
	
	private void parseDisplayCaseOpen() throws IncorrectTagException
	{
//		PLAYER_DISPLAY_CASE_OPEN [ths1_DisplayCaseScript < (02044E20)>]
		
		if(this.tag.equals("PLAYER_DISPLAY_CASE_OPEN"))
		{
			setDisplayCaseID(parseDisplayCaseID());
		}
		else
		{
			throw new IncorrectTagException();
		}
	}	
	
	private String parseDisplayCaseID()
	{
		String form = "";		
		String searchRegex = "(\\[{1})([_a-zA-Z0-9]+)(\\s*)(\\<{1})(\\s*)(\\({1})([a-zA-Z0-9]+)(\\){1}\\>{1}\\]{1})"; // e.g. [ths1_DisplayCaseScript < (02044E20)>]
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
	
	@Override
	public String toString() {
		String str = "";
		str = str.concat(super.toString());
		str = str.concat("DisplayCaseID: " + this.displayCaseID + "\n");
		str = str.concat("------------------\n");
		
		return str;
	}

}

