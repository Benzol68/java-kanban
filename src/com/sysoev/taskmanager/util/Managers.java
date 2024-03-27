package com.sysoev.taskmanager.util;

import com.sysoev.taskmanager.service.HistoryManager;
import com.sysoev.taskmanager.service.impl.InMemoryHistoryManager;
import com.sysoev.taskmanager.service.impl.InMemoryTaskManager;
import com.sysoev.taskmanager.service.TaskManager;

public class Managers {

    public static TaskManager getDefaultTaskManager() {
        return new InMemoryTaskManager(getDefaultHistoryManager());
    }

    public static HistoryManager getDefaultHistoryManager() {
        return new InMemoryHistoryManager();
    }
}