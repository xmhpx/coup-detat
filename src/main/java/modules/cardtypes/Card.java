package modules.cardtypes;

public class Card {
    protected String name;
    protected String description;
    protected boolean isAlive;
    protected int cardNumber;


    public Card(String name, String description, boolean isAlive, int cardNumber){
        this.name = name;
        this.description = description;
        this.isAlive = isAlive;
        this.cardNumber = cardNumber;
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
