package modules.cardtypes;

public class Captain extends Card {
    public static final String name = "Captain";
    public static final String description =
            "1. Steal two coins from another player.\n" +
            "2. Block anyone from stealing from you.";

    public Captain(boolean isAlive, int cardNumber) {
        super(name, description, isAlive, cardNumber);
    }
}
