package iss;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task extends Entity<Integer>{
    private String description;
    private TaskStatus status;
    private final LocalDateTime time;

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Task(Integer id, String description, TaskStatus status, LocalDateTime time) {
        this.setId(id);
        this.description = description;
        this.status = status;
        this.time = time;
    }

    public Task(Integer id, String description, String status, String time) {
        this.setId(id);
        this.description = description;
        this.status = TaskStatus.valueOf(status);
        this.time = LocalDateTime.parse(time, formatter);
    }

    public Task(String description){
        this.description = description;
        this.status = TaskStatus.PENDING;
        this.time = LocalDateTime.now();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getStringTime() {
        return time.format(formatter);
    }

    @Override
    public String toString() {
        return description + time.format(formatter);
    }
}
