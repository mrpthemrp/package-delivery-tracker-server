package cmpt213.assignment4.packagedeliveries.webappserver.control;

import cmpt213.assignment4.packagedeliveries.webappserver.gson.extras.RuntimeTypeAdapterFactory;
import cmpt213.assignment4.packagedeliveries.webappserver.model.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class creates a PackageDeliveryControl object which
 * manages data for the program. This class handles data loading and saving.
 * @author Deborah Wang
 */
public class PackageDeliveryControl {

    public final static int REMOVE = 1;
    public final static int DELIVERY_STATUS = 2;
    public final static int DATA_SAVE = 1;
    private final static int DATA_LOAD = 2;
    public static Gson gson;
    private static File gsonFile;
    private static ArrayList<PackageBase> masterListOfPackages;
    private static ArrayList<PackageBase> upcomingPackages;
    private static ArrayList<PackageBase> overduePackages;
    private final PackageFactory pkgFactory;
    private LocalDateTime currentTime;

    /**
     * Constructs a PackageDeliveryControl Object.
     * Initializes class fields and also loads in any data from the JSON list.
     */
    public PackageDeliveryControl() {

        this.pkgFactory = new PackageFactory();
        this.currentTime = LocalDateTime.now();

        masterListOfPackages = new ArrayList<>();
        upcomingPackages = new ArrayList<>();
        overduePackages = new ArrayList<>();

        String[] pathNames = {"src", "cmpt213", "assignment4", "packagedeliveries","client", "gson"};
        String path = String.join(Util.fs, pathNames);
        gsonFile = new File(path + Util.fs + "list.json");

        setGsonBuilder();
        arrayData(DATA_LOAD);
    }

    /**
     * Helper method that sets up the GSON logic for this application.
     * Registers subclasses of PackageBase into the RuntimeTypeAdapter.
     */
    private void setGsonBuilder() {
        RuntimeTypeAdapterFactory<PackageBase> packageAdapterFactory = RuntimeTypeAdapterFactory.of(PackageBase.class, "type")
                .registerSubtype(Book.class, "Book")
                .registerSubtype(Perishable.class, "Perishable")
                .registerSubtype(Electronic.class, "Electronic");

        gson = new GsonBuilder()
                .registerTypeAdapter(DateTimeFormatter.class, new TypeAdapter<DateTimeFormatter>() {
                    @Override
                    public void write(JsonWriter jsonWriter, DateTimeFormatter dateTimeFormatter) throws IOException {
                        jsonWriter.value(dateTimeFormatter.toString());
                    }

                    @Override
                    public DateTimeFormatter read(JsonReader jsonReader) throws IOException {
                        return DateTimeFormatter.ofPattern(jsonReader.nextString());
                    }
                })
                .registerTypeAdapter(LocalDateTime.class, new TypeAdapter<LocalDateTime>() {
                    @Override
                    public void write(JsonWriter jsonWriter, LocalDateTime localDateTime) throws IOException {
                        jsonWriter.value(localDateTime.toString());
                    }

                    @Override
                    public LocalDateTime read(JsonReader jsonReader) throws IOException {
                        return LocalDateTime.parse(jsonReader.nextString());
                    }
                })
                .registerTypeAdapterFactory(packageAdapterFactory)
                .create();
    }

