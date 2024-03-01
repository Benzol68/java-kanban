package com.sysoev.taskmanager.servise;

public class IdGenerate {
    private int idSequence = 0;

     public int generateNewId() {
        return idSequence++;
    }
}
