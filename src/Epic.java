import java.util.ArrayList;



public class Epic extends Task {
    private ArrayList<Integer> epicSubtasks;

    public Epic(String name, String description) {
        super(name, description, StatusTask.NEW);
        epicSubtasks = new ArrayList<>();
    }



    public ArrayList<Integer> getEpicSubtasks() {
        return epicSubtasks;
    }

    public void setEpicSubtasks(ArrayList<Integer> epicSubtasks) {
        this.epicSubtasks = epicSubtasks;
    }

    public void addNewSubtaskInEpic(int id) {
        epicSubtasks.add(id);
    }

    public void deleteSubtask(int id) {
        epicSubtasks.remove(id);
    }

    public void deleteAllSubtasksOfEpic() {
        epicSubtasks.clear();
    }


}