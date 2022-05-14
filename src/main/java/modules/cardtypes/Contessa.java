package modules.cardtypes;

public class Contessa extends Card {
    public static final String name = "Contessa";
    public static final String description =
            "1. Block assassination attempts against you.";

    public Contessa(boolean isAlive, int cardNumber) {
        super(name, description, isAlive, cardNumber);
    }
}
