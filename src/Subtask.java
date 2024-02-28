

public class Subtask extends Task {

    private int epicId;


    public Subtask(String name, String description, StatusTask statusTask, int epicId){
        super(name, description, statusTask);
        this.epicId = epicId;
    }


    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
}