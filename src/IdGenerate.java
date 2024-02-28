public class IdGenerate {
    static int idSequence = 0;

    static int generateNewId() {
        return idSequence++;
    }
}
