package io.academicappeals.a1413249.academicappeals.models;


import java.util.Date;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;

public class Event extends RealmObject {
    @PrimaryKey
    private int eventId;
    private String eventName;
    private String eventDesc;
    private Date eventDate;

    @LinkingObjects("events")
    private final RealmResults<Timeline> timelines = null;

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String name) {
        this.eventName = name;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String desc) {
        this.eventDesc = desc;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date date) {
        this.eventDate = date;
    }

}
