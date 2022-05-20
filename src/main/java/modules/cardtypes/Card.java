package modules.cardtypes;

import javafx.scene.image.Image;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Card {
    private static final Logger log = LogManager.getLogger(Card.class);

    protected String name;
    protected String description;
    protected boolean isAlive;
    protected int cardNumber;
    public static final String backImagePath = "C:\\Users\\Hami\\IdeaProjects\\coup-deta\\src\\main\\resources\\Images\\Back.jpeg";


    public Card(String name, String description, boolean isAlive, int cardNumber){
        this.name = name;
        this.description = description;
        this.isAlive = isAlive;
        this.cardNumber = cardNumber;
    }


    public Image getImage(){
        return null;
    }

    public Image getDeadImage(){
        return null;
    }

    public static Image getBackImage(){
        return new Image(backImagePath);
    }

    // getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }
}
