package cmpt213.assignment4.packagedeliveries.webappserver.model;

import java.time.LocalDateTime;

/**
 * A concrete implementation {@link PackageBase}, models a package that
 * holds a perishable. Contains extra field that is a LocalDateTime object, acts as expiry date.
 *
 * @author Deborah Wang
 */
public class Perishable extends PackageBase {
    private final LocalDateTime expiryDate;

    /**
     * Constructor of the Perishable object.
     *
     * @param name         See {@link PackageBase#PackageBase(String, String, double, double, LocalDateTime)}
     * @param notes        See {@link PackageBase#PackageBase(String, String, double, double, LocalDateTime)}
     * @param price        See {@link PackageBase#PackageBase(String, String, double, double, LocalDateTime)}
     * @param weight       See {@link PackageBase#PackageBase(String, String, double, double, LocalDateTime)}
     * @param deliveryDate See {@link PackageBase#PackageBase(String, String, double, double, LocalDateTime)}
     * @param expiryDate   Holds the perishable's expiry date.
     */
    public Perishable(String name, String notes, double price,
                      double weight, LocalDateTime deliveryDate, LocalDateTime expiryDate) {

        super(name, notes, price, weight, deliveryDate);
        this.expiryDate = expiryDate;
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
                "Package Type: Perishable\n" +
                "Notes: " + this.notes + "\n" +
                "Price (CAD): $" + this.price + "\n" +
                "Weight (kg): " + this.weight + "kg\n" +
                "Expected Delivery Date: " +
                this.expectedDeliveryDate.format(dateFormat) +
                "\nDelivery Status: " + deliveryStatus + "\n" +
                "Product Expiry Date: " + this.expiryDate.format(dateFormat) + "\n");
    }

    /**
     * Getter for the expiry date.
     *
     * @return Returns the expiry date.
     */
    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }
}
