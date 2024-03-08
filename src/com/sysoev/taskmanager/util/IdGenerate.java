package com.sysoev.taskmanager.util;

public class IdGenerate {
    private int idSequence = 0;

     public int generateNewId() {
        return idSequence++;
    }
}

