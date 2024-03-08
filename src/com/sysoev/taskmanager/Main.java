package com.sysoev.taskmanager;

import com.sysoev.taskmanager.model.Epic;
import com.sysoev.taskmanager.model.StatusTask;
import com.sysoev.taskmanager.model.Subtask;
import com.sysoev.taskmanager.model.Task;
import com.sysoev.taskmanager.util.Managers;
import com.sysoev.taskmanager.service.TaskManager;

public class Main {
    public static void main(String[] args) {

        TaskManager taskManager = Managers.getDefaultTaskManager();


        for (int i = 0; i < 2; i++) {
            String name = "Задача " + (i + 1);
            String description = "Описание " + (i + 1);

            Task task = new Task(name, description);
            taskManager.addNewTask(task);
        }
        String name = "Эпик 1";
        String description = "Описание эпика ";
        Epic epic = new Epic(name, description);
        taskManager.addNewEpic(epic);

        for (int i = 0; i < 2; i++) {
            name = "Подзадача " + (i + 1);
            description = "Описание подзадачи " + (i + 1);
            Subtask subtask = new Subtask(name, description, StatusTask.NEW, epic.getId());
            taskManager.addNewSubtask(subtask);
        }

        name = "Эпик 2";
        description = "Описание эпика ";
        Epic epic1 = new Epic(name, description);
        taskManager.addNewEpic(epic1);

        for (int i = 0; i < 3; i++) {
            name = "Подзадача " + (i + 1);
            description = "Описание подзадачи " + (i + 1);
            Subtask subtask = new Subtask(name, description, StatusTask.NEW, epic1.getId());
            taskManager.addNewSubtask(subtask);
        }


        System.out.println(taskManager.getAllTasks());
        taskManager.deleteTaskById(0);
        System.out.println(taskManager.getAllTasks());

        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubtasks());


        taskManager.updateTask(taskManager.getTaskById(1));
        System.out.println(taskManager.getTaskById(1));

        taskManager.deleteTaskById(0);
        System.out.println(taskManager.getAllTasks());

        System.out.println(taskManager.getEpicById(5).getStatusTask());
        taskManager.getSubtaskById(6).setStatusTask(StatusTask.DONE);
        taskManager.getSubtaskById(8).setStatusTask(StatusTask.DONE);

        taskManager.getSubtaskById(7).setStatusTask(StatusTask.DONE);

        System.out.println(taskManager.getSubtaskById(7).getStatusTask());
        System.out.println(taskManager.getSubtaskById(6).getStatusTask());

        taskManager.deleteSubtaskById(7);
        System.out.println(taskManager.getEpicById(5).getStatusTask());

        taskManager.deleteEpicById(5);
        System.out.println(taskManager.getAllEpics());


        System.out.println(taskManager.getSubtaskById(3));
        taskManager.updateSubtask(taskManager.getSubtaskById(3));
        System.out.println(taskManager.getSubtaskById(3));
    }
}