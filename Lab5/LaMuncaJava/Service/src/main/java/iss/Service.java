package iss;

import java.util.ArrayList;
import java.util.List;

public class Service implements Observable{
    private final List<WorkerObserver> workerObservers = new ArrayList<>();
    private final List<BossObserver> bossObservers = new ArrayList<>();

    private final UserRepository userRepository;
    private final PresentWorkersRepository presentWorkerRepository;
    public Service(UserRepository userRepository, PresentWorkersRepository presentWorkerRepository) {
        this.userRepository = userRepository;
        this.presentWorkerRepository = presentWorkerRepository;
    }

    public Boss loginBoss(String username, String password){
        Boss boss;
        try{
            boss = (Boss) userRepository.findByUsernameAndPassword(username, password);
        } catch (Exception e) {
            throw new IllegalArgumentException("User not found");
        }
        if(boss == null){
            throw new IllegalArgumentException("User not found");
        }
        return boss;
    }

    public Worker loginWorker(String username, String password){
        Worker worker;
        try{
            worker = (Worker) userRepository.findByUsernameAndPassword(username, password);
        } catch (Exception e) {
            throw new IllegalArgumentException("User not found");
        }
        if(worker == null){
            throw new IllegalArgumentException("User not found");
        }

        PresentWorker presentWorker = new PresentWorker(worker);
        presentWorkerRepository.save(presentWorker);
        notifyBoss(presentWorker);
        return worker;
    }

    public void logoutWorker(int id){
        notifyBoss(presentWorkerRepository.findOne(id));
        presentWorkerRepository.delete(id);
    }

    public void sendTask(int idWorker, Task task){
        notifyWorker(idWorker, task);
    }

    public void addWorker(String username, String password){ addUser(username, password, "WORKER"); }
    public void addBoss(String username, String password){ addUser(username, password, "BOSS"); }
    private void addUser(String username, String password, String type) {
        UserType userType;
        try{
            userType = UserType.valueOf(type);
        }
        catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Invalid user type: " + type);
        }

        switch (userType){
            case BOSS -> userRepository.save(new Boss(username, password, type));
            case WORKER -> userRepository.save(new Worker(username, password, type));
        }
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
    public Iterable<PresentWorker> getAllPresentWorkers() {
        return presentWorkerRepository.findAll();
    }

    @Override
    public void addWorkerObserver(WorkerObserver observer) {
        workerObservers.add(observer);
    }

    @Override
    public void addBossObserver(BossObserver observer) {
        bossObservers.add(observer);
    }

    @Override
    public void removeWorkerObserver(WorkerObserver observer) {
        workerObservers.remove(observer);
    }

    @Override
    public void removeBossObserver(BossObserver observer) {
        bossObservers.remove(observer);
    }

    @Override
    public void notifyBoss(PresentWorker worker) {
        for (BossObserver observer : bossObservers) {
            observer.update(worker);
        }
    }

    @Override
    public void notifyWorker(int idWorker, Task task) {
        for (WorkerObserver observer : workerObservers) {
            observer.update(idWorker, task);
        }
    }
}
