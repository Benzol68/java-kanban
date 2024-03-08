package com.sysoev.taskmanager.service.impl;

import com.sysoev.taskmanager.model.Task;
import com.sysoev.taskmanager.service.HistoryManager;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private static final int MAX_SIZE = 10;
    private List<Task> history = new ArrayList<>();

    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }
        if (history.size() >= MAX_SIZE) {
            history.removeFirst();
        }
        history.add(task);
    }


    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(history);
    }
}
