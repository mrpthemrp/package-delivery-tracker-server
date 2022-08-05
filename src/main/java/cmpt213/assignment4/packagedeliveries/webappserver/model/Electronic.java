package cmpt213.assignment4.packagedeliveries.webappserver.model;

import java.time.LocalDateTime;

/**
 * A concrete implementation {@link PackageBase}, models a package that
 * holds an electronic. Contains extra field that is a double, acts as the handling fee.
 *
 * @author Deborah Wang
 */
public class Electronic extends PackageBase {
    private final double handleFee;

    /**
     * Constructor of the Electronic object.
     *
     * @param name      See {@link PackageBase#PackageBase(String, String, double, double, LocalDateTime)}
     * @param notes     See {@link PackageBase#PackageBase(String, String, double, double, LocalDateTime)}
     * @param price     See {@link PackageBase#PackageBase(String, String, double, double, LocalDateTime)}
     * @param weight    See {@link PackageBase#PackageBase(String, String, double, double, LocalDateTime)}
     * @param date      See {@link PackageBase#PackageBase(String, String, double, double, LocalDateTime)}
     * @param handleFee The environmental handling fee that comes with sending an electronic package.
     */
    public Electronic(String name, String notes, double price,
                      double weight, LocalDateTime date, double handleFee) {
        super(name, notes, price, weight, date);
        this.handleFee = handleFee;
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
                "Package Type: Electronic\n" +
                "Notes: " + this.notes + "\n" +
                "Price (CAD): $" + this.price + "\n" +
                "Weight (kg): " + this.weight + "kg\n" +
                "Expected Delivery Date: " +
                this.expectedDeliveryDate.format(dateFormat) +
                "\nDelivery Status: " + deliveryStatus + "\n" +
                "Environmental Handling Fee: " + this.handleFee + "\n");
    }

    /**
     * Getter for handling fee.
     *
     * @return Returns the handling fee.
     */
    public double getHandleFee() {
        return handleFee;
    }
}
