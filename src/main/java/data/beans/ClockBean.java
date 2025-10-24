package data.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Named
@RequestScoped
public class ClockBean {

    public String getCurrentTime(){

        System.out.println("!@#");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        return LocalDateTime.now().format(formatter);

    }

}
