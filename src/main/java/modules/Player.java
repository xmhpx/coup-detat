package modules;

import modules.cardtypes.Card;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import pagecontrollers.BasicPageController;

public class Player {
    private static final Logger log = LogManager.getLogger(Player.class);

    protected String name;
    protected Card leftCard;
    protected Card rightCard;
    protected int coins;
    protected DoerType type = null;


    public Player(String name, Card leftCard, Card rightCard, int coins, DoerType type){
        this.name = name;
        this.leftCard = leftCard;
        this.rightCard = rightCard;
        this.coins = coins;
        this.type = type;
    }



    public Move getMove(){return null;}


    // getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Card getLeftCard() {
        return leftCard;
    }

    public void setLeftCard(Card leftCard) {
        this.leftCard = leftCard;
    }


    public Card getRightCard() {
        return rightCard;
    }

    public void setRightCard(Card rightCard) {
        this.rightCard = rightCard;
    }


    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }


    public boolean isAlive(){
        return leftCard.isAlive() || rightCard.isAlive();
    }


    public DoerType getType() {
        return type;
    }

    public void setType(DoerType type) {
        this.type = type;
    }
}
