package cmpt213.assignment4.packagedeliveries.webappserver.controllers;

import cmpt213.assignment4.packagedeliveries.webappserver.WebAppServerApplication;
import cmpt213.assignment4.packagedeliveries.webappserver.control.PackageDeliveryControl;
import cmpt213.assignment4.packagedeliveries.webappserver.model.PackageBase;
import cmpt213.assignment4.packagedeliveries.webappserver.model.Util;
import com.google.gson.JsonArray;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class Controller {
    private final PackageDeliveryControl control = new PackageDeliveryControl();

    @GetMapping("/ping")
    public String getPingMessage() {
        control.arrayData(PackageDeliveryControl.DATA_LOAD);
        return "System is up!";
    }

    @GetMapping("/listAll")
    public String getAllPackages() {
        System.out.println(control.getListAsJSON());
        return control.getListAsJSON().toString();
    }

    @GetMapping("/listOverduePackages")
    public ArrayList<PackageBase> getOverduePackages() {
        return control.getAListOfPackages(Util.SCREEN_STATE.OVERDUE);
    }

    @GetMapping("/listUpcomingPackages")
    public String getUpcomingPackages() {
        return control.getAListOfPackages(Util.SCREEN_STATE.UPCOMING).toString();
    }

    @GetMapping("/exit")
    public String exitClient() {
        control.arrayData(PackageDeliveryControl.DATA_SAVE);
        return "Client closed.";
    }
}
