package entities.user;

import entities.IdProvider;
import entities.kweet.Kweet;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@NamedQueries({
        @NamedQuery(name="user.findAll",
                query="SELECT u FROM User u"),
        @NamedQuery(name="user.findById",
                query="SELECT u FROM User u WHERE u.id = :id"),
        @NamedQuery(name = "user.findByName",
                query = "SELECT u FROM User u WHERE u.name = :name"),
        @NamedQuery(name = "user.likeintext",
                query = "SELECT u FROM User u WHERE u.name.data LIKE CONCAT('%',:query,'%')")
})
@NamedNativeQueries({
        @NamedNativeQuery(
                name    =   "user.deleteFollower",
                query   =   "DELETE FROM following_follower WHERE follower = ? AND following = ?"
        )
})
@Entity
public class User implements IdProvider {

    @Column(unique = true)
    private int id = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int storeId = 0;


    @NotNull
    @Valid
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private NameUserSaveEntity name;

    @Valid
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private ProfileImage profileImage;

    @Valid
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private BioUserSaveEntity bio;

    @Valid
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private WebsiteUserSaveEntity website;

    @JoinTable(name = "following_follower", joinColumns = {
            @JoinColumn(name = "follower", referencedColumnName = "id", nullable = false)}, inverseJoinColumns = {
            @JoinColumn(name = "following", referencedColumnName = "id", nullable = false)})
    @ManyToMany(cascade = CascadeType.ALL)
    private List<User> following;

    @ManyToMany(mappedBy = "following", cascade = CascadeType.ALL)
    private List<User> followers;

    @OneToMany()
    private List<Kweet> kweets;

    public User() {
        this.following = new ArrayList<>();
        this.followers = new ArrayList<>();
        this.kweets = new ArrayList<>();
        this.createdOn = new Date();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name.toString();
    }

    public void setName(String name) {
        NameUserSaveEntity nameSave = new NameUserSaveEntity();
        nameSave.setData(name);
        this.name = nameSave;
        nameSave.setUser(this);
    }

    public ProfileImage getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(ProfileImage profileImage) {
        this.profileImage = profileImage;
        profileImage.setUser(this);
    }

    public String getBio() {
        return bio.toString();
    }

    public void setBio(String bio) {
        BioUserSaveEntity bioSave = new BioUserSaveEntity();
        bioSave.setData(bio);
        this.bio = bioSave;
        bioSave.setUser(this);
    }

    public String getWebsite() {
        return website.toString();
    }

    public void setWebsite(String website) {
        WebsiteUserSaveEntity websiteSave = new WebsiteUserSaveEntity();
        websiteSave.setData(website);
        this.website = websiteSave;
        websiteSave.setUser(this);
    }

    public List<User> getFollowing() {
        return Collections.unmodifiableList(following);
    }

    @Temporal(value = TemporalType.DATE)
    private Date createdOn;

    public Date getCreatedOn() {
        return createdOn;
    }

    public void addFollowing(User user) {
        if(!this.following.contains(user)){
            this.following.add(user);
            user.addFollower(this);
        }
    }

    public void removeFollowing(User user) {
        if(this.following.contains(user)){
            this.following.remove(user);
            user.removeFollower(this);
        }
    }

    public List<User> getFollowers() {
        return Collections.unmodifiableList(followers);
    }

    public void addFollower(User user) {
        if(!this.followers.contains(user)){
            this.followers.add(user);
            user.addFollowing(this);
        }
    }

    public void removeFollower(User user) {
        if(this.followers.contains(user)){
            this.followers.remove(user);
            user.removeFollowing(this);
        }
    }

    public List<entities.kweet.Kweet> getKweets() {
        return Collections.unmodifiableList(kweets);
    }

    public void addKweet(entities.kweet.Kweet kweet){
        if(!this.kweets.contains(kweet)) {
            this.kweets.add(kweet);
            kweet.setUser(this);
        }
    }
    /*
    public void removeKweet(entities.kweet.Kweet kweet){
        if(this.kweets.contains(kweet)) {
            this.kweets.remove(kweet);
            kweet.setUser(null);
        }
    }
*/
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof User){

            User user = (User) obj;
            return user == this || user.getId() != 0 && this.getId() != 0 && user.getId() == this.getId();
        }
        return false;
    }

    public void setId(int id) {
        this.id = id;
    }
}
