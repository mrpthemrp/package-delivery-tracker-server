package cmpt213.assignment4.packagedeliveries.webappserver.controllers;

import cmpt213.assignment4.packagedeliveries.webappserver.control.PackageDeliveryControl;
import cmpt213.assignment4.packagedeliveries.webappserver.model.PackageBase;
import cmpt213.assignment4.packagedeliveries.webappserver.model.Util;
import com.google.gson.JsonArray;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class Controller {
    private final PackageDeliveryControl control = new PackageDeliveryControl();

    @GetMapping("/ping")
    public String getPingMessage() {
        return "System is up!";
    }

    @GetMapping("/listAll")
    public JsonArray getAllPackages() {
        return control.getListAsJSON();
    }

    @GetMapping("/listOverduePackages")
    public ArrayList<PackageBase> getOverduePackages() {
        return control.getAListOfPackages(Util.SCREEN_STATE.OVERDUE);
    }

    @GetMapping("/listUpcomingPackages")
    public ArrayList<PackageBase> getUpcomingPackages() {
        return control.getAListOfPackages(Util.SCREEN_STATE.UPCOMING);
    }

    @GetMapping("/exit")
    public void exitClient() {
        control.arrayData(PackageDeliveryControl.DATA_SAVE);
    }
}
