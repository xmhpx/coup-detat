package modules.bots;

import logic.GameLogicCenter;
import modules.*;
import modules.cardtypes.Card;
import modules.cardtypes.Duke;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Coupper extends DefaultPlayer {
    private static final Logger log = LogManager.getLogger(Coupper.class);

    public Coupper(String name, Card leftCard, Card rightCard, int coins, DoerType type) {
        super(name, leftCard, rightCard, coins, type);
    }


    @Override
    public Move getMove(){
        GameLogicCenter backend = GameLogicCenter.getInstance();
        if(coins >= 7){
            for(int victimNumber = 0; victimNumber <= 3; victimNumber++){
                DefaultPlayer victim = backend.getPlayer(victimNumber);
                if(victim == this){
                    continue;
                }
                if(!victim.isAlive())continue;
                return Move.getCoupMove(this, victim);
            }
        }
        return Move.getTakeFromTreasuryMove(this);
    }


    @Override
    public boolean doesKillLeftCard(Move move){
        if(leftCard.isAlive() && !leftCard.getName().equals(Duke.name)) {
            return true;
        }
        if(rightCard.isAlive() && !rightCard.getName().equals(Duke.name)) {
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
