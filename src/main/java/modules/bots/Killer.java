package modules.bots;

import logic.GameLogicCenter;
import modules.DefaultPlayer;
import modules.DoerType;
import modules.Move;
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

    private boolean hasAssassin(){
        return  (rightCard.isAlive() && rightCard.getName().equals(Assassin.name)) ||
                (leftCard.isAlive() && leftCard.getName().equals(Assassin.name));
    }

    @Override
    public Move getMove(){
        GameLogicCenter backend = GameLogicCenter.getInstance();
        if(coins >= 3 && hasAssassin()){
            for(int victimNumber = 0; victimNumber <= 3; victimNumber++){
                DefaultPlayer victim = backend.getPlayer(victimNumber);
                if(victim == this){
                    continue;
                }
                if(!victim.isAlive())continue;
                return Move.getAssassinateMove(this, victim);
            }
        }
        if(hasAssassin()){
            return Move.getForeignAidMove(this);
        }
        else{
            if(coins > 0){
                return Move.getSwapOneMove(this, true);
            }
            else{
                return Move.getForeignAidMove(this);
            }
        }
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
