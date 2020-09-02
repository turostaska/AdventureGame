package org.github.turostaska.adventuregame.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "REGISTEREDUSER")
@Entity
@NoArgsConstructor
public class User {
    @Column(unique = true)
    @Getter @Setter private String userName;

    @JsonIgnore
    @Getter @Setter
    private String password;

    @Column(unique = true)
    @Getter @Setter private String email;

    @Id  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="IDSEQ")
    @SequenceGenerator(name="IDSEQ", sequenceName="IDSEQ", allocationSize=1)
    @Getter @Setter
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    @JsonManagedReference
    @Getter @Setter
    private Player player;

    public User(String userName, String password, String email) {
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    public Player createPlayer() {
        if (player == null)
            this.player = new Player(this);

        return this.player;
    }

}
