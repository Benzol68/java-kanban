package com.sysoev.taskmanager.service.impl;

import com.sysoev.taskmanager.model.Epic;
import com.sysoev.taskmanager.model.Subtask;
import com.sysoev.taskmanager.model.Task;
import com.sysoev.taskmanager.service.TaskManager;
import com.sysoev.taskmanager.util.Managers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    private TaskManager taskManager;

    @BeforeEach
    void init() {
        taskManager = Managers.getDefaultTaskManager();
    }

    @Test
    void add_ShouldSavedPreviousVersionTaskAndItsData() {
        Task task = new Task("Задача 1", "Описание 1");
        taskManager.addNewTask(task);
        taskManager.getAllTasks();
        final List<Task> history = taskManager.getHistory();
        final Task savedTask = history.get(0);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        assertEquals(task.getId(), savedTask.getId(), "Задачи не совпадают.");
        assertEquals(task.getName(), savedTask.getName(), "Задачи не совпадают.");
        assertEquals(task.getDescription(), savedTask.getDescription(), "Задачи не совпадают.");
        assertEquals(task.getStatusTask(), savedTask.getStatusTask(), "Задачи не совпадают.");
    }

    @Test
    void remove_ShouldDeleteTaskInManagerAndDeleteInHistory() {
        for (int i = 0; i < 13; i++) {
            Task task = new Task("Задача 1", "Описание 1");
            taskManager.addNewTask(task);
        }
        for (Task task : taskManager.getAllTasks()) {
            int count = taskManager.getHistory().size();
            taskManager.deleteTaskById(task.getId());

            assertNotEquals(count, taskManager.getHistory().size(), "Задача не удалена из истории");
        }
    }

    @Test
    void remove_ShouldDeleteEpicInManagerAndDeleteInHistoryAllSubtasksOfEpic() {
        Epic epic = new Epic("Эпик 1", "Описание эпика 1");
        taskManager.addNewEpic(epic);
        for (int i = 0; i < 13; i++) {
            Subtask subtask = new Subtask("Задача 1", "Описание 1", epic.getId());
            taskManager.addNewSubtask(subtask);
        }
        taskManager.getAllEpics();
        taskManager.getAllSubtasks();
        int countTasks = taskManager.getHistory().size();
        taskManager.deleteAllEpics();
        assertEquals(0, taskManager.getHistory().size(), "Подзадачи не удалены");
    }

    @Test
    void add_ShouldReDisplayingTasksChangesOrderOfHistory() {
        for (int i = 0; i < 10; i++) {
            Task task = new Task(("Задача " + i), ("Описание " + i));
            taskManager.addNewTask(task);
        }
        taskManager.getAllTasks();
        int size = taskManager.getHistory().size();
        taskManager.getTaskById(5);
        assertEquals(size, taskManager.getHistory().size(), "Количество задач не изменилось");
        assertEquals(taskManager.getTaskById(5), taskManager.getHistory().get(size - 1), "Порядок задач не изменился");

    }
@Test
    void add_ShouldTaskWithSameIsOverwrittenInHistory() {

        Task task = new Task(("Задача 1"), ("Описание 1"));
        taskManager.addNewTask(task);

    taskManager.getTaskById(0);
    String description = taskManager.getHistory().get(0).getDescription();
    taskManager.getTaskById(0).setDescription("Новое описание");
    assertNotEquals(description, taskManager.getHistory().get(0).getDescription(), "Задача не перезаписалась");
}
}