package entities.kweet;

import entities.IdProvider;
import entities.SoftDelete;
import entities.user.User;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;

@NamedQueries({
        @NamedQuery(name="kweet.findAll",
                query="SELECT k FROM Kweet k"),
        @NamedQuery(name="kweet.findById",
                query="SELECT k FROM Kweet k WHERE k.id = :id"),
        @NamedQuery(name="kweet.findByUser",
                query="SELECT k FROM Kweet k WHERE k.user = :user"),
        @NamedQuery(name = "kweet.likeintext",
                query = "SELECT k FROM Kweet k WHERE k.text.data LIKE CONCAT('%',:query,'%')")
})
@Entity
public class Kweet implements IdProvider {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id = 0;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @Valid
    @NotNull
    private KweetTextSaveEntity text;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @NotNull
    private User user;

    @Temporal(value = TemporalType.DATE)
    private Date createdOn;

    public String getText() {
        return text.toString();
    }

    public void setText(String text) {
        KweetTextSaveEntity column = new KweetTextSaveEntity();
        column.setData(text);
        this.text = column;
        this.text.setKweet(this);
        this.text.setUser(this.user);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        if(user != this.user) {
            this.user = user;
            this.user.addKweet(this);
        }
    }


    @Override
    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Kweet){
            Kweet user = (Kweet) obj;
            return user == this || user.getId() != 0 && this.getId() != 0 && user.getId() == this.getId();
        }
        return false;
    }
}
