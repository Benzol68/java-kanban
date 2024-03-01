package model;

import com.sysoev.taskmanager.servise.StatusTask;

public class Subtask extends model.Task {

    private final int epicId;


    public Subtask(String name, String description, StatusTask statusTask, int epicId){
        super(name, description, statusTask);
        this.epicId = epicId;
    }


    public int getEpicId() {
        return epicId;
    }

}