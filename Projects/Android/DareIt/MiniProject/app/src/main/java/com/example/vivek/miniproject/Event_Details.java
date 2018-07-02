package com.example.vivek.miniproject;

import java.util.List;

/**
 * Created by Vivek on 12/3/2017.
 */

public class Event_Details
{
    private String event_id;
    private String event_name;
    private String event_details;
    private String event_venue;
    private long dateOfEvent;
    private long dateOfCreation;
    private String idofCreator;
    private long noOfGuests;
    List <String> guestList;

    public Event_Details()
    {

    }

    public Event_Details(String event_id, String event_name, String event_details, String event_venue, long dateOfEvent, long dateOfCreation, String idofCreator, long noOfGuests, List<String> guestList, String myStatus) {
        this.event_id = event_id;
        this.event_name = event_name;
        this.event_details = event_details;
        this.event_venue = event_venue;
        this.dateOfEvent = dateOfEvent;
        this.dateOfCreation = dateOfCreation;
        this.idofCreator = idofCreator;
        this.noOfGuests = noOfGuests;
        this.guestList = guestList;
    }

    public String getEvent_id() {
        return event_id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public String getEvent_details() {
        return event_details;
    }

    public String getEvent_venue() {
        return event_venue;
    }

    public long getDateOfEvent() {
        return dateOfEvent;
    }

    public long getDateOfCreation() {
        return dateOfCreation;
    }

    public String getIdofCreator() {
        return idofCreator;
    }

    public long getNoOfGuests() {
        return noOfGuests;
    }

    public List<String> getGuestList() {
        return guestList;
    }


    public void setNoOfGuests(long noOfGuests) {
        this.noOfGuests = noOfGuests;
    }

    public void setGuestList(List<String> guestList) {
        this.guestList = guestList;
    }

}
