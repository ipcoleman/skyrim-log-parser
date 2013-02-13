package ths;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Event {
	
	protected String line;
	protected String tag;
	protected Date timestamp;
//	protected String[] tokens;
	
	public Event(String line)
	{
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
	abstract void parse();
	
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
			myTag = line.split(" ")[0];
			// remove tag from line
			if(line.split(" ").length > 1) // only cut off tag from line if more data after tag
				line = line.substring(line.indexOf(myTag) + myTag.length() + 1);
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
		str.concat("Tag: " + this.tag + "\n");
		str.concat("Timestamp: " + this.timestamp + "\n");
		str.concat("------------------");
				
		return str;
	}
}
