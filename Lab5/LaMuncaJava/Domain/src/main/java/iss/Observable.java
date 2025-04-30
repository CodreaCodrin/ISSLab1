package iss;

public interface Observable {
    void addWorkerObserver(WorkerObserver observer);
    void addBossObserver(BossObserver observer);

    void removeWorkerObserver(WorkerObserver observer);
    void removeBossObserver(BossObserver observer);

    void notifyBoss(PresentWorker worker);

    void notifyWorker(int idWorker, Task task);
}
