package modules.cardtypes;

public class Assassin extends Card {
    public static final String name = "Assassin";
    public static final String description =
            "1. Pay three coins to assassinate one of another player's card(s).";

    public Assassin(boolean isAlive, int cardNumber) {
        super(name, description, isAlive, cardNumber);
    }
}