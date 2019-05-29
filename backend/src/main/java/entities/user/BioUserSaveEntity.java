package entities.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class BioUserSaveEntity extends UserSaveEntity{

    @Column
    @NotNull
    @Size(min = 0, max = 160)
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
