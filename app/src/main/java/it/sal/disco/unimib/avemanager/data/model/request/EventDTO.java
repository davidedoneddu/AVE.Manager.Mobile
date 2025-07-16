package it.sal.disco.unimib.avemanager.data.model.request;

public class EventDTO {
    private String EventId;
    private String EventName;

    public String getEventId() {
        return EventId;
    }

    public void setEventId(String eventId) {
        EventId = eventId;
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }
}
