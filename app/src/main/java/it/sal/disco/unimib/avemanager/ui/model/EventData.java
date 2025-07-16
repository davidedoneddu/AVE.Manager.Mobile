package it.sal.disco.unimib.avemanager.ui.model;

import java.time.LocalDateTime;

public class EventData {
    private int EventId;
    private String EventName;
    private int EventQuorum;
    private int EventInvPresenti;
    private int EventInvTotali;
    private int EventVotiInvPresenti;
    private int EventVotiInvTotali;
    private int EventVotiPresenti;
    private int EventVotiTotali;
    private double EventPercQuorum;
    private LocalDateTime EventDataQuorum;
    private boolean EventIsQuorumOk;

    // Costruttore vuoto
    public EventData() {
    }

    public int getEventId() {
        return EventId;
    }

    public void setEventId(int eventId) {
        this.EventId = eventId;
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        this.EventName = eventName;
    }

    public int getEventQuorum() {
        return EventQuorum;
    }

    public void setEventQuorum(int eventQuorum) {
        this.EventQuorum = eventQuorum;
    }

    public int getEventInvPresenti() {
        return EventInvPresenti;
    }

    public void setEventInvPresenti(int eventInvPresenti) {
        this.EventInvPresenti = eventInvPresenti;
    }

    public int getEventInvTotali() {
        return EventInvTotali;
    }

    public void setEventInvTotali(int eventInvTotali) {
        this.EventInvTotali = eventInvTotali;
    }

    public int getEventVotiInvPresenti() {
        return EventVotiInvPresenti;
    }

    public void setEventVotiInvPresenti(int eventVotiInvPresenti) {
        this.EventVotiInvPresenti = eventVotiInvPresenti;
    }

    public int getEventVotiInvTotali() {
        return EventVotiInvTotali;
    }

    public void setEventVotiInvTotali(int eventVotiInvTotali) {
        this.EventVotiInvTotali = eventVotiInvTotali;
    }

    public int getEventVotiPresenti() {
        return EventVotiPresenti;
    }

    public void setEventVotiPresenti(int eventVotiPresenti) {
        this.EventVotiPresenti = eventVotiPresenti;
    }

    public int getEventVotiTotali() {
        return EventVotiTotali;
    }

    public void setEventVotiTotali(int eventVotiTotali) {
        this.EventVotiTotali = eventVotiTotali;
    }

    public double getEventPercQuorum() {
        return EventPercQuorum;
    }

    public void setEventPercQuorum(double eventPercQuorum) {
        this.EventPercQuorum = eventPercQuorum;
    }

    public LocalDateTime getEventDataQuorum() {
        return EventDataQuorum;
    }

    public void setEventDataQuorum(LocalDateTime eventDataQuorum) {
        this.EventDataQuorum = eventDataQuorum;
    }

    public boolean isEventIsQuorumOk() {
        return EventIsQuorumOk;
    }

    public void setEventIsQuorumOk(boolean eventIsQuorumOk) {
        this.EventIsQuorumOk = eventIsQuorumOk;
    }
}
