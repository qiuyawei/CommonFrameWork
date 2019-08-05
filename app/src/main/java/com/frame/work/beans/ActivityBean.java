package com.frame.work.beans;

public class ActivityBean {
    private String activityName;
    private String activityDesc;


    public ActivityBean(String activityName, String activityDesc){
        this.activityName=activityName;
        this.activityDesc=activityDesc;

    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityDesc() {
        return activityDesc;
    }

    public void setActivityDesc(String activityDesc) {
        this.activityDesc = activityDesc;
    }
}
