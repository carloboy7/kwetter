package entities;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class SaveEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Temporal(value = TemporalType.DATE)
    private Date createdOn;

    public abstract String toString();

    public Date getCreatedOn() {
        return createdOn;
    }

    public int getId() {
        return id;
    }
}
