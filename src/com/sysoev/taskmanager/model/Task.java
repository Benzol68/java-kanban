package com.sysoev.taskmanager.model;

import java.util.Objects;

public class Task {
    private String name;
    private String description;
    private StatusTask statusTask;
    private int id;


    public Task(String name, String description, StatusTask statusTask) {
        this.name = name;
        this.description = description;
        this.statusTask = statusTask;
    }

    public Task(String name, String description, StatusTask statusTask, int id){
        this.name = name;
        this.description = description;
        this.statusTask = statusTask;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public StatusTask getStatusTask() {
        return statusTask;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatusTask(StatusTask statusTask) {
        this.statusTask = statusTask;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(name, task.name) && Objects.equals(description, task.description) && statusTask == task.statusTask;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }


}

