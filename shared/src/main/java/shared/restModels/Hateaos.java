package shared.restModels;

import javax.json.bind.annotation.JsonbProperty;
import java.util.ArrayList;
import java.util.List;

public abstract class Hateaos {
    private String url;
    private String resource;
    private List<String> nextUrl;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public List<String> getNextUrl() {
        return nextUrl;
    }

    public void setNextUrl(List<String> nextUrl) {
        this.nextUrl = nextUrl;
    }
}
