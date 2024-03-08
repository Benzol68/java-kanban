package com.sysoev.taskmanager.util;

import com.sysoev.taskmanager.service.HistoryManager;
import com.sysoev.taskmanager.service.TaskManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ManagersTest {


    @Test
    void getDefaultTaskManager() {
        TaskManager taskManager = Managers.getDefaultTaskManager();
        assertNotNull(taskManager, "Объект не инициализирован");

    }

    @Test
    void getDefaultHistoryManager() {
        HistoryManager historyManager = Managers.getDefaultHistoryManager();
        assertNotNull(historyManager, "Объект не инициализирован");
    }
}