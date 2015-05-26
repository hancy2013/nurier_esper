package event;

public class BaseEvent {
	private int eventID;
	private int dataIntVal;
	private String macAddress1;
	
	public BaseEvent(int eventID, int dataIntVal, String macAddress1) {
		this.eventID = eventID;
		this.dataIntVal = dataIntVal;
		this.macAddress1 = macAddress1;
	}
	
	public int getEventID() {
		return eventID;
	}
	public void setEventID(int eventID) {
		this.eventID = eventID;
	}
	public int getDataIntVal() {
		return dataIntVal;
	}
	public void setDataIntVal(int dataIntVal) {
		this.dataIntVal = dataIntVal;
	}

	public String getMacAddress1() {
		return macAddress1;
	}

	public void setMacAddress_1(String macAddress1) {
		this.macAddress1 = macAddress1;
	}

}
