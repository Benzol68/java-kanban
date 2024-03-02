package com.sysoev.taskmanager.model;

import java.util.ArrayList;



public class Epic extends com.sysoev.taskmanager.model.Task {
    private final ArrayList<Integer> epicSubtasks;

    public Epic(String name, String description) {
        super(name, description, StatusTask.NEW);
        epicSubtasks = new ArrayList<>();
    }



    public ArrayList<Integer> getEpicSubtasks() {
        return new ArrayList<>(epicSubtasks);
    }


    public void addNewSubtaskInEpic(int id) {
        epicSubtasks.add(id);
    }

    public void deleteSubtask(int id) {
        epicSubtasks.remove(Integer.valueOf(id));
    }

    public void deleteAllSubtasksOfEpic() {
        epicSubtasks.clear();
    }


}