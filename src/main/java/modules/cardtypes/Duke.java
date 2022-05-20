package modules.cardtypes;

import javafx.scene.image.Image;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Duke extends Card {
    private static final Logger log = LogManager.getLogger(Duke.class);

    public static final String name = "Duke";
    public static final String description =
            "1. Take three coins from treasury.\n" +
            "2. Block anyone from taking the foreign aid action.";
    public static final String imagePath = "C:\\Users\\Hami\\IdeaProjects\\coup-deta\\src\\main\\resources\\Images\\Duke.jpg";

    public Duke(boolean isAlive, int cardNumber) {
        super(name, description, isAlive, cardNumber);
    }


    public Image getImage(){
        return new Image(imagePath);
    }
}
