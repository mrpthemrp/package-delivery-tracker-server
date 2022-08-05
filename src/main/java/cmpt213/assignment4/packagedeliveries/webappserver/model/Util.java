package cmpt213.assignment4.packagedeliveries.webappserver.model;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

/**
 * Utilities class that holds constants that are used by a lot of files.
 *
 * @author Deborah Wang
 */
public final class Util {

    public static final String fs = File.separator;

    /**
     * Enum is for the state of the UI, helpful with updating states.
     */
    public enum SCREEN_STATE {
        START, LIST_ALL, UPCOMING, OVERDUE
    }
}
