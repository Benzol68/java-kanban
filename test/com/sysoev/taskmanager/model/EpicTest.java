package com.sysoev.taskmanager.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EpicTest {

    @Test
    void testEquals_ShouldEpicsEqualsIfEqualsId() {
        Epic epic = new Epic("Задача 1", "Описание 1", StatusTask.IN_PROGRESS, 5);
        Epic epic1 = new Epic("Задача 2", "Описание 2", StatusTask.NEW, 5);

        assertEquals(epic.getId(), epic1.getId(), "ID не совпадают.");
        assertEquals(epic, epic1, "Задачи не совпадают.");
    }
}
