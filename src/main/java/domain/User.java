package domain;

public class User {
    private String userName;
    private String password;
    private String email;
    private int ID;
    private Player player;

    public User(String userName, String password, String email) {
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public int getID() {
        return ID;
    }

    public Player getPlayer() {
        return player;
    }

    public String getPassword() {
        return password;
    }
}
