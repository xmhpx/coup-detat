package modules.bots;

import logic.GameLogicCenter;
import modules.*;
import modules.cardtypes.Card;
import modules.cardtypes.Duke;

public class Coupper extends DefaultPlayer {

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
    public boolean doesChallenge(Move move){
        return false;
    }

    @Override
    public boolean doesIntervene(Move move){
        return false;
    }

    @Override
    public boolean doesKillLeftCard(Move move){
        if(leftCard.getName().equals(Duke.name)) {
            return !rightCard.isAlive();
        }
        return leftCard.isAlive();
    }

    @Override
    public boolean doesShowLeftCardWhenChallenged(Move move){
        if(leftCard.getName().equals(Duke.name)) {
            return leftCard.isAlive();
        }
        return !rightCard.isAlive();
    }
}
