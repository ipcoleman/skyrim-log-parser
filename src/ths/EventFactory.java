package src.ths;

public class EventFactory {

	private enum EventType
	{
		LOG_OPEN, PLAYER_MOVE, PLAYER_ITEM_OBTAIN, PLAYER_POV_CHANGE, PLAYER_TRIGGER_ENTER, PLAYER_ENTER_TRIGGER, PLAYER_TRIGGER_LEAVE,
		PLAYER_WEAPON_RIGHT, PLAYER_WEAPON_LEFT, PLAYER_WEAPON_WITHDRAWN, PLAYER_ITEM_REMOVED, PLAYER_CELL_CHANGE,
		PLAYER_WORLDSPACE_CHANGE, PLAYER_FIGHT, PLAYER_DIALOGUE, QUEST_STAGE_CHANGE, PLAYER_DISPLAY_CASE_OPEN,
		PLAYER_ACTOR_VIEW_GAIN, PLAYER_ACTOR_VIEW_LOSS, PLAYER_DISTANCE_ACTOR, PLAYER_BOOK_READ, PLAYER_READ_BOOK, QUEST_COMPLETED,
		LOG_CLOSE, QUEST_INIT, AI_PACKAGE_START, ACTOR_PACKAGE_START, ACTOR_MOVE_TO_PLAYER,
		PLAYER_DOOR_OPEN, DOOR_OPEN, ACTOR_DOOR_OPEN, PLAYER_DOOR_ACTIVATE, PLAYER_GRAB, ACTOR_DEATH, PLAYER_HIT,
		ACTOR_HIT
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
			case PLAYER_ITEM_REMOVED:
				e = new PlayerItemRemovedEvent(line);
				break;
			case PLAYER_POV_CHANGE:
				e = new PlayerPOVChangeEvent(line);
				break;
			case PLAYER_TRIGGER_ENTER:
			case PLAYER_ENTER_TRIGGER:
			case PLAYER_TRIGGER_LEAVE:
				e = new PlayerTriggerEvent(line);
				break;
			case PLAYER_WEAPON_RIGHT:
			case PLAYER_WEAPON_LEFT:
				e = new PlayerWeaponEvent(line);
				break;
			case PLAYER_WEAPON_WITHDRAWN:
				e = new PlayerWeaponWithdrawnEvent(line);
				break;
			case QUEST_INIT:
				e = new QuestInitEvent(line);
				break;
			case QUEST_STAGE_CHANGE:
				e = new QuestStageChangeEvent(line);
				break;
			case QUEST_COMPLETED:
				e = new QuestCompletedEvent(line);
				break;
			case PLAYER_CELL_CHANGE:
				e = new PlayerCellChangeEvent(line);
				break;
			case PLAYER_WORLDSPACE_CHANGE:
				e = new PlayerWorldspaceChangeEvent(line);
				break;
			case PLAYER_FIGHT:
				e = new PlayerFightEvent(line);
				break;
			case PLAYER_DIALOGUE:
				e = new PlayerDialogueEvent(line);
				break;
			case PLAYER_DISPLAY_CASE_OPEN:
				e = new PlayerDisplayCaseOpenEvent(line);
				break;
			case PLAYER_ACTOR_VIEW_GAIN:
				e = new PlayerActorViewGainEvent(line);
				break;
			case PLAYER_ACTOR_VIEW_LOSS:
				e = new PlayerActorViewLossEvent(line);
				break;
			case PLAYER_DISTANCE_ACTOR:
				e = new PlayerDistanceActorEvent(line);
				break;
			case PLAYER_BOOK_READ:
			case PLAYER_READ_BOOK:
				e = new PlayerBookReadEvent(line);
				break;
			case AI_PACKAGE_START:			
				e = new AIPackageStartEvent(line);
				break;
			case ACTOR_PACKAGE_START:
				e = new ActorPackageStartEvent(line);
				break;
			case ACTOR_MOVE_TO_PLAYER:
				e = new ActorMoveToPlayerEvent(line);
				break;
			case PLAYER_DOOR_OPEN:
				e = new PlayerDoorOpenEvent(line);
				break;
//			case DOOR_OPEN:
//			case ACTOR_DOOR_OPEN:
//				System.out.println(line);
//				e = new PlayerDoorOpenEvent(line);
//				break;		
			case PLAYER_DOOR_ACTIVATE:
				e = new PlayerDoorActivateEvent(line);
				break;
			case PLAYER_GRAB:
				e = new PlayerGrabEvent(line);
				break;
			case ACTOR_DEATH:
				e = new ActorDeathEvent(line);
				break;
			case PLAYER_HIT:
				e = new PlayerHitEvent(line);
				break;
			case ACTOR_HIT: 
				e = new Event(line); // GENERIC EVENT
				break;
			default:
				System.out.println("******NO EVENT TYPE FOR TAG******");
				e = null;
				break;
		}
		
		return e;
	}
}
