package com.sysoev.taskmanager.service.impl;

import com.sysoev.taskmanager.model.Epic;
import com.sysoev.taskmanager.model.StatusTask;
import com.sysoev.taskmanager.model.Subtask;
import com.sysoev.taskmanager.model.Task;
import com.sysoev.taskmanager.service.TaskManager;
import com.sysoev.taskmanager.util.Managers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    private TaskManager taskManager;

    @BeforeEach
    void init() {
        taskManager = Managers.getDefaultTaskManager();
    }

    @Test
    void addNewTask_ShouldSaveTaskAndGetTaskById() {

        Task task = new Task("Задача 1", "Описание 1");
        final int taskId = taskManager.addNewTask(task);
        final Task savedTask = taskManager.getTaskById(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getAllTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }


    @Test
    void addNewSubtask__ShouldSaveSubtaskAndGetSubtaskById() {
        Epic epic = new Epic("Эпик 1", "Описание эпика 1");
        taskManager.addNewEpic(epic);
        Subtask subtask = new Subtask("Задача 1", "Описание 1", epic.getId());
        final int subtaskId = taskManager.addNewSubtask(subtask);
        final Subtask savedSubtask = taskManager.getSubtaskById(subtaskId);

        assertNotNull(savedSubtask, "Задача не найдена.");
        assertEquals(subtask, savedSubtask, "Задачи не совпадают.");

        final List<Subtask> subtasks = taskManager.getAllSubtasks();

        assertNotNull(subtasks, "Задачи не возвращаются.");
        assertEquals(1, subtasks.size(), "Неверное количество задач.");
        assertEquals(subtask, subtasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void addNewEpic_ShouldSaveEpicAndGetEpicById() {
        Epic epic = new Epic("Эпик 1", "Описание эпика 1");
        final int EpicId = taskManager.addNewEpic(epic);
        final Epic savedEpic = taskManager.getEpicById(EpicId);

        assertNotNull(savedEpic, "Задача не найдена.");
        assertEquals(epic, savedEpic, "Задачи не совпадают.");

        final List<Epic> epics = taskManager.getAllEpics();

        assertNotNull(epics, "Задачи не возвращаются.");
        assertEquals(1, epics.size(), "Неверное количество задач.");
        assertEquals(epic, epics.get(0), "Задачи не совпадают.");
    }

    @Test
    void deleteEpicById_ShouldDeleteEpicAndDeleteAllSubtasksInEpicById() {
        Epic epic = new Epic("Эпик 1", "Описание эпика 1");
        final int epicId = taskManager.addNewEpic(epic);
        final Epic savedEpic = taskManager.getEpicById(epicId);
        final List<Epic> epics = taskManager.getAllEpics();
        int countSubtask = 0;
        for (int i = 0; i < 4; i++) {
            Subtask subtask = new Subtask("Задача 1", "Описание 1", epic.getId());
            taskManager.addNewSubtask(subtask);
            countSubtask++;
        }
        final List<Integer> subtasksId = savedEpic.getEpicSubtasks();

        assertEquals(1, epics.size(), "Неверное количество задач.");
        assertEquals(countSubtask, subtasksId.size(), "Неверное количество задач.");
        assertEquals(epic, savedEpic, "Задачи не совпадают.");
        assertNotNull(savedEpic, "Задача не найдена.");


        taskManager.deleteEpicById(epicId);

        final Epic deletedEpic = taskManager.getEpicById(epicId);
        assertNull(deletedEpic, "Задача присутствет");

        for (Integer deletedSubtask : subtasksId) {
            assertNull(taskManager.getSubtaskById(deletedSubtask), "Задача присутствет");
        }
    }

    @Test
    void addTask_ShouldTaskIdWritesIdGenerate() {
        Task task = new Task("Задача 1", "Описание 1", StatusTask.NEW, 23);
        final int newId = taskManager.addNewTask(task);

        assertEquals(task.getId(), newId, "ID не совпадают.");
    }

    @Test
    void addTask_ShouldAfterAddingTaskToManagerFieldsMatch() {
        final Task task = new Task("Задача 1", "Описание 1");
        final int taskId = taskManager.addNewTask(task);
        final Task savedTask = taskManager.getTaskById(taskId);

        assertNotNull(savedTask, "Задача не найдена.");


        assertEquals(task.getId(), savedTask.getId(), "Поля не совпадают.");
        assertEquals(task.getName(), savedTask.getName(), "Поля не совпадают.");
        assertEquals(task.getDescription(), savedTask.getDescription(), "Поля не совпадают.");
        assertEquals(task.getStatusTask(), savedTask.getStatusTask(), "Поля не совпадают.");
    }
}
