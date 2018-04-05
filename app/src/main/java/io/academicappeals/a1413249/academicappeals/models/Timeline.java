package io.academicappeals.a1413249.academicappeals.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;

public class Timeline extends RealmObject {
    @PrimaryKey
    private int timelineId;
    private String timelineName;
    private RealmList<Event> events;

    @LinkingObjects("timelines")
    private final RealmResults<Department> departments = null;

    public int getTimelineId() {
        return timelineId;
    }

    public void setTimelineId(int timelineId) {
        this.timelineId = timelineId;
    }

    public String getTimelineName() {
        return timelineName;
    }

    public void setTimelineName(String name) {
        this.timelineName = name;
    }

    public void addEvent(Event event) {
        this.events.add(event);
    }
}
