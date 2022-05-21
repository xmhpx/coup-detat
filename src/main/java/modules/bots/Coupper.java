package modules.bots;

import logic.GameLogicCenter;
import modules.*;
import modules.cardtypes.Card;

public class Coupper extends Player {

    public Coupper(String name, Card leftCard, Card rightCard, int coins, DoerType type) {
        super(name, leftCard, rightCard, coins, type);
    }


    @Override
    public Move getMove(){
        GameLogicCenter backend = GameLogicCenter.getInstance();
        if(coins >= 7){
            for(int victimNumber = 3; victimNumber >= 0; victimNumber--){
                Player victim = backend.getPlayer(victimNumber);
                if(victim.getType().equals(getType())){
                    continue;
                }
                if(!victim.isAlive())continue;
                return new Move(this.getType(), MoveTarget.valueOf(""+victim.getType()), MoveType.COUP);
            }
        }
        return new Move(this.getType(), MoveTarget.CENTER, MoveType.TAKE_FROM_TREASURY);
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
    public boolean killsLeftCard(Move move){
        return leftCard.isAlive();
    }

    @Override
    public boolean showsLeftCardWhenChallenged(Move move){
        return leftCard.isAlive();
    }
}
