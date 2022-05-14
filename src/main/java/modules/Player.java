package modules;

import modules.cardtypes.Card;

public class Player {
    protected String name;
    protected Card leftCard;
    protected Card rightCard;
    protected int coins;


    public Player(String name, Card leftCard, Card rightCard, int coins){
        this.name = name;
        this.leftCard = leftCard;
        this.rightCard = rightCard;
        this.coins = coins;
    }



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
}
