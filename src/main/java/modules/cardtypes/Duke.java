package modules.cardtypes;

public class Duke extends Card {
    public static final String name = "Duke";
    public static final String description =
            "1. Take three coins from treasury.\n" +
            "2. Block anyone from taking the foreign aid action.";

    public Duke(boolean isAlive, int cardNumber) {
        super(name, description, isAlive, cardNumber);
    }
}
