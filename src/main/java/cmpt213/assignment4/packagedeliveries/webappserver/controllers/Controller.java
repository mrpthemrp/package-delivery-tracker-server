package cmpt213.assignment4.packagedeliveries.webappserver.controllers;

import cmpt213.assignment4.packagedeliveries.webappserver.control.PackageDeliveryControl;
import cmpt213.assignment4.packagedeliveries.webappserver.model.*;
import cmpt213.assignment4.packagedeliveries.webappserver.model.Util;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Rest Controller class. Defines methods that handle requests by client.
 *
 * @author Deborah Wang
 */
@RestController
public class Controller {
    private final PackageDeliveryControl control = new PackageDeliveryControl();

    /**
     * Method handles client starting connection to server.
     *
     * @return Returns a message that tells the client the server is up.
     */
    @GetMapping("/ping")
    public String getPingMessage() {
        control.arrayData(PackageDeliveryControl.DATA_LOAD);
        return "System is up!";
    }

    /**
     * Method handles client request for a list of all packages.
     *
     * @return Returns a String in JSON format of the list to all packages.
     */
    @GetMapping("/listAll")
    public String getAllPackages() {
        return control.getListAsJSON(Util.SCREEN_STATE.LIST_ALL).toString();
    }

    /**
     * Method handles client request for a list of overdue packages.
     *
     * @return Returns a String in JSON format of the list to overdue packages.
     */
    @GetMapping("/listOverduePackage")
    public String getOverduePackages() {
        return control.getListAsJSON(Util.SCREEN_STATE.OVERDUE).toString();
    }

    /**
     * Method handles client request for a list of upcoming packages.
     *
     * @return Returns a String in JSON format of the list to upcoming packages.
     */
    @GetMapping("/listUpcomingPackage")
    public String getUpcomingPackages() {
        return control.getListAsJSON(Util.SCREEN_STATE.UPCOMING).toString();
    }

    /**
     * Method handles client request to add a package.
     *
     * @param newPackage String in JSON format of the Package to be added.
     * @return Returns a String in JSON format of updated list to all packages.
     */
    @PostMapping("/addPackage")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public String addPackage(@RequestBody String newPackage) {
        PackageBase pkg = deserializePackage(newPackage);
        PackageDeliveryControl.masterListOfPackages.add(pkg);
        return control.getListAsJSON(Util.SCREEN_STATE.LIST_ALL).toString();
    }

    /**
     * Method handles client request to remove a package.
     *
     * @param stringContents Index of package to be removed.
     * @return Returns a String in JSON format of updated list to all packages.
     */
    @PostMapping("/removePackage")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public String removePackage(@RequestBody String stringContents) {
        JsonArray messageContent = PackageDeliveryControl.gson.fromJson(stringContents, JsonArray.class);
        int pkgIndex = PackageDeliveryControl.gson.fromJson(messageContent.get(0),Integer.class);
        Util.SCREEN_STATE state = Enum.valueOf(Util.SCREEN_STATE.class,PackageDeliveryControl.gson.fromJson(messageContent.get(1),
                String.class));
        control.adjustPackage(pkgIndex, PackageDeliveryControl.REMOVE, false, state);
        return control.getListAsJSON(Util.SCREEN_STATE.LIST_ALL).toString();
    }

    /**
     * Method handles client request to change package delivery status.
     *
     * @param pkgChangeContent A string Array of two values, an index and a boolean.
     * @return Returns a String in JSON format of updated list to all packages.
     */
    @PostMapping("/markPackageAsDelivered")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public String markPackageAsDelivered(@RequestBody String pkgChangeContent) {
        JsonArray messageContent = PackageDeliveryControl.gson.fromJson(pkgChangeContent, JsonArray.class);
        int index = PackageDeliveryControl.gson.fromJson(messageContent.get(0), Integer.class);
        boolean newStatus = PackageDeliveryControl.gson.fromJson(messageContent.get(1), Boolean.class);
        Util.SCREEN_STATE state = Enum.valueOf(Util.SCREEN_STATE.class,PackageDeliveryControl.gson.fromJson(messageContent.get(2),
                String.class));
        control.adjustPackage(index, PackageDeliveryControl.DELIVERY_STATUS, newStatus,state);
        return control.getListAsJSON(Util.SCREEN_STATE.LIST_ALL).toString();
    }

    /**
     * Method that handles client request to save data.
     *
     * @return A String message that tells client the data has been saved.
     */
    @GetMapping("/exit")
    public String exitClient() {
        control.arrayData(PackageDeliveryControl.DATA_SAVE);
        return "Client data saved.";
    }

    /**
     * Helper method to deserialize JSON objects into PackageBase objects.
     *
     * @param stringPkg A String in serialized JSON format of the package.
     * @return Returns a deserialized JSON object as PackageBase.
     */
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
