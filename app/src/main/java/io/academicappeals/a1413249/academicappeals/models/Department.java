package io.academicappeals.a1413249.academicappeals.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Department extends RealmObject {
    @PrimaryKey
    private int departmentId;
    private String departmentName;
    private RealmList<Timeline> timelines;

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String name) {
        this.departmentName = name;
    }

    public void addTimeline(Timeline timeline) {
        this.timelines.add(timeline);
    }

}
