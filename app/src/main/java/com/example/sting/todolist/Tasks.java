package com.example.sting.todolist;

public class Tasks {
    private int mID;
    private String mTask;
    private String mDescription;
    private String mStatus;

    public Tasks(int id,String task, String description, String status){
        mID = id;
        mTask = task;
        mDescription = description;
        mStatus = status;
    }

    public int getID () { return mID; }
    public String getTask () { return mTask; }
    public String getDescription () { return mDescription; }
    public String getStatus () { return mStatus; }
}
