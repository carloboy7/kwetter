package models;

import org.hibernate.annotations.NaturalId;

import javax.ejb.Stateful;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Table(name = "Users",
        indexes = {@Index(name = "username_index",  columnList="Username", unique = true)})
@NamedQuery(name = "SimpleUser.byName", query = "SELECT u FROM User u WHERE u.username = :name")
@Entity
@Stateful
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id = 0;

    @ManyToMany
    @JoinTable(name = "Groups_auth",
            joinColumns = @JoinColumn(referencedColumnName="username", name = "Username"),
            inverseJoinColumns = @JoinColumn(referencedColumnName = "name", name ="GroupId")
    )
    private List<Role> roles = new ArrayList<>();

    @Column(name = "Username")
    @NaturalId
    private String username;

    @Column(name = "Password")

    @JsonbTransient
    private String password;

    @Column
    @Temporal(value = TemporalType.DATE)
    private Date createdOn;

    public Date getCreatedOn() {
        return createdOn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public User(){
        this.createdOn = new Date();
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
}
