package cmpt213.assignment4.packagedeliveries.webappserver.controllers;

import cmpt213.assignment4.packagedeliveries.webappserver.control.PackageDeliveryControl;
import cmpt213.assignment4.packagedeliveries.webappserver.model.*;
import cmpt213.assignment4.packagedeliveries.webappserver.model.Util;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
        return control.getListAsJSON(Util.SCREEN_STATE.LIST_ALL).toString();
    }

    @GetMapping("/listOverduePackage")
    public String getOverduePackages() {
        return control.getListAsJSON(Util.SCREEN_STATE.OVERDUE).toString();
    }

    @GetMapping("/listUpcomingPackage")
    public String getUpcomingPackages() {
        return control.getListAsJSON(Util.SCREEN_STATE.UPCOMING).toString();
    }

    @PostMapping("/addPackage")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public String addPackage(@RequestBody String newPackage) {
        PackageBase pkg = deserializePackage(newPackage);
        PackageDeliveryControl.masterListOfPackages.add(pkg);
        return control.getListAsJSON(Util.SCREEN_STATE.LIST_ALL).toString();
    }

    @PostMapping("/removePackage")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public String removePackage(@RequestBody int pkgIndex) {
        control.adjustPackage(pkgIndex, PackageDeliveryControl.REMOVE, false);
        return control.getListAsJSON(Util.SCREEN_STATE.LIST_ALL).toString();
    }

    @PostMapping("/markPackageAsDelivered")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public String markPackageAsDelivered(@RequestBody String pkgChangeContent) {

        JsonArray messageContent = PackageDeliveryControl.gson.fromJson(pkgChangeContent, JsonArray.class);
        int index = PackageDeliveryControl.gson.fromJson(messageContent.get(0), Integer.class);
        boolean newStatus = PackageDeliveryControl.gson.fromJson(messageContent.get(1), Boolean.class);
        control.adjustPackage(index, PackageDeliveryControl.DELIVERY_STATUS, newStatus);
        return control.getListAsJSON(Util.SCREEN_STATE.LIST_ALL).toString();
    }

    @GetMapping("/exit")
    public String exitClient() {
        control.arrayData(PackageDeliveryControl.DATA_SAVE);
        return "Client closed.";
    }

    private PackageBase deserializePackage(String stringPkg) {
        JsonObject jsonPkg = PackageDeliveryControl.gson.fromJson(stringPkg, JsonObject.class);
        String pkgType = jsonPkg.get("type").getAsString();
        return switch (pkgType) {
            case "Book" -> PackageDeliveryControl.gson.fromJson(stringPkg, Book.class);
            case "Perishable" -> PackageDeliveryControl.gson.fromJson(stringPkg, Perishable.class);
            case "Electronic" -> PackageDeliveryControl.gson.fromJson(stringPkg, Electronic.class);
            default -> null;
        };
    }
}
