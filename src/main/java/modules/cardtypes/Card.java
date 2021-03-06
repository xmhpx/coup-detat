package modules.cardtypes;

import javafx.scene.image.Image;
import modules.MoveTarget;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Card {
    private static final Logger log = LogManager.getLogger(Card.class);

    protected String name;
    protected String description;
    protected String imagePath;
    protected String deadImagePath;
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
        return new Image(imagePath);
    }

    public Image getDeadImage(){
        return new Image(deadImagePath);
    }

    public static Image getBackImage(){
        return new Image(backImagePath);
    }


    public MoveTarget getMoveTarget(){
        return MoveTarget.valueOf(getName().toUpperCase());
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

    public void setAlive(boolean alive){
        if(!alive) {
            log.info(name + " died");
        }
        else{
            log.info(name + " survived");
        }
        isAlive = alive;
    }


    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }


    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


    public String getDeadImagePath() {
        return deadImagePath;
    }

    public void setDeadImagePath(String deadImagePath) {
        this.deadImagePath = deadImagePath;
    }
}
