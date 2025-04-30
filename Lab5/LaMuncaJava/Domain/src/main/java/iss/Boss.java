package iss;

public class Boss extends User{
    private final String name;
    public Boss(Integer id, String username, String password, String userType) {
        super(id, username, password, userType);
        this.name = username;
    }

    public Boss(String username, String password, String userType) {
        super(username, password, userType);
        this.name = username;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
