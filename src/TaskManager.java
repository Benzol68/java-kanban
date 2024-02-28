import java.util.ArrayList;
import java.util.HashMap;


public class TaskManager {


    HashMap<Integer, Task> tasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();
    HashMap<Integer, Subtask> subtasks = new HashMap<>();



    // добавление новых задач
    public int addNewTask(Task newTask) {
        newTask.setId(IdGenerate.generateNewId());
        tasks.put(newTask.getId(), newTask);
        return newTask.getId();
    }

    public int addNewEpic(Epic newEpic) {
        newEpic.setId(IdGenerate.generateNewId());
        epics.put(newEpic.getId(), newEpic);
        return newEpic.getId();
    }

    public int addNewSubtask(Subtask newSubtask) {
        newSubtask.setId(IdGenerate.generateNewId());

        Epic epic = getEpicById(newSubtask.getEpicId());
        epic.getEpicSubtasks().add(newSubtask.getId());

        subtasks.put(newSubtask.getId(), newSubtask);
        changeEpicStatus(getEpicById(newSubtask.getEpicId()));

        return newSubtask.getId();
    }

    // изменение статуса задачи
public void changeStatusSubtask(int id, StatusTask statusTask) {
        subtasks.get(id).setStatusTask(statusTask);
    changeEpicStatus(epics.get(subtasks.get(id).getEpicId()));
}
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
    public ArrayList<Integer> getSubtasksOfEpic(int epicId) {
        return new ArrayList<>(epics.get(epicId).getEpicSubtasks());
    }


    // получение списка всех задач
    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> allTasks = new ArrayList<>();
        for (Task task : tasks.values()) {
            allTasks.add(tasks.get(task.getId()));
        }
        return allTasks;
    }
    // получение списка всех эпиков
    public ArrayList<Epic> getAllEpics() {
        ArrayList<Epic> allEpics = new ArrayList<>();
        for (Epic epic : epics.values()) {
            allEpics.add(epics.get(epic.getId()));
        }
        return allEpics;
    }
    // получение списка всех подзадач
    public ArrayList<Subtask> getAllSubtasks() {
        ArrayList<Subtask> allSubtasks = new ArrayList<>();
        for (Subtask subtask : subtasks.values()) {
            allSubtasks.add(subtasks.get(subtask.getId()));
        }
        return allSubtasks;
    }

    // удаление по id

    public void deleteTaskById(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        }
    }

    public void deleteEpicById(int id) {
        if (epics.containsKey(id)) {
            for (Integer epicSubtascId : epics.get(id).getEpicSubtasks()) {
                if (epicSubtascId == subtasks.get(epicSubtascId).getEpicId()) {
                    subtasks.remove(epicSubtascId);
                }
            }
            epics.remove(Integer.valueOf(id));
        }
    }

    public void deleteSubtaskById(int id) {
        epics.get(subtasks.get(id).getEpicId()).getEpicSubtasks().remove(Integer.valueOf(id));
        int epicId = subtasks.get(Integer.valueOf(id)).getEpicId();
        if (subtasks.containsKey(id)) {

            subtasks.remove(Integer.valueOf(id));
        }
        changeEpicStatus(epics.get(epicId));
    }


    // обновление задачи/подзадачи/эпика
    public void updateTask(Task updateTask) {
        if (tasks.containsKey(updateTask.getId())) {
            Task task = new Task(updateTask.getName(), updateTask.getDescription(),
                    updateTask.getStatusTask(), updateTask.getId());
            task.setId(updateTask.getId());

            tasks.put(updateTask.getId(), task);
        }
    }

    public void updateSubtask(Subtask updateSubtask) {
        if ((subtasks.containsKey(updateSubtask.getId())) &&
                (subtasks.get(updateSubtask.getId()).getEpicId() == updateSubtask.getEpicId())) {
            Subtask subtask = new Subtask(updateSubtask.getName(), updateSubtask.getDescription(),
                    updateSubtask.getStatusTask(), updateSubtask.getEpicId());
            subtask.setId(updateSubtask.getId());

            subtasks.put(updateSubtask.getId(), subtask);
            changeEpicStatus(epics.get(subtask.getEpicId()));
        }
    }

    public void updateEpic(Epic updateEpic) {
        if (epics.containsKey(updateEpic.getId())) {
            Epic epic = new Epic(updateEpic.getName(), updateEpic.getDescription());

            epic.setEpicSubtasks(updateEpic.getEpicSubtasks());
            epic.setId(updateEpic.getId());

            epics.put(updateEpic.getId(), epic);

        }
    }


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



        taskManager.updateTask(taskManager.tasks.get(1));
        System.out.println(taskManager.tasks.get(1));

        taskManager.deleteTaskById(0);
        System.out.println(taskManager.getAllTasks());

        System.out.println(taskManager.epics.get(5).getStatusTask());
        taskManager.changeStatusSubtask(6, StatusTask.DONE);
        taskManager.changeStatusSubtask(8, StatusTask.DONE);

        taskManager.changeStatusSubtask(7, StatusTask.DONE);
        System.out.println(taskManager.subtasks.get(7).getStatusTask());
        System.out.println(taskManager.epics.get(5).getStatusTask());

        taskManager.deleteSubtaskById(7);
        System.out.println(taskManager.epics.get(5).getStatusTask());

        taskManager.deleteEpicById(5);
        System.out.println(taskManager.getAllEpics());


        System.out.println(taskManager.subtasks.get(3));
        taskManager.updateSubtask(taskManager.getSubtaskById(3));
        System.out.println(taskManager.subtasks.get(3));
    }
}