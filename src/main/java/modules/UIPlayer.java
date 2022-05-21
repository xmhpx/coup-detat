package modules;

import logic.GameLogicCenter;
import modules.cardtypes.Card;
import pagecontrollers.PageControllerStorage;

public class UIPlayer extends DefaultPlayer{

    public UIPlayer(String name, Card leftCard, Card rightCard, int coins, DoerType type) {
        super(name, leftCard, rightCard, coins, type);
    }


    protected Move move = null;
    protected boolean decided = false;


    public void decideChallenge(Move move){
        decided = false;
        PageControllerStorage.getInstance().getGamePageController().decideChallenge(move);
    }

    public void decideIntervene(Move move){
        decided = false;
        PageControllerStorage.getInstance().getGamePageController().decideIntervene(move);
    }

    public boolean doesChallenge(Move move){
        return PageControllerStorage.getInstance().getGamePageController().doesChallenge(move);
    }

    public boolean doesIntervene(Move move){
        return PageControllerStorage.getInstance().getGamePageController().doesIntervene(move);
    }

    public boolean doesKillLeftCard(Move move){
        return PageControllerStorage.getInstance().getGamePageController().doesKillLeftCard(move);
    }

    public boolean doesShowLeftCardWhenChallenged(Move move){
        return PageControllerStorage.getInstance().getGamePageController().doesShowLeftCardWhenChallenged(move);
    }



    // getters and setters

    public Move getMove(){
        if(move != null)return move;

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

    public void setMove(Move move) {
        this.move = move;
    }


    public boolean isDecided() {
        return decided;
    }

    public void setDecided(boolean decided) {
        this.decided = decided;
    }
}
