package org.github.turostaska.domain;

import javax.persistence.*;


@Entity
public class User {
    private String userName;
    private String password;
    private String email;

    @Id
    @GeneratedValue
    private Long ID;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    //@JoinColumn(name = "playerId", referencedColumnName = "ID")
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

    public Long getID() {
        return ID;
    }

    public Player getPlayer() {
        return player;
    }

    public String getPassword() {
        return password;
    }
}
