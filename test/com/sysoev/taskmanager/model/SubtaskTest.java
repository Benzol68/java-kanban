package com.sysoev.taskmanager.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {
    @Test
    void testEquals_ShouldSubtasksEqualsIfEqualsId() {
        Subtask subtask = new Subtask("Задача 1", "Описание 1", 3);
        Subtask subtask1 = new Subtask("Задача 3", "Описание 2", 3);

        assertEquals(subtask.getId(), subtask1.getId(), "ID не совпадают.");
        assertEquals(subtask, subtask1, "Задачи не совпадают.");
    }
}