package com.example.desktop.event;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;

public class Event implements Serializable {
    private String eventName;
    private Timestamp eventTime;
    private String eventDesc;
    private ArrayList<String> Participant;
    private String Origizator;
    private Double Lat;
    private Double Lng;
    private String CREATE_TIME;

    public Event(String name, String desc) {
        eventName = name;
        eventDesc = desc;
        CREATE_TIME = "0";
        eventTime = null;
        Participant = new ArrayList<>();
        Origizator = " ";
        Lat = 0.00;
        Lng = 0.00;
    }

    public Event(String name, Timestamp time, String desc, ArrayList<String> ptt, String org, Double lat, Double lng, String ctime) {
        this.eventName = name;
        this.eventTime = time;
        this.eventDesc = desc;
        this.Participant.addAll(ptt);
        this.Origizator = org;
        this.Lat = lat;
        this.Lng = lng;
        this.CREATE_TIME = ctime;
    }

    public Event(Event e) {
        eventName = e.getEventName();
        eventTime = e.getEventTime();
        eventDesc = e.getEventDesc();
        if (!e.getParticipant().isEmpty() && e.getParticipant() != null)
            Participant.addAll(e.getParticipant());
        Origizator = e.getOrigizator();
        Lat = e.getLat();
        Lng = e.getLng();
        CREATE_TIME = e.getCREATE_TIME();
    }

    public void joinEvent(String name) {
        Participant.add(name);
    }

    public void quitEvent(String name) {
        Participant.remove(name);
    }

    public boolean isParticipant(String name) {
        for (int i = 0; i < Participant.size(); i++) {
            if (name.equals(Participant.get(i))) {
                return true;
            }
        }
        return false;
    }

    public boolean isOriginizator(String name) {
        return (name.equals(Origizator)) ? true : false;
    }

    public String outputParticipant() {
        if (Participant != null && !Participant.isEmpty()) {

            StringBuilder sb = new StringBuilder();
            sb.append(Participant.get(0));
            for (int i = 1; i < Participant.size(); i++) {
                sb.append("," + Participant.get(i));
            }
            return sb.toString();
        }
        return "";
    }

    public void setEventDetail(String name, String desc) {
        eventName = name;
        eventDesc = desc;
    }

    public void setParticipant(ArrayList<String> arr) {
        Participant.addAll(arr);
    }

    public void setLatLng(Double a, Double b) {
        Lat = a;
        Lng = b;
    }

    public void setTimstamp(int year, int month, int day, int hour, int minutes) {
        eventTime = new Timestamp(new GregorianCalendar(year, month, day, hour, minutes).getTimeInMillis());
    }

    public void setTimstamp(String stamp) {
        eventTime = Timestamp.valueOf(stamp);
    }

    public String getOrigizator() {
        return Origizator;
    }

    public void setOrigizator(String name) {
        this.Origizator = name;
    }

    public Timestamp getEventTime() {
        return eventTime;
    }

    public ArrayList<String> getParticipant() {
        return Participant;
    }

    public void setParticipant(String input) {
        String[] ppt = input.split(",");
        Participant = new ArrayList<>(Arrays.asList(ppt));
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public Double getLat() {
        return Lat;
    }

    public Double getLng() {
        return Lng;
    }

    public String getCREATE_TIME() {
        return CREATE_TIME;
    }

    public void setCREATE_TIME(String ctime) {
        CREATE_TIME = ctime;
    }

    public String toString() {
        return eventName + eventTime + eventDesc + Lng.toString() + Lat.toString() + CREATE_TIME;
    }
}
