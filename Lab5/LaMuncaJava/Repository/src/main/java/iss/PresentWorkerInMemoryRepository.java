package iss;

import java.util.HashMap;
import java.util.Map;

public class PresentWorkerInMemoryRepository implements PresentWorkersRepository {
    private final Map<Integer , PresentWorker> list = new HashMap<>();

    public PresentWorkerInMemoryRepository() {}

    @Override
    public Iterable<PresentWorker> findAll() {
        return list.values();
    }

    @Override
    public PresentWorker findOne(Integer integer) {
        if (list.containsKey(integer)) {
            return list.get(integer);
        }
        return null;
    }

    @Override
    public void save(PresentWorker entity) {
        if (list.containsKey(entity.getId())) {
            throw new IllegalArgumentException("PresentWorker already exists");
        }
        list.put(entity.getId(), entity);
    }

    @Override
    public PresentWorker delete(Integer integer) {
        return list.remove(integer);
    }
}
