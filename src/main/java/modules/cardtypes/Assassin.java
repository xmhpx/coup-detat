package modules.cardtypes;

import javafx.scene.image.Image;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Assassin extends Card {
    private static final Logger log = LogManager.getLogger(Assassin.class);

    public static final String name = "Assassin";
    public static final String description =
            "1. Pay three coins to assassinate one of another player's card(s).";
    public static final String imagePath = "C:\\Users\\Hami\\IdeaProjects\\coup-deta\\src\\main\\resources\\Images\\Assassin.jpg";
    public static final String deadImagePath = "C:\\Users\\Hami\\IdeaProjects\\coup-deta\\src\\main\\resources\\Images\\DeadAssassin.jpg";

    public Assassin(boolean isAlive, int cardNumber) {
        super(name, description, isAlive, cardNumber);
        setImagePath(imagePath);
        setDeadImagePath(deadImagePath);
    }
}