package com.sysoev.taskmanager.model;

public class Subtask extends com.sysoev.taskmanager.model.Task {

    private final int epicId;

    public Subtask(String name, String description, StatusTask statusTask, int epicId){
        super(name, description, statusTask);
        this.epicId = epicId;
    }


    public int getEpicId() {
        return epicId;
    }

}