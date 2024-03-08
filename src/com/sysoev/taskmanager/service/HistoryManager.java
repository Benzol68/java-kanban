package com.sysoev.taskmanager.service;

import com.sysoev.taskmanager.model.Task;

import java.util.List;

public interface HistoryManager {
    void add(Task task);


    List<Task> getHistory();
}
