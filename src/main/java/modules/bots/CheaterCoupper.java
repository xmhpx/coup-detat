package modules.bots;

import logic.GameLogicCenter;
import modules.*;
import modules.cardtypes.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class CheaterCoupper extends Coupper{
    private static final Logger log = LogManager.getLogger(CheaterCoupper.class);

    public CheaterCoupper(String name, Card leftCard, Card rightCard, int coins, DoerType type) {
        super(name, leftCard, rightCard, coins, type);
    }

    @Override
    public boolean doesChallenge(Move move){
        GameLogicCenter backend = GameLogicCenter.getInstance();
        DefaultPlayer doer = null;

        for(int i = 0; i < 4; i++){
            if(backend.getPlayer(i).getType() == move.getDoer()){
                doer = backend.getPlayer(i);
                break;
            }
        }

        if(doer == null){
            return super.doesChallenge(move);
        }

        if(move.getMoveType() == MoveType.TAKE_FROM_TREASURY){
            return !doer.hasCard(Duke.name);
        }
        if(move.getMoveType() == MoveType.STEAL){
            return !doer.hasCard(Captain.name);
        }
        if(move.getMoveType() == MoveType.ASSASSINATE){
            return !doer.hasCard(Assassin.name);
        }
        if(move.getMoveType() == MoveType.INTERVENE){
            if(move.getMoveTarget() == MoveTarget.ASSASSINATE){
                return !doer.hasCard(Contessa.name);
            }
            if(move.getMoveTarget() == MoveTarget.STEAL){
                return ! (doer.hasCard(Ambassador.name) || doer.hasCard(Captain.name));
            }
            if(move.getMoveTarget() == MoveTarget.FOREIGN_AID){
                return !doer.hasCard(Duke.name);
            }
        }
        return false;
    }
}
