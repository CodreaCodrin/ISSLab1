package iss;

public abstract class User extends Entity<Integer>{
    private String username;
    private String password;
    private final UserType userType;

    public User(Integer id, String username, String password, String userType) {
        this.setId(id);
        this.username = username;
        this.password = password;
        this.userType = UserType.valueOf(userType);
    }

    public User(String username, String password, String userType) {
        this.username = username;
        this.password = password;
        this.userType = UserType.valueOf(userType);
    }

    public UserType getUserType() {
        return userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return username + "->" + password;
    }
}
