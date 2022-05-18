package modules.cardtypes;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Contessa extends Card {
    private static final Logger log = LogManager.getLogger(Contessa.class);

    public static final String name = "Contessa";
    public static final String description =
            "1. Block assassination attempts against you.";

    public Contessa(boolean isAlive, int cardNumber) {
        super(name, description, isAlive, cardNumber);
    }
}
