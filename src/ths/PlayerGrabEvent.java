package src.ths;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerGrabEvent extends Event {

	private String objRefID;
	private String objName;
	
	public PlayerGrabEvent(String line) {
		super(line);
		parse();
	}

	public String getObjRefID() {
		return objRefID;
	}

	public void setObjRefID(String objRefID) {
		this.objRefID = objRefID;
	}

	public String getObjName() {
		return objName;
	}

	public void setObjName(String objName) {
		this.objName = objName;
	}
	
	@Override
	protected void parse() {
		// TODO Auto-generated method stub
		super.parse();
		try {
			parsePlayerGrab();
		} catch (IncorrectTagException e) {
			e.printStackTrace();
		}
	}
	
	private void parsePlayerGrab() throws IncorrectTagException
	{
		if(this.tag.equals("PLAYER_GRAB"))
		{
			setObjRefID(parseObjRefID());
			setObjName(parseObjName());
		}
		else
		{
			throw new IncorrectTagException();
		}
	}	
	
	private String parseObjRefID()
	{
		String form = "";		
		String searchRegex = "(\\[{1})([a-zA-Z0-9\\s]+)(\\<{1})(\\s*)(\\({1})([a-zA-Z0-9]+)(\\){1})(\\>{1})(\\]{1})"; // e.g. [ObjectReference < (0207120C)>]
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
	
	private String parseObjName()
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
	
	@Override
	public String toString() {
		String str = "";
		str = str.concat(super.toString());
		str = str.concat("ObjRefID: " + this.objRefID + "\n");
		str = str.concat("ObjName: " + this.objName + "\n");
		str = str.concat("------------------\n");
		
		return str;
	}

}
