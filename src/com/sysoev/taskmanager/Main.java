package com.sysoev.taskmanager;

import com.sysoev.taskmanager.servise.TaskManager;
import com.sysoev.taskmanager.servise.StatusTask;
import model.Epic;
import model.Subtask;
import model.Task;

public class Main {
    public static void main(String[] args) {

        TaskManager taskManager = new TaskManager();


        for (int i = 0; i < 2; i++) {
            String name = "Задача " + (i + 1);
            String description = "Описание " + (i + 1);

            Task task = new Task(name, description, StatusTask.NEW);
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


        taskManager.updateTask(taskManager.getTasks().get(1));
        System.out.println(taskManager.getTasks().get(1));

        taskManager.deleteTaskById(0);
        System.out.println(taskManager.getAllTasks());

        System.out.println(taskManager.getEpics().get(5).getStatusTask());
        taskManager.getSubtasks().get(6).setStatusTask(StatusTask.DONE);
        taskManager.getSubtasks().get(8).setStatusTask(StatusTask.DONE);

        taskManager.getSubtasks().get(7).setStatusTask(StatusTask.DONE);

        System.out.println(taskManager.getSubtasks().get(7).getStatusTask());
        System.out.println(taskManager.getEpics().get(5).getStatusTask());

        taskManager.deleteSubtaskById(7);
        System.out.println(taskManager.getEpics().get(5).getStatusTask());

        taskManager.deleteEpicById(5);
        System.out.println(taskManager.getAllEpics());


        System.out.println(taskManager.getSubtasks().get(3));
        taskManager.updateSubtask(taskManager.getSubtaskById(3));
        System.out.println(taskManager.getSubtasks().get(3));
    }
}