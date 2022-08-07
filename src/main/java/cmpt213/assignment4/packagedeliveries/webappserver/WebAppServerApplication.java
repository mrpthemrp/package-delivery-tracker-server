package cmpt213.assignment4.packagedeliveries.webappserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Web server class. Connects to Spring.
 *
 * @author Deborah Wang
 */
@SpringBootApplication
public class WebAppServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebAppServerApplication.class, args);
    }

}
