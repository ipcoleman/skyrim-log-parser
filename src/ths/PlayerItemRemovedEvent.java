package src.ths;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerItemRemovedEvent extends Event {

	private String formID;
	private String objRef;
	private String formName;
	private int count;
	private String prevContainer;
	
	public PlayerItemRemovedEvent(String line) {
		super(line);
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
			parsePlayerItemRemoved();
		} catch (IncorrectTagException e) {
			e.printStackTrace();
		}
	}
	
	private void parsePlayerItemRemoved() throws IncorrectTagException
	{
		// [11/08/2012 - 04:11:13PM] PLAYER_ITEM_OBTAIN [Form < (0004DA00)>] [ObjectReference < (02071201)>] "Fly Amanita" COUNT 1 CONTAINER None
		// [11/08/2012 - 04:10:42PM] PLAYER_ITEM_REMOVED [Form < (00064B2F)>] [ObjectReference < (020711CC)>] "Green Apple" COUNT 1 CONTAINER None
		
		if(this.tag.equals("PLAYER_ITEM_REMOVED"))
		{
			setFormID(parseFormID());
			System.out.println(formID);
			setObjRef(parseObjRef());
			System.out.println(objRef);
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
