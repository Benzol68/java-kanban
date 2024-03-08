package com.sysoev.taskmanager.service.impl;

import com.sysoev.taskmanager.model.Epic;
import com.sysoev.taskmanager.model.StatusTask;
import com.sysoev.taskmanager.model.Subtask;
import com.sysoev.taskmanager.model.Task;
import com.sysoev.taskmanager.service.HistoryManager;
import com.sysoev.taskmanager.service.TaskManager;
import com.sysoev.taskmanager.util.IdGenerate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {

    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();

    private HistoryManager historyManager;
    private IdGenerate idGenerate = new IdGenerate();

    public InMemoryTaskManager(HistoryManager historyManager) {
        this.historyManager = historyManager;
    }

    //  добавление новых задач
    @Override
    public int addNewTask(Task newTask) {
        newTask.setId(idGenerate.generateNewId());
        tasks.put(newTask.getId(), newTask);

        return newTask.getId();
    }

    @Override
    public int addNewEpic(Epic newEpic) {
        newEpic.setId(idGenerate.generateNewId());
        epics.put(newEpic.getId(), newEpic);

        return newEpic.getId();
    }

    @Override
    public int addNewSubtask(Subtask newSubtask) {
        newSubtask.setId(idGenerate.generateNewId());

        Epic epic = epics.get(newSubtask.getEpicId());
        epic.addNewSubtaskInEpic(newSubtask.getId());

        subtasks.put(newSubtask.getId(), newSubtask);
        changeEpicStatus(epic);

        return newSubtask.getId();
    }


    // удаление всех задач
    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public void deleteAllEpics() {
        subtasks.clear();
        epics.clear();
    }

    @Override
    public void deleteAllSubtasks() {
        for (Epic epic : epics.values()) {
            epic.deleteAllSubtasksOfEpic();
            changeEpicStatus(epic);
        }
        subtasks.clear();
    }


    // получение задачи/эпика/подзадачи по идентификатору
    @Override
    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        historyManager.add(task);

        return task;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = epics.get(id);
        historyManager.add(epic);

        return epic;
    }

    @Override
    public Subtask getSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);
        historyManager.add(subtasks.get(id));

        return subtask;
    }


    // Получение списка всех подзадач определённого эпика.
    @Override
    public ArrayList<Subtask> getSubtasksOfEpic(int epicId) {
        ArrayList<Subtask> SubtasksOfEpic = new ArrayList<>();
        for (Integer subtask : epics.get(epicId).getEpicSubtasks()) {
            SubtasksOfEpic.add(subtasks.get(subtask));
        }
        return SubtasksOfEpic;
    }

    // получение списка всех задач

    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    // получение списка всех эпиков
    @Override
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    // получение списка всех подзадач
    @Override
    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public List<Task> getHistory() {
        return historyManager.getHistory();
    }


    // удаление по id


    @Override
    public void deleteTaskById(int id) {
        tasks.remove(id);
    }


    @Override
    public Epic deleteEpicById(int id) {
        Epic epic = epics.remove(id);
        if (epic == null) {
            return epic;
        }
        for (Integer epicSubtaskId : epic.getEpicSubtasks()) {
            subtasks.remove(epicSubtaskId);
        }
        return epic;
    }

    @Override
    public void deleteSubtaskById(int id) {
        Subtask subtask = subtasks.remove(id);
        int epicId = subtask.getEpicId();
        Epic epic = epics.get(epicId);
        epic.deleteSubtask(id);
        changeEpicStatus(epic);
    }


    // обновление задачи/подзадачи/эпика

    @Override
    public void updateTask(Task updateTask) {
        if (tasks.containsKey(updateTask.getId())) {
            tasks.put(updateTask.getId(), updateTask);
        }
    }

    @Override
    public void updateSubtask(Subtask updateSubtask) {
        Subtask subtask = subtasks.get(updateSubtask.getId());
        int epicId = updateSubtask.getEpicId();
        if (subtask != null && subtask.getEpicId() == epicId) {
            subtasks.put(updateSubtask.getId(), updateSubtask);
            changeEpicStatus(epics.get(epicId));
        }
    }

    @Override
    public void updateEpic(Epic updateEpic) {
        Epic oldEpic = epics.get(updateEpic.getId());
        if (oldEpic != null) {
            oldEpic.setName(updateEpic.getName());
            oldEpic.setDescription(updateEpic.getDescription());
        }
    }

    // изменение статуса задачи
    private void changeEpicStatus(Epic epic) {
        if (epic.getEpicSubtasks().isEmpty()) {
            epic.setStatusTask(StatusTask.NEW);
            return;
        }
        int countNew = 0;
        int countDone = 0;

        for (int id : epic.getEpicSubtasks()) {

            switch (subtasks.get(id).getStatusTask()) {
                case NEW:
                    countNew++;
                    break;
                case DONE:
                    countDone++;
                    break;
                case IN_PROGRESS:
                    epic.setStatusTask(StatusTask.IN_PROGRESS);
                    return;
            }
        }

        if (countNew == epic.getEpicSubtasks().size()) {
            epic.setStatusTask(StatusTask.NEW);
        } else if (countDone == epic.getEpicSubtasks().size()) {
            epic.setStatusTask(StatusTask.DONE);
        } else {
            epic.setStatusTask(StatusTask.IN_PROGRESS);
        }
    }

}



