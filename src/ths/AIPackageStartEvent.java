package src.ths;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AIPackageStartEvent extends Event {

	private String aliasID;
	private String packageID;
	
	public AIPackageStartEvent(String line) {
		super(line);
		// TODO Auto-generated constructor stub
	}

	public String getAliasID() {
		return aliasID;
	}

	public void setAliasID(String aliasID) {
		this.aliasID = aliasID;
	}

	public String getPackageID() {
		return packageID;
	}

	public void setPackageID(String packageID) {
		this.packageID = packageID;
	}

	@Override
	protected void parse() {
		super.parse();
		try {
			parseAIPackageStart();
		} catch (IncorrectTagException e) {
			e.printStackTrace();
		}
	}
	
	private void parseAIPackageStart() throws IncorrectTagException
	{
		//AI_PACKAGE_START [ths1_PackageLogSCRIPT <alias Barrian on quest ths1_BarrianBookQuest (0204540C)>]PACKAGE [Package < (0205845A)>]
		
		if(this.tag.equals("AI_PACKAGE_START"))
		{
			setAliasID(parseAliasID());
			setPackageID(parsePackageID());			
		}
		else
		{
			throw new IncorrectTagException();
		}
	}
	
	private String parseAliasID()
	{
		String aID = "";		
		// e.g. [ths1_PackageLogSCRIPT <alias Barrian on quest ths1_BarrianBookQuest (0204540C)>]
		String searchRegex = "(\\[{1})([_a-zA-Z0-9\\s]+)(\\<{1})([_a-zA-Z0-9\\s]*)(\\({1})([a-zA-Z0-9]+)(\\){1}\\>{1}\\]{1})"; 
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
	
	private String parsePackageID()
	{

		String pID = "";		
		String searchRegex = "(\\[{1})PACKAGE(\\s*)(\\<{1})(\\s*)(\\({1})([a-zA-Z0-9]+)(\\){1}\\>{1}\\]{1})"; 	// e.g., PACKAGE [Package < (0003EAB9)>]
		Pattern pattern = Pattern.compile(searchRegex);
	    Matcher matcher = pattern.matcher(line);
	    
		if(matcher.find())
		{
			pID = line.substring(matcher.start(), matcher.end()).replaceFirst(searchRegex, "$7");
			// chop off formID from line
			line = line.substring(matcher.end());	
		}
		
		return pID;
	}
	
	@Override
	public String toString() {
		String str = "";
		str = str.concat(super.toString());
		str = str.concat("AliasID: " + this.aliasID + "\n");
		str = str.concat("PackageID: " + this.packageID + "\n");
		str = str.concat("------------------\n");
		
		return str;
	}

}


