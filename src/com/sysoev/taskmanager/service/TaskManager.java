package com.sysoev.taskmanager.service;

import com.sysoev.taskmanager.model.Epic;
import com.sysoev.taskmanager.model.Subtask;
import com.sysoev.taskmanager.model.Task;

import java.util.ArrayList;

public interface TaskManager {
    //  добавление новых задач
    int addNewTask(Task newTask);

    int addNewEpic(Epic newEpic);

    int addNewSubtask(Subtask newSubtask);

    // удаление всех задач
    void deleteAllTasks();

    void deleteAllEpics();

    void deleteAllSubtasks();

    // получение задачи/эпика/подзадачи по идентификатору
    Task getTaskById(int id);

    Epic getEpicById(int id);

    Subtask getSubtaskById(int id);

    // Получение списка всех подзадач определённого эпика.
    ArrayList<Subtask> getSubtasksOfEpic(int epicId);

    ArrayList<Task> getAllTasks();

    // получение списка всех эпиков
    ArrayList<Epic> getAllEpics();

    // получение списка всех подзадач
    ArrayList<Subtask> getAllSubtasks();

    void deleteTaskById(int id);

    Epic deleteEpicById(int id);

    void deleteSubtaskById(int id);

    void updateTask(Task updateTask);

    void updateSubtask(Subtask updateSubtask);

    void updateEpic(Epic updateEpic);
}
