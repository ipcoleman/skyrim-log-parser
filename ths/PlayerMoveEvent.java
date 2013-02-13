package ths;

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
		return PlayerMoveType.valueOf(parseMoveType().toUpperCase());
//		return moveType;
	}

	@Override
	protected void parse() {
		// TODO Auto-generated method stub
		try {
			super.parse();
			parsePlayerMove();
		} catch (IncorrectTagException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void parsePlayerMove() throws IncorrectTagException
	{
		if(this.tag.equals("PLAYER_MOVE"))
		{
//			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy - hh:mm:ssaa");
//			Date timestamp = parseDateTimeAsDate();
			
			/*if(prevMoveDate == null)
				prevMoveDate = timestamp;*/
			this.type =  getMoveTypeFromString(parseMoveType());
			// TODO: get proper interval
			this.interval = new Interval(timestamp.getTime(), timestamp.getTime());
//			PlayerMoveEvent moveTypeObj = new PlayerMoveEvent(prevMoveDate.getTime(), timestamp.getTime(), typeStr);
//			System.out.println(typeStr + " " + moveTypeObj.getInterval().toDuration().getStandardSeconds());
//			prevMoveDate = timestamp;
		}
		else
		{
			throw new IncorrectTagException();
		}
	}
	
	public String parseMoveType()
	{		
		String moveType = line.split(" ")[0];
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
