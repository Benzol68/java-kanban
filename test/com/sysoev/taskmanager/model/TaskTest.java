package com.sysoev.taskmanager.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskTest {

    @Test
    void testEquals_ShouldTasksEqualsIfEqualsId() {
        Task task = new Task("Задача 1", "Описание 1", StatusTask.IN_PROGRESS, 3);
        Task task1 =  new Task("Задача 2", "Описание 2", StatusTask.NEW, 3);

        assertEquals(task.getId(), task1.getId(), "ID не совпадают.");
        assertEquals(task, task1, "Задачи не совпадают.");
    }
}
