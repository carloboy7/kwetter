package entities;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class SoftDelete {

    @Column
    private boolean deleted;
}
