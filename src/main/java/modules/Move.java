package modules;

import modules.cardtypes.Card;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Move {
    private static final Logger log = LogManager.getLogger(Move.class);
    private DoerType doer;
    private MoveTarget moveTarget;
    private MoveType moveType;


    public Move(){}

    public Move(DoerType doer, MoveTarget moveTarget, MoveType moveType){
        this.doer = doer;
        this.moveTarget = moveTarget;
        this.moveType = moveType;
    }


    public static Move getChallengeMove(Player doer, Player challenger){
        return new Move(challenger.getType(), doer.getMoveTarget(), MoveType.CHALLENGE);
    }

    public static Move getInterveneMove(Player doer, Player intervener){
        return new Move(intervener.getType(), doer.getMoveTarget(), MoveType.INTERVENE);
    }

    public static Move getShowCardMove(Player doer, Card shownCard){
        return new Move(doer.getType(), shownCard.getMoveTarget(), MoveType.SHOW_CARD);
    }

    public static Move getAssassinateMove(Player assassin, Player victim){
        return new Move(assassin.getType(), victim.getMoveTarget(), MoveType.ASSASSINATE);
    }

    public static Move getStealMove(Player captain, Player victim) {
        return new Move(captain.getType(), victim.getMoveTarget(), MoveType.STEAL);
    }

    public static Move getForeignAidMove(Player doer) {
        return new Move(doer.getType(), MoveTarget.CENTER, MoveType.FOREIGN_AID);
    }

    public static Move getTakeFromTreasuryMove(Player duke) {
        return new Move(duke.getType(), MoveTarget.CENTER, MoveType.TAKE_FROM_TREASURY);
    }


    @Override
    public String toString(){
        return doer+" "+moveTarget+" "+moveType;
    }

    // getters and setters

    public DoerType getDoer() {
        return doer;
    }

    public void setDoer(DoerType doer) {
        this.doer = doer;
    }


    public MoveTarget getMoveTarget() {
        return moveTarget;
    }

    public void setMoveTarget(MoveTarget moveTarget) {
        this.moveTarget = moveTarget;
    }


    public MoveType getMoveType() {
        return moveType;
    }

    public void setMoveType(MoveType moveType) {
        this.moveType = moveType;
    }
}
