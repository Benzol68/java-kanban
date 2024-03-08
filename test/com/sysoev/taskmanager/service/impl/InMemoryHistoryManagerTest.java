package com.sysoev.taskmanager.service.impl;

import com.sysoev.taskmanager.model.Task;
import com.sysoev.taskmanager.service.HistoryManager;
import com.sysoev.taskmanager.util.Managers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class InMemoryHistoryManagerTest {
    private HistoryManager historyManager;

    @BeforeEach
    void init() {
        historyManager = Managers.getDefaultHistoryManager();
    }

    @Test
    void add_ShouldSavedPreviousVersionTaskAndItsData() {
        Task task = new Task("Задача 1", "Описание 1");
        historyManager.add(task);
        final List<Task> history = historyManager.getHistory();
        final Task savedTask = history.get(0);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        assertEquals(task.getId(), savedTask.getId(), "Задачи не совпадают.");
        assertEquals(task.getName(), savedTask.getName(), "Задачи не совпадают.");
        assertEquals(task.getDescription(), savedTask.getDescription(), "Задачи не совпадают.");
        assertEquals(task.getStatusTask(), savedTask.getStatusTask(), "Задачи не совпадают.");
    }
}