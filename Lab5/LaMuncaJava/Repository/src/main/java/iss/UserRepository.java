package iss;

public interface UserRepository extends Repository<Integer, User> {
    public User findByUsernameAndPassword(String username, String password);
}
