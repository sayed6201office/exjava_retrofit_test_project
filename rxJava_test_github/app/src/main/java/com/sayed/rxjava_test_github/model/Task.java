package com.sayed.rxjava_test_github.model;


import java.util.ArrayList;
import java.util.List;

public class Task {

    private String description;
    private boolean isComplete;
    private int priority;

    public Task(String description, boolean isComplete, int priority) {
        this.description = description;
        this.isComplete = isComplete;
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public int getPriority() {
        return priority;
    }

    public static List<Task> createTasksList(){
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task("Take out the trash", true, 3));
        tasks.add(new Task("Walk the dog", false, 2));
        tasks.add(new Task("Make my bed", true, 1));
        tasks.add(new Task("Unload the dishwasher", false, 0));
        tasks.add(new Task("Make dinner", true, 5));

        return tasks;
    }


}

