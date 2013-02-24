package src.ths;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.*;

public class PlayerMoveEvent extends Event {

	private Interval interval;
	private PlayerMoveType type;
	
	public enum PlayerMoveType
	{
		NONE, RUN, SPRINT, SNEAK;
	}
	
	public PlayerMoveEvent() {
		// TODO Auto-generated constructor stub
		super("");
	}
	
	public PlayerMoveEvent(String line)
	{
		super(line);
		parse();
	}

	public Interval getInterval() {
		return interval;
	}

	public void setInterval(Interval interval) {
		this.interval = interval;
	}

	public PlayerMoveType getType() {
		return type;
	}

	public void setType(PlayerMoveType type) {
		this.type = type;
	}
	
	private PlayerMoveType getMoveTypeFromString(String moveType)
	{
		return PlayerMoveType.valueOf(moveType.toUpperCase());
//		return moveType;
	}

	@Override
	protected void parse() {
		try {
			super.parse();
			parsePlayerMove();
		} catch (IncorrectTagException e) {
			e.printStackTrace();
		}
	}
	
	public void parsePlayerMove() throws IncorrectTagException
	{
		if(this.tag.equals("PLAYER_MOVE"))
		{
			this.type =  getMoveTypeFromString(parseMoveType());
			// TODO: get proper interval
			this.interval = new Interval(timestamp.getTime(), timestamp.getTime());
		}
		else
		{
			throw new IncorrectTagException();
		}
	}
	
	public String parseMoveType()
	{		
		String moveType = "";
		String searchRegex = "([a-zA-Z]+)"; //e.g. None, Run, Sneak, Sprint
		Pattern pattern = Pattern.compile(searchRegex);
		Matcher matcher = pattern.matcher(line);
		if(matcher.find())
		{
			moveType = line.substring(matcher.start(), matcher.end()).replaceFirst(searchRegex, "$1");
			line = line.substring(matcher.end());
		}
				
		return moveType;
	}
	
	@Override
	public String toString() {
		String str = "";
		str = str.concat(super.toString());
		str = str.concat("Interval: " + this.interval.toString() + "\n");
		str = str.concat("MoveType: " + this.type.toString() + "\n");
		str = str.concat("------------------\n");
		
		return str;
	}
}
