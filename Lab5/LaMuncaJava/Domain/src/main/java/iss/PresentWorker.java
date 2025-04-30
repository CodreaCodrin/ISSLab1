package iss;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PresentWorker extends Entity<Integer>{
    private final Worker worker;

    private final LocalDateTime time;
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    public PresentWorker(Worker worker) {
        this.worker = worker;
        this.setId(worker.getId());
        this.time = LocalDateTime.now();
    }

    private LocalDateTime getTime(){
        return time;
    }

    public String getStringTime() {
        return time.format(formatter);
    }

    public Worker getWorker() {
        return worker;
    }

    @Override
    public String toString() {
        return worker.getName() + " " + time.format(formatter);
    }
}
