package modules.cardtypes;

public class Ambassador extends Card {
    public static final String name = "Ambassador";
    public static final String description =
            "1. Draw two character cards from the draw deck, choose two to keep (from the 4 you now have) and return two.\n" +
            "2. Block anyone from stealing from you.";

    public Ambassador(boolean isAlive, int cardNumber) {
        super(name, description, isAlive, cardNumber);
    }
}
