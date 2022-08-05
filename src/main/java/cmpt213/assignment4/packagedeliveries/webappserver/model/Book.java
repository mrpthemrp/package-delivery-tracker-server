package cmpt213.assignment4.packagedeliveries.webappserver.model;

import java.time.LocalDateTime;

/**
 * A concrete implementation {@link PackageBase}, models a package that
 * holds a book. Contains extra field that is a String, acts as the author name.
 *
 * @author Deborah Wang
 */
public class Book extends PackageBase {
    private final String authorName;

    /**
     * Constructor of the Book object.
     *
     * @param name       See {@link PackageBase#PackageBase(String, String, double, double, LocalDateTime)}
     * @param notes      See {@link PackageBase#PackageBase(String, String, double, double, LocalDateTime)}
     * @param price      See {@link PackageBase#PackageBase(String, String, double, double, LocalDateTime)}
     * @param weight     See {@link PackageBase#PackageBase(String, String, double, double, LocalDateTime)}
     * @param date       See {@link PackageBase#PackageBase(String, String, double, double, LocalDateTime)}
     * @param authorName Holds the author name of the Book package.
     */
    public Book(String name, String notes, double price,
                double weight, LocalDateTime date, String authorName) {

        super(name, notes, price, weight, date);
        this.authorName = authorName;
    }

    /**
     * Converts object's fields into a String that
     * conveys what the fields are (units are included).
     *
     * @return A string of the object's fields, ready for print.
     */
    @Override
    public String toString() {

        String deliveryStatus = "Not delivered.";

        if (this.isDelivered) {
            deliveryStatus = "Delivered.";
        }

        return ("Package Name: " + this.name + "\n" +
                "Package Type: Book\n" +
                "Notes: " + this.notes + "\n" +
                "Price (CAD): $" + this.price + "\n" +
                "Weight (kg): " + this.weight + "kg\n" +
                "Expected Delivery Date: " +
                this.expectedDeliveryDate.format(dateFormat) +
                "\nDelivery Status: " + deliveryStatus + "\n" +
                "Author name: " + this.authorName + "\n");
    }

    /**
     * Getter for the author name.
     *
     * @return Returns the author's name.
     */
    public String getAuthorName() {
        return authorName;
    }
}
