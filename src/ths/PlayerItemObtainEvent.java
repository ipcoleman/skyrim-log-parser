package src.ths;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// e.g. [11/08/2012 - 04:07:19PM] PLAYER_ITEM_OBTAIN [Form < (000139B7)>] [ObjectReference < (020711F4)>] "Daedric Greatsword" COUNT 1 CONTAINER None

public class PlayerItemObtainEvent extends Event {

	private String formID;
	private String objRef;
	private String formName;
	private int count;
	private String prevContainer;
		
	public PlayerItemObtainEvent() {
		super("");
	}
	
	public PlayerItemObtainEvent(String line)
	{
		super(line);
		parse();
	}
	
	public String getFormID() {
		return formID;
	}

	public void setFormID(String formID) {
		this.formID = formID;
	}

	public String getObjRef() {
		return objRef;
	}

	public void setObjRef(String objRef) {
		this.objRef = objRef;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getPrevContainer() {
		return prevContainer;
	}

	public void setPrevContainer(String prevContainer) {
		this.prevContainer = prevContainer;
	}

	@Override
	protected void parse() {
		super.parse();
		try {
			parsePlayerItemObtain();
		} catch (IncorrectTagException e) {
			e.printStackTrace();
		}
	}
	
	private void parsePlayerItemObtain() throws IncorrectTagException
	{
		if(this.tag.equals("PLAYER_ITEM_OBTAIN"))
		{
			setFormID(parseFormID());
			setObjRef(parseObjRef());
			setFormName(parseFormName());
			setCount(parseCount());
			setPrevContainer(parsePrevContainer());
		}
		else
		{
			throw new IncorrectTagException();
		}
	}
	
	private String parseFormID()
	{
		String form = "";		
		String searchRegex = "(\\[{1})([a-zA-Z]+)(\\s*)(\\<{1})(\\s*)(\\({1})([a-zA-Z0-9]+)(\\){1}\\>{1}\\]{1})"; // e.g. [Form < (02050A4C)>]
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
	
	private String parseObjRef()
	{
		String ref = "";		
		String nullStr = "None";
		String searchRegex = nullStr + "|(\\[{1})([a-zA-Z]+)(\\s*)(\\<{1})(\\s*)(\\({1})([a-zA-Z0-9]+)(\\){1}\\>{1}\\]{1})"; // e.g. [ObjectReference < (02039836)>]
		Pattern pattern = Pattern.compile(searchRegex);
	    Matcher matcher = pattern.matcher(line);

	    if(matcher.find())
	    {
    		/* check if matched string is length of nullStr */
	    	if((matcher.end() - matcher.start()) == nullStr.length())
	    	{
	    		ref = "None";
	    		line = line.substring(ref.length()); // start line at end of 'None'	
	    	}
	    	else 
	    	{
	    		ref = line.substring(matcher.start(), matcher.end()).replaceFirst(searchRegex, "$7");
				line = line.substring(matcher.end());	
	    	}
	    }
		
		return ref;
	}
	
	private String parseFormName()
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

	private int parseCount()
	{
		int myCount = -1;
		String searchRegex = "COUNT(\\s+)([0-9]+)";
		Pattern pattern = Pattern.compile(searchRegex);
		Matcher matcher = pattern.matcher(line);

		if(matcher.find())
		{
			myCount = Integer.parseInt(line.substring(matcher.start(), matcher.end()).replaceFirst(searchRegex, "$2"));
			line = line.substring(matcher.end());
		}
		
		return myCount;
	}
	
	private String parsePrevContainer()
	{
		String pCont = "";
		String nullStr = "None";
		String searchRegex = nullStr + "|(\\[{1})([a-zA-Z]+)(\\s*)(\\<{1})(\\s*)(\\({1})([a-zA-Z0-9]+)(\\){1}\\>{1}\\]{1})"; // e.g. [ObjectReference < (02039836)>]
		Pattern pattern = Pattern.compile(searchRegex);
	    Matcher matcher = pattern.matcher(line);

	    if(matcher.find())
	    {
    		/* check if matched string is length of nullStr */
	    	if((matcher.end() - matcher.start()) == nullStr.length())
	    	{
	    		pCont = "None";
	    		line = line.substring(pCont.length()); // start line at end of 'None'	
	    	}
	    	else 
	    	{
	    		pCont = line.substring(matcher.start(), matcher.end()).replaceFirst(searchRegex, "$7");
				line = line.substring(matcher.end());	
	    	}
	    }
	
		return pCont;
	}
	
	@Override
	public String toString() {
		String str = "";
		str = str.concat(super.toString());
		str = str.concat("FormID: " + this.formID + "\n");
		str = str.concat("ObjRef: " + this.objRef + "\n");
		str = str.concat("FormName: " + this.formName + "\n");
		str = str.concat("Count: " + this.count + "\n");
		str = str.concat("PrevContainer: " + this.prevContainer + "\n");
		str = str.concat("------------------\n");
		
		return str;
	}
	
}
