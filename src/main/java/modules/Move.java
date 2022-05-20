package modules;

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
