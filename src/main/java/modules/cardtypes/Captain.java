package modules.cardtypes;

import javafx.scene.image.Image;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Captain extends Card {
    private static final Logger log = LogManager.getLogger(Captain.class);

    public static final String name = "Captain";
    public static final String description =
            "1. Steal two coins from another player.\n" +
            "2. Block anyone from stealing from you.";
    public static final String imagePath = "C:\\Users\\Hami\\IdeaProjects\\coup-deta\\src\\main\\resources\\Images\\Captain.jpg";
    public static final String deadImagePath = "C:\\Users\\Hami\\IdeaProjects\\coup-deta\\src\\main\\resources\\Images\\DeadCaptain.jpg";

    public Captain(boolean isAlive, int cardNumber) {
        super(name, description, isAlive, cardNumber);
        setImagePath(imagePath);
        setDeadImagePath(deadImagePath);
    }

}
