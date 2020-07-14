package domain;

public class User {
    private String userName;
    private String password;
    private String email;
    private int ID;

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

    private Player player;
}
