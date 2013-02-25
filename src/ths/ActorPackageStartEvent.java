package src.ths;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActorPackageStartEvent extends Event {

	private String aliasID;
	private String questID;
	private String packageID;
	
	public ActorPackageStartEvent(String line) {
		super(line);
		parse();
	}

	public String getAliasID() {
		return aliasID;
	}

	public void setAliasID(String aliasID) {
		this.aliasID = aliasID;
	}

	public String getQuestID() {
		return questID;
	}

	public void setQuestID(String questID) {
		this.questID = questID;
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
			parseActorPackageStart();
		} catch (IncorrectTagException e) {
			e.printStackTrace();
		}
	}
	
	private void parseActorPackageStart() throws IncorrectTagException
	{
		//ACTOR_PACKAGE_START [ths1_MDQSeamusAliasSCRIPT <alias Seamus on quest ths1_MissingDog (02009344)>] "Seamus" : "[PF_ths1_SeamusFollowerPackag_0201C01E < (0201C01E)>]"
		
		if(this.tag.equals("ACTOR_PACKAGE_START"))
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
		// e.g. [ths1_MDQSeamusAliasSCRIPT <alias Seamus on quest ths1_MissingDog (02009344)>]
		String searchRegex = "(\\[{1})([_a-zA-Z0-9\\s]+)(\\<{1})alias([_a-zA-Z0-9\\s]+)on quest([_a-zA-Z0-9\\s]+)(\\({1})([a-zA-Z0-9]+)(\\){1})(\\>{1})(\\]{1})"; 
		Pattern pattern = Pattern.compile(searchRegex);
	    Matcher matcher = pattern.matcher(line);
	    
		if(matcher.find())
		{
			// go ahead and set questID
			aID = line.substring(matcher.start(), matcher.end()).replaceFirst(searchRegex, "$4");
			setQuestID(line.substring(matcher.start(), matcher.end()).replaceFirst(searchRegex, "$5$6$7$8"));			
			// chop off formID from line
			line = line.substring(matcher.end());	
		}
		
		return aID;
	}
	
	private String parsePackageID()
	{

		String pID = "";		
		String searchRegex = ":(\\s*)(\\\"{1})(\\[{1})([_a-zA-Z0-9\\s]+)(\\<{1})(\\s*)(\\({1})([a-zA-Z0-9]+)(\\){1})(\\>{1})(\\]{1})"; 	// e.g., : "[PF_ths1_SeamusFollowerPackag_0201C01E < (0201C01E)>]"
		Pattern pattern = Pattern.compile(searchRegex);
	    Matcher matcher = pattern.matcher(line);
	    
		if(matcher.find())
		{
			pID = line.substring(matcher.start(), matcher.end()).replaceFirst(searchRegex, "$4");
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
		str = str.concat("QuestID: " + this.questID + "\n");
		str = str.concat("PackageID: " + this.packageID + "\n");
		str = str.concat("------------------\n");
		
		return str;
	}

}



