package modules;

import logic.GameLogicCenter;
import modules.cardtypes.Card;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import pagecontrollers.PageControllerStorage;

public class DefaultPlayer {
    private static final Logger log = LogManager.getLogger(DefaultPlayer.class);

    protected String name;
    protected Card leftCard;
    protected Card rightCard;
    protected int coins;
    protected DoerType type = null;


    public DefaultPlayer(String name, Card leftCard, Card rightCard, int coins, DoerType type){
        this.name = name;
        this.leftCard = leftCard;
        this.rightCard = rightCard;
        this.coins = coins;
        this.type = type;
    }



    public Move getMove(){
        GameLogicCenter backend = GameLogicCenter.getInstance();
        if(coins >= 10){
            for(int victimNumber = 3; victimNumber >= 0; victimNumber--){
                DefaultPlayer victim = backend.getPlayer(victimNumber);
                if(victim == this){
                    continue;
                }
                if(!victim.isAlive())continue;
                return Move.getCoupMove(this, victim);
            }
        }
        return Move.getIncomeMove(this);
    }



    public void decideChallenge(Move move){
    }

    public void decideIntervene(Move move){
    }

    public void decideKillLeftCard(Move move){
    }

    public void decideShowLeftCardWhenChallenged(Move move){
    }

    public boolean doesChallenge(Move move){
        return false;
    }

    public boolean doesIntervene(Move move){
        return false;
    }

    public boolean doesKillLeftCard(Move move){
        return leftCard.isAlive();
    }

    public boolean doesShowLeftCardWhenChallenged(Move move){
        return leftCard.isAlive();
    }



    public MoveTarget getMoveTarget(){
        return MoveTarget.valueOf(""+getType());
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


    public DoerType getType() {
        return type;
    }

    public void setType(DoerType type) {
        this.type = type;
    }
}
