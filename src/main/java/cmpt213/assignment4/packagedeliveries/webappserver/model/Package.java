package cmpt213.assignment4.packagedeliveries.webappserver.model;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Package interface that writes method declarations for a package object.
 * Extends Comparable to allow {@link java.util.Collections#sort(List)} to be used on lists of PackageBase items.
 *
 * @author Deborah Wang
 */
public interface Package extends Comparable<PackageBase> {

    /**
     * For full method implementation see methods in {@link PackageBase}
     *
     * @param p the object to be compared.
     * @return An integer.
     */
    @Override
    int compareTo(PackageBase p);

    /**
     * For full method implementation see methods in {@link PackageBase}
     *
     * @return A boolean that tells if a package is delivered or not.
     */
    boolean isDelivered();

    /**
     * For full method implementation see methods in {@link PackageBase}
     *
     * @param newStatus The new Status to be set for teh package's delivery status.
     */
    void setDeliveryStatus(boolean newStatus);

    /**
     * For full method implementation see methods in {@link PackageBase}
     *
     * @return Returns the expected delivery date of the package.
     */
    LocalDateTime getExpectedDeliveryDate();

    /**
     * For full method implementation see methods in {@link PackageBase}
     *
     * @return Returns the name of the package.
     */
    String getName();

    /**
     * For full method implementation see methods in {@link PackageBase}
     *
     * @return Returns the notes of the package.
     */
    String getNotes();

    /**
     * For full method implementation see methods in {@link PackageBase}
     *
     * @return Returns the price of the package.
     */
    double getPrice();

    /**
     * For full method implementation see methods in {@link PackageBase}
     *
     * @return Returns the weight of the package.
     */
    double getWeight();
}
