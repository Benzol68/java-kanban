package com.sysoev.taskmanager.model;

import java.util.ArrayList;
import java.util.List;


public class Epic extends com.sysoev.taskmanager.model.Task {
    private final List<Integer> epicSubtasks;

    public Epic(String name, String description) {
        super(name, description);
        epicSubtasks = new ArrayList<>();
    }

    public Epic(String name, String description, StatusTask statusTask, int id) {
        super(name, description, statusTask, id);
        this.epicSubtasks = new ArrayList<>();
    }

    public List<Integer> getEpicSubtasks() {
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
