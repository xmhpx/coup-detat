package modules.cardtypes;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Captain extends Card {
    private static final Logger log = LogManager.getLogger(Captain.class);

    public static final String name = "Captain";
    public static final String description =
            "1. Steal two coins from another player.\n" +
            "2. Block anyone from stealing from you.";

    public Captain(boolean isAlive, int cardNumber) {
        super(name, description, isAlive, cardNumber);
    }
}
