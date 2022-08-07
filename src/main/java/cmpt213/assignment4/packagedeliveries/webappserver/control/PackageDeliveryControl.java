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
 *
 * @author Deborah Wang
 */
public class PackageDeliveryControl {

    public final static int DATA_SAVE = 1;
    public final static int DATA_LOAD = 2;
    public final static int REMOVE = 1;
    public final static int DELIVERY_STATUS = 2;
    public static Gson gson;
    private static File gsonFile;
    public static ArrayList<PackageBase> masterListOfPackages;
    private static ArrayList<PackageBase> overduePackages;
    private static ArrayList<PackageBase> upcomingPackages;
    private LocalDateTime currentTime;

    /**
     * Constructs a PackageDeliveryControl Object.
     * Initializes class fields and also loads in any data from the JSON list.
     */
    public PackageDeliveryControl() {
        this.currentTime = LocalDateTime.now();

        masterListOfPackages = new ArrayList<>();
        overduePackages = new ArrayList<>();
        upcomingPackages = new ArrayList<>();

        String[] pathNames = {"src", "main", "java", "cmpt213", "assignment4", "packagedeliveries", "webappserver", "gson"};
        String path = String.join(Util.fs, pathNames);
        gsonFile = new File(path + Util.fs + "list.json");

        setGsonBuilder();
        arrayData(DATA_LOAD);
    }

    /**
     * Helper method for adjusting a given Package.
     * Will either remove package or change its delivery state.
     *
     * @param pkgIndex          The index of the package in the list.
     * @param option            Remove or set delivery status
     * @param newDeliveryStatus The new delivery status, false if Remove is option.
     */
    public void adjustPackage(int pkgIndex, int option, boolean newDeliveryStatus) {
        if (option == REMOVE) {
            masterListOfPackages.remove(pkgIndex);
        } else if (option == DELIVERY_STATUS) {
            masterListOfPackages.get(pkgIndex).setDeliveryStatus(newDeliveryStatus);
        }
    }

    /**
     * Helper method for loading or saving application data into JSON format.
     *
     * @param dataMode - Determines if method will save data from or load data to JSON.
     */
    public void arrayData(int dataMode) {
        ArrayList<PackageBase> newArray = new ArrayList<>();

        if (dataMode == DATA_SAVE) {
            try {
                FileWriter fileWrite = new FileWriter(gsonFile);
                JsonArray jsonArrayOfPackages = toJsonArray(masterListOfPackages);
                gson.toJson(jsonArrayOfPackages, fileWrite);
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
     * Helper method that returns master list as.
     *
     * @return Returns master list as JSON object.
     */
    public JsonArray getListAsJSON(Util.SCREEN_STATE currentState) {
        updateLists();
        arrayData(DATA_SAVE);
        switch (currentState) {
            case LIST_ALL -> {
                return toJsonArray(masterListOfPackages);
            }
            case UPCOMING -> {
                return toJsonArray(upcomingPackages);
            }
            case OVERDUE -> {
                return toJsonArray(overduePackages);
            }

        }
        return null;
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
     * Helper method to update package's overdue status.
     *
     * @param packageDate The date to be compared against the current time.
     * @return Returns true if package is overdue, false if not.
     */
    private boolean isOverdue(LocalDateTime packageDate) {
        return packageDate.isBefore(currentTime);
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
     * Helper method that converts an ArrayList into a JsonArray
     *
     * @param list List to be converted into JsonArray
     * @return Returns list as a JsonArray.
     */
    private JsonArray toJsonArray(ArrayList<PackageBase> list) {
        JsonArray jsonArray = new JsonArray();
        //convert each object to Json string
        for (PackageBase p : list) {
            if (p != null) {
                jsonArray.add(gson.toJsonTree(p, PackageBase.class));
            }
        }
        return jsonArray;
    }
}
