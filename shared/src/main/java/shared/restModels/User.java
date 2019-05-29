package shared.restModels;


import javax.json.bind.annotation.JsonbProperty;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


public class User extends Hateaos implements UserIdProivder  {

    @NotNull
    @Min(1)
    private int id;

    @JsonbProperty(nillable = true)
    @Size(min = 1, max = 160)
    private String name;

    @JsonbProperty(nillable = true)
    @Valid
    private ProfileImage profileImage;

    @JsonbProperty(nillable = true)
    @Size(max = 160)
    private String bio;

    @Size(max = 160)
    @JsonbProperty(nillable = true)
    private String website;


    public int getUserId(){
        return getId();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProfileImage getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(ProfileImage profileImage) {
        this.profileImage = profileImage;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }


    public void setCreated(Date created) {

    }
}
