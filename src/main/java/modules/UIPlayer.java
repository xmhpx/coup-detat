package modules;

import javafx.fxml.FXML;
import logic.GameLogicCenter;
import modules.cardtypes.Card;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import pagecontrollers.PageControllerStorage;

import java.util.Arrays;

public class UIPlayer extends DefaultPlayer{
    private static final Logger log = LogManager.getLogger(UIPlayer.class);

    public UIPlayer(String name, Card leftCard, Card rightCard, int coins, DoerType type) {
        super(name, leftCard, rightCard, coins, type);
    }


    protected Move move = null;
    protected boolean decided = false;


    @Override
    public void decideAmbassadorExchange(Move move){
        decided = false;
        PageControllerStorage.getInstance().getGamePageController().decideAmbassadorExchange(move);
    }

    @Override
    public void decideChallenge(Move move){
        decided = false;
        PageControllerStorage.getInstance().getGamePageController().decideChallenge(move);
    }

    @Override
    public void decideIntervene(Move move){
        decided = false;
        PageControllerStorage.getInstance().getGamePageController().decideIntervene(move);
    }

    @Override
    public void decideKillLeftCard(Move move){
        decided = false;
        PageControllerStorage.getInstance().getGamePageController().decideKillLeftCard(move);
    }

    @Override
    public void decideShowLeftCardWhenChallenged(Move move){
        decided = false;
        PageControllerStorage.getInstance().getGamePageController().decideShowLeftCardWhenChallenged(move);
    }



    public Card[] ambassadorExchange(Move move){
        decideAmbassadorExchange(move);
        while(!decided){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        var res = PageControllerStorage.getInstance().getGamePageController().ambassadorExchange(move);
        log.info(Arrays.toString(res));
        return res;
    }

    @Override
    public boolean doesChallenge(Move move){
        decideChallenge(move);
        while(!decided){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return PageControllerStorage.getInstance().getGamePageController().doesChallenge(move);
    }

    @Override
    public boolean doesIntervene(Move move){
        decideIntervene(move);
        while(!decided){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return PageControllerStorage.getInstance().getGamePageController().doesIntervene(move);
    }

    @Override
    public boolean doesKillLeftCard(Move move){
        decideKillLeftCard(move);
        while(!decided){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        boolean killLeft = PageControllerStorage.getInstance().getGamePageController().doesKillLeftCard(move);
        if((killLeft && !leftCard.isAlive()) || (!killLeft && !rightCard.isAlive())){
            killLeft = !killLeft;
        }
        return killLeft;
    }

    @Override
    public boolean doesShowLeftCardWhenChallenged(Move move){
        decideShowLeftCardWhenChallenged(move);
        while(!decided){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        boolean showLeft = PageControllerStorage.getInstance().getGamePageController().doesShowLeftCardWhenChallenged(move);
        if((showLeft && !leftCard.isAlive()) || (!showLeft && !rightCard.isAlive())){
            showLeft = !showLeft;
        }
        return showLeft;
    }



    // getters and setters

    @Override
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
