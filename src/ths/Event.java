package src.ths;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Event {
	
	protected String line;
	protected String tag;
	protected Date timestamp;
//	protected String[] tokens;
	
	public Event(String line)
	{
//		this.setTokens(line.split(" "));
		this.setLine(line);
		this.setTimestamp(parseDateTimeAsDate());
		this.setTag(parseTag());
	}
	
	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	// Intended to parse and set applicable
	// attributes for respective Event obj
	protected void parse() {
		// will likely always be overridden
	}
	
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	protected String parseTag()
	{
		String myTag = "";
//		int timeStampEnd = line.indexOf(']');
		// get first token after timestamp
		if(line.contains("log opened"))
		{
			myTag = "LOG_OPEN";
		}
		else if(line.contains("Log closed"))
		{
			myTag = "LOG_CLOSE";
		}
		else
		{
			String searchRegex = "([_A-Z]+)"; //e.g. PLAYER_GRAB (allcaps w/ underscores)
			Pattern pattern = Pattern.compile(searchRegex);
			Matcher matcher = pattern.matcher(line);
			if(matcher.find())
			{
				myTag = line.substring(matcher.start(), matcher.end()).replaceFirst(searchRegex, "$1");
				line = line.substring(matcher.end());
			}
		}
		
		return myTag;
	}
	
	protected String parseDateTimeAsString()
	{
		int beginIndex, endIndex;
		String timestampStr = "";
		beginIndex = line.indexOf('[') + 1;
		endIndex = line.indexOf(']');
		timestampStr = line.substring(beginIndex, endIndex);
		// remove timestamp from line
		line = line.substring(endIndex + 2); // account for end bracket+space
		return timestampStr;
	}
	
	protected Date parseDateTimeAsDate()
	{
		return convertDateTime(parseDateTimeAsString());	
	}
	
	protected Date convertDateTime(String dateString){
	    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy - hh:mm:ssaa");
	    Date convertedDate = new Date();
	    try {
	        convertedDate = dateFormat.parse(dateString);
	    } catch (ParseException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    return convertedDate;
	}
	
	@Override
	public String toString() {
		String str = "";
		str = str.concat("Tag: " + tag + "\n");
		str = str.concat("Timestamp: " + timestamp + "\n");
				
		return str;
	}
}
