package ui.handlers;

import backend.BackendService;
import shared.restModels.Kweet;
import shared.restModels.User;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Named
@SessionScoped
public class SearchPage implements Serializable {
    private String query = "";
    private List<Kweet> kweets = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    @EJB
    private BackendService service;

    public String getQuery() {
        return query;
    }

    public void delete(int id) throws IOException, URISyntaxException {
        Logger.getAnonymousLogger().info("Going to delete kweet with id: " +id);
        service.deleteKweet(id);
        search();
    }
    public void setQuery(String query) {
        this.query = query;
    }


    public void search() {
        try {
            kweets = service.searchKweets(query);
            users = service.searchUsers(query);
        } catch (IOException | URISyntaxException e) {
            Logger.getAnonymousLogger().warning(e.getMessage());
        }
    }

    public List<Kweet> getKweets() {
        return kweets;
    }

    public void setKweets(List<Kweet> kweets) {
        this.kweets = kweets;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
