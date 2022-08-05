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

/**
 * This class creates a PackageDeliveryControl object which
 * manages data for the program. This class handles data loading and saving.
 *
 * @author Deborah Wang
 */
public class PackageDeliveryControl {

    public final static int DATA_SAVE = 1;
    private final static int DATA_LOAD = 2;
    public static Gson gson;
    private static File gsonFile;
    private static ArrayList<PackageBase> listOfPackages;

    /**
     * Constructs a PackageDeliveryControl Object.
     * Initializes class fields and also loads in any data from the JSON list.
     */
    public PackageDeliveryControl() {

        listOfPackages = new ArrayList<>();

        String[] pathNames = {"src", "cmpt213", "assignment4", "packagedeliveries", "webappserver", "gson"};
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
     *
     * @param dataMode - Determines if method will save data from or load data to JSON.
     */
    public void arrayData(int dataMode) {
        ArrayList<PackageBase> newArray = new ArrayList<>();

        if (dataMode == DATA_SAVE) {
            try {
                FileWriter fileWrite = new FileWriter(gsonFile);
                JsonArray toJsonArray = new JsonArray();
                //convert each object to Json string
                for (PackageBase p : listOfPackages) {
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

            listOfPackages = newArray;
        }
    }

    /**
     * Helper method that allows the UI to access the master list.
     *
     * @return Returns master list.
     */
    public ArrayList<PackageBase> getListOfPackages() {
        return listOfPackages;
    }
}
