package iss;

public interface Repository<ID, E extends Entity<ID>> {
    Iterable<E> findAll();

    E findOne(ID id);

    void save(E entity);

    E delete(ID id);
}
