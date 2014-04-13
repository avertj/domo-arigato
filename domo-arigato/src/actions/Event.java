package actions;

public class Event {
	private TypeEvent typeEvent;
	private String name;
	
	public Event(TypeEvent typeEvent) {
		this.typeEvent = typeEvent;
		name = "";
	}
	
	public Event(TypeEvent typeEvent, String name) {
		this.typeEvent = typeEvent;
		this.name = name;
	}
	
	public TypeEvent getTypeEvent() {
		return typeEvent;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
