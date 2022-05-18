package modules.cardtypes;

import modules.Player;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Duke extends Card {
    private static final Logger log = LogManager.getLogger(Duke.class);

    public static final String name = "Duke";
    public static final String description =
            "1. Take three coins from treasury.\n" +
            "2. Block anyone from taking the foreign aid action.";

    public Duke(boolean isAlive, int cardNumber) {
        super(name, description, isAlive, cardNumber);
    }
}
