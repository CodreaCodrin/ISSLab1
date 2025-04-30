package iss;

import java.util.ArrayList;
import java.util.List;

public class Worker extends User{
    private final String name;
    private final List<Task> tasks = new ArrayList<>();

    public Worker(Integer id, String username, String password, String userType) {
        super(id, username, password, userType);
        this.name = username;
    }

    public Worker(String username, String password, String userType) {
        super(username, password, userType);
        this.name = username;
    }

    public Iterable<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void finishTask(Task task) {
        tasks.remove(task);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
