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


    public static Move getChallengeMove(DefaultPlayer doer, DefaultPlayer challenger){
        return new Move(challenger.getType(), doer.getMoveTarget(), MoveType.CHALLENGE);
    }

    public static Move getInterveneMove(DefaultPlayer doer, DefaultPlayer intervener){
        return new Move(intervener.getType(), doer.getMoveTarget(), MoveType.INTERVENE);
    }

    public static Move getShowCardMove(DefaultPlayer doer, Card shownCard){
        return new Move(doer.getType(), shownCard.getMoveTarget(), MoveType.SHOW_CARD);
    }

    public static Move getAssassinateMove(DefaultPlayer assassin, DefaultPlayer victim){
        return new Move(assassin.getType(), victim.getMoveTarget(), MoveType.ASSASSINATE);
    }

    public static Move getStealMove(DefaultPlayer captain, DefaultPlayer victim) {
        return new Move(captain.getType(), victim.getMoveTarget(), MoveType.STEAL);
    }

    public static Move getForeignAidMove(DefaultPlayer doer) {
        return new Move(doer.getType(), MoveTarget.CENTER, MoveType.FOREIGN_AID);
    }

    public static Move getTakeFromTreasuryMove(DefaultPlayer duke) {
        return new Move(duke.getType(), MoveTarget.CENTER, MoveType.TAKE_FROM_TREASURY);
    }

    public static Move getIncomeMove(DefaultPlayer doer) {
        return new Move(doer.getType(), MoveTarget.CENTER, MoveType.INCOME);
    }

    public static Move getCoupMove(DefaultPlayer coup, DefaultPlayer victim) {
        return new Move(coup.getType(), victim.getMoveTarget(), MoveType.COUP);
    }

    public static Move getSwapOneMove(DefaultPlayer doer, boolean swapLeft) {
        MoveTarget moveTarget;
        if(swapLeft) {
            moveTarget = MoveTarget.LEFT;
        }
        else{
            moveTarget = MoveTarget.RIGHT;
        }

        return new Move(doer.getType(), moveTarget, MoveType.SWAP_ONE);
    }

    public static Move getAmbassadorExchangeMove(DefaultPlayer doer) {
        return new Move(doer.getType(), MoveTarget.CENTER, MoveType.AMBASSADOR_EXCHANGE);
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
