package cmpt213.assignment4.packagedeliveries.webappserver.model;

import java.time.LocalDateTime;

/**
 * A Factory class that creates concrete PackageBase objects.
 * Modelled after OOD Factory Design.
 *
 * @author Deborah Wang
 */
public class PackageFactory {
    /**
     * Method that returns a concrete PackageBase object based on the given type.
     *
     * @param packageType  Which package type to return.
     * @param name         Name of Package.
     * @param notes        Notes of Package.
     * @param price        price of Package.
     * @param weight       weight of Package.
     * @param deliveryDate Delivery date of Package.
     * @param extraField   Extra field of Package, different depending on type.
     * @return A package object with given type.
     */
    public PackageBase getInstance(PackageType packageType, String name, String notes,
                                   double price, double weight, LocalDateTime deliveryDate, String extraField) {

        if (packageType == null) {
            return null;
        }
        return switch (packageType) {
            case BOOK -> new Book(name, notes, price, weight, deliveryDate, extraField);
            case PERISHABLE ->
                    new Perishable(name, notes, price, weight, deliveryDate, LocalDateTime.parse(extraField));
            case ELECTRONIC -> new Electronic(name, notes, price, weight, deliveryDate, Double.parseDouble(extraField));
        };
    }

    /**
     * Enum that holds Package types, helps with identifying packages.
     */
    public enum PackageType {
        BOOK, PERISHABLE, ELECTRONIC
    }


}
