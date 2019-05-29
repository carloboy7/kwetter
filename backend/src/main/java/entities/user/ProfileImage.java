package entities.user;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class ProfileImage extends UserSaveEntity {

    @Column
    @NotNull
    private String location;


    @Override
    public String toString() {
        return getLocation();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
