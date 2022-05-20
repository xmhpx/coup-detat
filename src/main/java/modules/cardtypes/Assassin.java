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

    public Assassin(boolean isAlive, int cardNumber) {
        super(name, description, isAlive, cardNumber);
    }


    public Image getImage(){
        return new Image(imagePath);
    }
}