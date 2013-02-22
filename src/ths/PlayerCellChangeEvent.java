package src.ths;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerCellChangeEvent extends Event {

	private String cellName;
	private String cellID;
	
	public PlayerCellChangeEvent() {
		super("");
	}
	
	public PlayerCellChangeEvent(String line) {
		super(line);
		parse();
	}
	
	public String getCellName() {
		return cellName;
	}

	public void setCellName(String cellName) {
		this.cellName = cellName;
	}

	public String getCellID() {
		return cellID;
	}

	public void setCellID(String cellID) {
		this.cellID = cellID;
	}
	
	@Override
	protected void parse() {
		super.parse();	
		try {
			parseCellChange();
		} catch (IncorrectTagException e) {
			e.printStackTrace();
		}
	}
	
	private void parseCellChange() throws IncorrectTagException
	{
		if(this.tag.equals("PLAYER_CELL_CHANGE"))
		{
			setCellID(parseCellID());
			setCellName(parseCellName());
		}
		else
		{
			throw new IncorrectTagException();
		}
	}
	
	private String parseCellID()
	{
		String form = "";		
		String searchRegex = "(\\[{1})Cell(\\s*)(\\<{1})([_a-zA-Z0-9\\s]+)(\\({1})([a-zA-Z0-9]+)(\\){1}\\>{1}\\]{1})"; // e.g. [Cell <Wilderness (02050A4C)>]
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
	
	private String parseCellName()
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
		str = str.concat("CellID: " + this.cellID + "\n");
		str = str.concat("CellName: " + this.cellName + "\n");	
		str = str.concat("------------------\n");
		
		return str;
	}

}
