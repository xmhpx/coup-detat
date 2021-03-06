package modules.cardtypes;

import javafx.scene.image.Image;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Ambassador extends Card {
    private static final Logger log = LogManager.getLogger(Ambassador.class);

    public static final String name = "Ambassador";
    public static final String description =
            "1. Draw two character cards from the draw deck, choose two to keep (from the 4 you now have) and return two.\n" +
            "2. Block anyone from stealing from you.";
    public static final String imagePath = "C:\\Users\\Hami\\IdeaProjects\\coup-deta\\src\\main\\resources\\Images\\Ambassador.jpg";
    public static final String deadImagePath = "C:\\Users\\Hami\\IdeaProjects\\coup-deta\\src\\main\\resources\\Images\\DeadAmbassador.jpg";

    public Ambassador(boolean isAlive, int cardNumber) {
        super(name, description, isAlive, cardNumber);
        setImagePath(imagePath);
        setDeadImagePath(deadImagePath);
    }

}
