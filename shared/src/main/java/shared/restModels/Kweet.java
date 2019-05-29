package shared.restModels;



import javax.json.bind.annotation.JsonbProperty;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;


public class Kweet extends Hateaos implements UserIdProivder {

    private int id;

    @JsonbProperty(nillable = false)
    @Size(min = 1, max = 160)
    private String text;

    @Valid
    @NotNull
    private User user;

    public int getId() {
        return id;
    }

    public int getUserId(){
        if(user ==null){
            return -1;
        }
        return user.getId();
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
