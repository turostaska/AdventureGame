package org.github.turostaska.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Table(name = "REGISTEREDUSER")
@Entity
@NoArgsConstructor
public class User {
    @Getter @Setter private String userName;

    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Getter @Setter
    private String password;

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

    public void createPlayer() {
        if (player != null)
            return;

        this.player = new Player(this);
    }

}
