package ths;

public class EventFactory {

	private enum EventType
	{
		LOG_OPEN, PLAYER_MOVE, PLAYER_ITEM_OBTAIN, PLAYER_POV_CHANGE, PLAYER_TRIGGER_ENTER, PLAYER_TRIGGER_LEAVE,
		PLAYER_WEAPON_RIGHT, PLAYER_WEAPON_LEFT, PLAYER_WEAPON_WITHDRAWN, PLAYER_ITEM_REMOVED, PLAYER_CELL_CHANGE,
		PLAYER_WORLDSPACE_CHANGE, PLAYER_FIGHT, PLAYER_DIALOGUE, QUEST_STAGE_CHANGE, PLAYER_DISPLAY_CASE_OPEN,
		PLAYER_ACTOR_VIEW_GAIN, PLAYER_ACTOR_VIEW_LOSS, PLAYER_DISTANCE_ACTOR, PLAYER_BOOK_READ, QUEST_COMPLETED,
		LOG_CLOSE
	}
	
	public EventFactory() {
		// TODO Auto-generated constructor stub
	}
	
	public Event makeEvent(String line)
	{
		Event e = new Event(line);
		EventType eType = EventType.valueOf(e.getTag().toUpperCase());
		
		switch(eType)
		{
			case LOG_OPEN:
				e = new Event(line);
				e.setTag("LOG_OPEN");
				break;
			case LOG_CLOSE:
				e = new Event(line);
				e.setTag("LOG_CLOSE");
				break;
			case PLAYER_MOVE:
				e = new PlayerMoveEvent(line);
				break;
			case PLAYER_ITEM_OBTAIN:
				e = new PlayerItemObtainEvent(line);
				break;
			case PLAYER_POV_CHANGE:
				e = new PlayerPOVChangeEvent(line);
				break;
			case PLAYER_TRIGGER_ENTER:
			case PLAYER_TRIGGER_LEAVE:
				e = new PlayerTriggerEvent(line);
				break;
			case PLAYER_WEAPON_RIGHT:
			case PLAYER_WEAPON_LEFT:
				e = new PlayerWeaponEvent(line);
				break;
			default:
				e = null;
				break;
		}
		
		return e;
	}
}
