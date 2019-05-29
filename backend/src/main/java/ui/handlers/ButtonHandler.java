package ui.handlers;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class ButtonHandler implements Serializable {

    protected int counter = 0;

    public String onClick(){
        counter++;
        System.out.println("Je drukte!");
        return "/button.xhtml";
    }

    public int getCounter() {
        return counter;
    }
}
