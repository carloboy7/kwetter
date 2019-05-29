package models;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NamedQuery(name = "Role.name", query = "SELECT r FROM Role r WHERE r.name = :name")
@Table(name = "Roles",
        indexes = {@Index(name = "role_name_index",  columnList="Name", unique = true)})

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, name = "Name")
    @NaturalId
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<User> user  = new ArrayList<>();;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
