package entities.user;

import entities.SaveEntity;

import javax.persistence.*;

@MappedSuperclass
public abstract class UserSaveEntity extends SaveEntity {

    @ManyToOne
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
