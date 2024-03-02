package com.sysoev.taskmanager.servise;

import com.sysoev.taskmanager.model.Epic;
import com.sysoev.taskmanager.model.StatusTask;
import com.sysoev.taskmanager.model.Subtask;
import com.sysoev.taskmanager.model.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {

        private HashMap<Integer, Task> tasks = new HashMap<>();
        private HashMap<Integer, Epic> epics = new HashMap<>();
        private HashMap<Integer, Subtask> subtasks = new HashMap<>();

        private IdGenerate idGenerate = new IdGenerate();

        //  добавление новых задач
        public int addNewTask(Task newTask) {
            newTask.setId(idGenerate.generateNewId());
            tasks.put(newTask.getId(), newTask);
            return newTask.getId();
        }

        public int addNewEpic(Epic newEpic) {
            newEpic.setId(idGenerate.generateNewId());
            epics.put(newEpic.getId(), newEpic);
            return newEpic.getId();
        }

        public int addNewSubtask(Subtask newSubtask) {
            newSubtask.setId(idGenerate.generateNewId());

            Epic epic = epics.get(newSubtask.getEpicId());
            epic.addNewSubtaskInEpic(newSubtask.getId());

            subtasks.put(newSubtask.getId(), newSubtask);
            changeEpicStatus(epic);

            return newSubtask.getId();
        }


        // удаление всех задач
        public void deleteAllTasks() {
            tasks.clear();
        }

        public void deleteAllEpics() {
            subtasks.clear();
            epics.clear();
        }

        public void deleteAllSubtasks() {
            for (Epic epic : epics.values()) {
                epic.deleteAllSubtasksOfEpic();
                changeEpicStatus(epic);
            }
            subtasks.clear();
        }


        // получение задачи/эпика/подзадачи по идентификатору
        public Task getTaskById(int id) {
            return tasks.get(id);
        }

        public Epic getEpicById(int id) {
            return epics.get(id);
        }

        public Subtask getSubtaskById(int id) {
            return subtasks.get(id);
        }


        // Получение списка всех подзадач определённого эпика.
        public ArrayList<Subtask> getSubtasksOfEpic(int epicId) {
            ArrayList<Subtask> SubtasksOfEpic = new ArrayList<>();
            for (Integer subtask : epics.get(epicId).getEpicSubtasks()) {
                SubtasksOfEpic.add(subtasks.get(subtask));
            }
            return SubtasksOfEpic;
        }

        // получение списка всех задач
        public ArrayList<Task> getAllTasks() {
            return new ArrayList<>(tasks.values());
        }

        // получение списка всех эпиков
        public ArrayList<Epic> getAllEpics() {
            return new ArrayList<>(epics.values());
        }

        // получение списка всех подзадач
        public ArrayList<Subtask> getAllSubtasks() {
            return new ArrayList<>(subtasks.values());
        }

        // удаление по id

        public void deleteTaskById(int id) {
            tasks.remove(id);
        }


        public void deleteEpicById(int id) {
            Epic epic = epics.remove(id);
            if (epic == null) {
                return;
            }
            for (Integer epicSubtaskId : epic.getEpicSubtasks()) {
                subtasks.remove(epicSubtaskId);
            }
        }

        public void deleteSubtaskById(int id) {
            Subtask subtask = subtasks.remove(id);
            int epicId = subtask.getEpicId();
            Epic epic = epics.get(epicId);
            epic.deleteSubtask(id);
            changeEpicStatus(epic);
        }


        // обновление задачи/подзадачи/эпика
        public void updateTask(Task updateTask) {
            if (tasks.containsKey(updateTask.getId())) {
                tasks.put(updateTask.getId(), updateTask);
            }
        }

        public void updateSubtask(Subtask updateSubtask) {
            Subtask subtask = subtasks.get(updateSubtask.getId());
            int epicId = updateSubtask.getEpicId();
            if (subtask != null && subtask.getEpicId() == epicId) {
                subtasks.put(updateSubtask.getId(), updateSubtask);
                changeEpicStatus(epics.get(epicId));
            }
        }

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



