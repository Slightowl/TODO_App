package com.example.todoapp.model;

public class TaskList {

    private int id;
    private String taskName;
    private String taskDescription;
    private String timeStamp;

    public TaskList(String taskName, String taskDescription, String timeStamp) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.timeStamp = timeStamp;
    }

    public TaskList() {

    }

    public TaskList(int id, String taskName, String taskDescription , String timeStamp) {
        this.id = id;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.timeStamp = timeStamp;
    }

    public int getId() {
        return id;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }
}