    /**
     * Helper method for loading or saving application data into JSON format.
     * @param dataMode - Determines if method will save data from or load data to JSON.
     */
    public void arrayData(int dataMode) {
        ArrayList<PackageBase> newArray = new ArrayList<>();

        if (dataMode == DATA_SAVE) {
            try {
                FileWriter fileWrite = new FileWriter(gsonFile);
                JsonArray toJsonArray = new JsonArray();
                //convert each object to Json string
                for (PackageBase p : masterListOfPackages) {
                    if (p != null) {
                        toJsonArray.add(gson.toJsonTree(p, PackageBase.class));
                    }
                }
                gson.toJson(toJsonArray, fileWrite);
                fileWrite.close();
            } catch (IOException e) {
                System.out.println("Could not save data!");
            }

        } else if (dataMode == DATA_LOAD) {
            if (gsonFile.exists()) {
                try {
                    FileReader fileRead = new FileReader(gsonFile);
                    JsonArray jsonArray = gson.fromJson(fileRead, JsonArray.class);
                    if (jsonArray != null) {
                        for (int i = 0; i < jsonArray.size(); i++) {
                            newArray.add(gson.fromJson(jsonArray.get(i), PackageBase.class));
                        }
                    }
                    fileRead.close();
                } catch (IOException e) {
                    System.out.println("Could not load data!");
                }
            }

            masterListOfPackages = newArray;
        }
    }

    /**
     * Method that creates a new Package using the PackageFactory class.
     * Updates lists after package creation.
     * @param name Name of the package.
     * @param notes Any notes for the package.
     * @param price Price of the package.
     * @param weight Weight of the package.
     * @param date Expected Delivery Date of the package.
     * @param extraField Extra field of the package, in String form already.
     * @param type Subclass type of the package, uses PackageFactory.PackageType.
     */
    public void createPackage(String name, String notes, double price, double weight, LocalDateTime date,
                              String extraField, PackageFactory.PackageType type) {
        PackageBase newPackage = pkgFactory.getInstance(type, name, notes, price, weight, date, extraField);
        masterListOfPackages.add(newPackage);
        updateLists();
    }

    /**
     * Helper method for adjusting a given Package.
     * Will either remove package or change its delivery state.
     * @param pkg The package to be adjusted.
     * @param option Which adjustment method is selected, based on constants from this class.
     * @param newDeliveryStatus The new delivery status of package, false if option is to remove.
     */
    public void adjustPackage(PackageBase pkg, int option, boolean newDeliveryStatus) {
        if (option == REMOVE) {
            masterListOfPackages.remove(pkg);
        } else if (option == DELIVERY_STATUS) {
            pkg.setDeliveryStatus(newDeliveryStatus);
        }
        updateLists();
    }

    /**
     * Helper method to update package's overdue status.
     * @param packageDate The date to be compared against the current time.
     * @return Returns true if package is overdue, false if not.
     */
    private boolean isOverdue(LocalDateTime packageDate) {
        return packageDate.isBefore(currentTime);
    }

    /**
     * Helper method to update all lists; lists are also sorted after update.
     * Current time is updated to LocalDateTime.now() here.
     */
    public final void updateLists() {
        this.currentTime = LocalDateTime.now();
        //reset upcoming and overdue
        ArrayList<PackageBase> tempMasterList = new ArrayList<>();
        upcomingPackages = new ArrayList<>();
        overduePackages = new ArrayList<>();

        //add necessary packages to lists
        for (PackageBase tempPkg : masterListOfPackages) {
            tempMasterList.add(tempPkg);
            if (!tempPkg.isDelivered()) {
                if (isOverdue(tempPkg.getExpectedDeliveryDate())) {
                    if (!overduePackages.contains(tempPkg)) {
                        overduePackages.add(tempPkg);
                    }
                } else {
                    if (!upcomingPackages.contains(tempPkg)) {
                        upcomingPackages.add(tempPkg);
                    }
                }

            }
        }
        masterListOfPackages = tempMasterList;

        //sort
        Collections.sort(masterListOfPackages);
        Collections.sort(upcomingPackages);
        Collections.sort(overduePackages);
    }

    /**
     * Helper method that allows the UI to access the lists.
     * @param currentState Current state of UI tells method which list to return.
     * @return Returns an ArrayList based on the current state.
     */
    public ArrayList<PackageBase> getAListOfPackages(Util.SCREEN_STATE currentState) {
        switch (currentState) {
            case LIST_ALL -> {
                return masterListOfPackages;
            }
            case UPCOMING -> {
                return upcomingPackages;
            }
            case OVERDUE -> {
                return overduePackages;
            }
        }
        return null;
    }
}
