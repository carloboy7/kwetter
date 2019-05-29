package rest_models;

import shared.restModels.Hateaos;

import javax.validation.constraints.*;

public class UserRegistration extends Hateaos {

    public UserRegistration(){
        setResource("authentication");
    }

    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    @NotNull
    @Pattern(regexp=".+@.+\\..+", message="Please provide a valid email address")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotNull
    @Size(min = 1, max = 255)
    private String password;

    @Min(1)
    @Max(12)
    private int profileImageId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getProfileImageId() {
        return profileImageId;
    }

    public void setProfileImageId(int profileImageId) {
        this.profileImageId = profileImageId;
    }
}
