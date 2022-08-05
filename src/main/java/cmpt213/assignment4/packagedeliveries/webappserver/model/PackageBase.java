package cmpt213.assignment4.packagedeliveries.webappserver.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Abstract package base class that is modelled after a package; stores name, notes, price, weight, date,
 * and delivery status. Must be implemented as: Book, Perishable, or Electronic.
 * Implements the Package Interface.
 *
 * @author Deborah Wang
 */
public abstract class PackageBase implements Package {
    protected static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm a");
    protected final String name;
    protected final String notes;
    protected final double price;
    protected final double weight;
    protected final LocalDateTime expectedDeliveryDate;
    protected boolean isDelivered;

    /**
     * Constructor for class, creates an object modelled as a package
     * with its respective fields.
     *
     * @param name                 The name of the package, cannot be empty.
     * @param notes                Any notes for the package, can be empty.
     * @param price                The price (in CAD) of the package, cannot be negative.
     * @param weight               The weight (in kg) of the package, cannot be negative.
     * @param expectedDeliveryDate The date of the package (Year, Month, Day, Hour, Minute), in 24-hour format.
     */
    public PackageBase(String name, String notes, double price, double weight,
                       LocalDateTime expectedDeliveryDate) {
        this.name = name;
        this.notes = notes;
        this.price = price;
        this.weight = weight;
        this.expectedDeliveryDate = expectedDeliveryDate;

        this.isDelivered = false;
    }

    /**
     * Sorts arraylist of package base items based on delivery date.
     * Order: oldest to newest.
     * Implementation of {@link Package#compareTo(PackageBase)}
     *
     * @return An integer, positive if date is before current time, negative if after.
     */
    @Override
    public int compareTo(PackageBase p) {
        if (p != null) {
            if (p.getExpectedDeliveryDate().isBefore(this.expectedDeliveryDate)) {
                return 1;
            }
        }
        return -1;
    }

    /**
     * Gets the package's delivery status;
     * returns false if not delivered, true if delivered.
     * Implementation of {@link Package#isDelivered()}
     *
     * @return A boolean value (true or false)
     */
    @Override
    public boolean isDelivered() {
        return this.isDelivered;
    }

    /**
     * Changes a package's delivery status;
     * either true or false.
     * Implementation of {@link Package#setDeliveryStatus(boolean)}
     *
     * @param newStatus The new status of the package, boolean value.
     */
    @Override
    public void setDeliveryStatus(boolean newStatus) {
        this.isDelivered = newStatus;
    }

    /**
     * Gets the delivery date of the package
     * as a LocalDateTime object.
     * Implementation of {@link Package#getExpectedDeliveryDate()}
     *
     * @return A LocalDateTime object with the date information.
     */
    @Override
    public LocalDateTime getExpectedDeliveryDate() {
        return this.expectedDeliveryDate;
    }

    /**
     * Gets the name of the package.
     * Implementation of {@link Package#getName()}
     *
     * @return A String that holds the name.
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Gets the notes of the package.
     * Implementation of {@link Package#getNotes()}
     *
     * @return A String that holds the notes.
     */
    @Override
    public String getNotes() {
        return notes;
    }

    /**
     * Gets the price of the package.
     * Implementation of {@link Package#getPrice()}
     *
     * @return A double that holds the price.
     */
    @Override
    public double getPrice() {
        return price;
    }

    /**
     * Gets the weight of the package.
     * Implementation of {@link Package#getWeight()}
     *
     * @return A double that holds the weight.
     */
    @Override
    public double getWeight() {
        return weight;
    }

}
