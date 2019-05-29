package entities.kweet;

import entities.SimpleSaveColumn;
import entities.user.UserSaveEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class KweetTextSaveEntity extends UserSaveEntity {

    @Column
    @NotNull
    @Size(min = 1, max = 160)
    private String data;

    @ManyToOne
    private Kweet kweet;


    public void setData(String data) {
        this.data = data;
    }

    public Kweet getKweet() {
        return kweet;
    }

    public void setKweet(Kweet kweet) {
        this.kweet = kweet;
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
