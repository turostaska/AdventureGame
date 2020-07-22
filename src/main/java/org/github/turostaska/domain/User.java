package org.github.turostaska.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;


@Entity
public class User {
    private String userName;
    private String password;
    private String email;

    @Id
    @GeneratedValue
    private int ID;

    @OneToOne
    private Player player;

    public User(String userName, String password, String email) {
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    public void createPlayer() {
        if (player != null)
            return;

        this.player = new Player(this);
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
