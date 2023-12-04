package org.example;


public class TaskManager {


    public String executeTaskA() {
        // Additional logic before calling executeTaskB
        String result = executeTaskB();
        // Additional logic after calling executeTaskB
        return result;
    }

    public String executeTaskB() {
        // Some important logic
        return "TaskB Result";
    }

}

