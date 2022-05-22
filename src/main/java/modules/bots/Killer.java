package modules.bots;

import logic.GameLogicCenter;
import modules.DefaultPlayer;
import modules.DoerType;
import modules.Move;
import modules.cardtypes.Ambassador;
import modules.cardtypes.Assassin;
import modules.cardtypes.Card;
import modules.cardtypes.Duke;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Killer extends DefaultPlayer {
    private static final Logger log = LogManager.getLogger(Killer.class);

    public Killer(String name, Card leftCard, Card rightCard, int coins, DoerType type) {
        super(name, leftCard, rightCard, coins, type);
    }

    @Override
    public Move getMove(){
        GameLogicCenter backend = GameLogicCenter.getInstance();
        if(coins >= 3 && hasCard(Assassin.name)){
            for(int victimNumber = 0; victimNumber <= 3; victimNumber++){
                DefaultPlayer victim = backend.getPlayer(victimNumber);
                if(victim == this){
                    continue;
                }
                if(!victim.isAlive())continue;
                return Move.getAssassinateMove(this, victim);
            }
        }
        if(hasCard(Assassin.name)){
            return Move.getForeignAidMove(this);
        }
        else{
            if(hasCard(Ambassador.name)){
                return Move.getAmbassadorExchangeMove(this);
            }

            if(coins > 0){
                return Move.getSwapOneMove(this, true);
            }
            else{
                return Move.getForeignAidMove(this);
            }
        }
    }



    @Override
    public Card[] ambassadorExchange(Move move){
        Card[] exchangeCards = new Card[4];
        Card[] randomCards = GameLogicCenter.getInstance().getTwoDrawableRandomCard();
        exchangeCards[0] = leftCard;
        exchangeCards[1] = rightCard;
        exchangeCards[2] = randomCards[0];
        exchangeCards[3] = randomCards[1];

        if(hasCard(Ambassador.name)) {
            for (int i = 0; i < 4; i++) {
                if (exchangeCards[i].getName().equals(Assassin.name) && exchangeCards[i].isAlive()) {
                    if (leftCard.isAlive() && leftCard.getName().equals(Ambassador.name)) {
                        return new Card[]{exchangeCards[i], rightCard};
                    } else {
                        return new Card[]{exchangeCards[i], leftCard};
                    }
                }
            }
        }

        return new Card[]{leftCard, rightCard};
    }

    @Override
    public boolean doesKillLeftCard(Move move){
        if(leftCard.isAlive() && !leftCard.getName().equals(Assassin.name)) {
            return true;
        }
        if(rightCard.isAlive() && !rightCard.getName().equals(Assassin.name)) {
            return false;
        }
        else{
            return super.doesKillLeftCard(move);
        }
    }

    @Override
    public boolean doesShowLeftCardWhenChallenged(Move move){
        return super.doesShowLeftCardWhenChallenged(move);
    }
}
