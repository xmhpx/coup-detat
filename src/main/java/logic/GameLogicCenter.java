package logic;

import modules.Player;
import modules.cardtypes.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.Random;

//TODO force doing coup-d'etat if player has at least 10 coins
//TODO if someone tries to assassinate but fails, they'll get their money back

public class GameLogicCenter {
    private static final Logger log = LogManager.getLogger(GameLogicCenter.class);

    private static GameLogicCenter INSTANCE;

    public static GameLogicCenter getInstance(){
        if(INSTANCE == null){
            INSTANCE = new GameLogicCenter();
        }
        return INSTANCE;
    }


    protected static int startingCoins;


    protected Player[] players;
    protected Card[] cards;

    boolean[] isDrawable;


    private GameLogicCenter(){
        players = new Player[4];
        players[0] = new Player("YOU", null, null, startingCoins);
        for(int i = 1; i < 4; i++){
            players[i] = new Player("bot"+i, null, null, startingCoins);
        }

        cards = new Card[15];
        isDrawable = new boolean[15];

        Arrays.fill(isDrawable, true);

        for(int i = 0; i < 15; i++){
            if(i < 3){
                cards[i] = new Ambassador(true, i);
            }
            else if(i < 6){
                cards[i] = new Assassin(true, i);
            }
            else if(i < 9){
                cards[i] = new Captain(true, i);
            }
            else if(i < 12){
                cards[i] = new Contessa(true, i);
            }
            else{
                cards[i] = new Duke(true, i);
            }
        }

        // TODO shuffle and ability to choose starting cards

        for(int i = 0; i < 4; i++){
            Card leftCard = cards[i*2];
            Card rightCard = cards[i*2+1];

            players[i].setLeftCard(leftCard);
            players[i].setRightCard(rightCard);

            int leftCardNumber = leftCard.getCardNumber();
            int rightCardNumber = rightCard.getCardNumber();

            isDrawable[leftCardNumber] = false;
            isDrawable[rightCardNumber] = false;
        }
    }


    //TODO buggy if exchanges cards with his own cards

    public boolean ambassadorExchangeOne(Player player, int drawnCardNumber, boolean exchangeLeftCard){
        if(!player.isAlive())return false;

        if(!isDrawable[drawnCardNumber])return false;

        if(exchangeLeftCard) {
            if (!player.getLeftCard().isAlive()) return false;
            int exchangedCardNumber = player.getLeftCard().getCardNumber();
            isDrawable[exchangedCardNumber] = true;
            isDrawable[drawnCardNumber] = false;
            player.setLeftCard(cards[drawnCardNumber]);
        }
        else{
            if (!player.getRightCard().isAlive()) return false;
            int exchangedCardNumber = player.getRightCard().getCardNumber();
            isDrawable[exchangedCardNumber] = true;
            isDrawable[drawnCardNumber] = false;
            player.setRightCard(cards[drawnCardNumber]);
        }

        return true;
    }

    public boolean ambassadorExchangeTwo(Player player, int cardNumber1, int cardNumber2){
        if(!player.isAlive())return false;

        if(!isDrawable[cardNumber1]){
            return false;
        }
        if(!isDrawable[cardNumber2]){
            return false;
        }

        if(!player.getLeftCard().isAlive())return false;
        if(!player.getRightCard().isAlive())return false;

        isDrawable[player.getLeftCard().getCardNumber()] = true;
        isDrawable[player.getRightCard().getCardNumber()] = true;
        isDrawable[cardNumber1] = false;
        isDrawable[cardNumber2] = false;

        player.setLeftCard(cards[cardNumber1]);
        player.setRightCard(cards[cardNumber2]);
        return true;
    }


    public boolean canAssassinate(Player assassin, Player victim){
        if(!assassin.isAlive()) return false;
        if(!victim.isAlive()) return false;

        return assassin.getCoins() >= 3;
    }

    public boolean assassinate(Player assassin, Player victim, boolean attackLeftCard){
        if(!canAssassinate(assassin, victim)) return false;

        if(attackLeftCard) {
            if (!victim.getLeftCard().isAlive()) return false;
        }
        else{
            if (!victim.getRightCard().isAlive()) return false;
        }

        assassin.setCoins(assassin.getCoins() - 3);

        if(attackLeftCard) victim.getLeftCard().setAlive(false);
        else victim.getRightCard().setAlive(false);

        return true;
    }



    public boolean canSteal(Player captain, Player victim){
        return captain.isAlive() && victim.isAlive();
    }

    public boolean steal(Player captain, Player victim){
        canSteal(captain, victim);

        int numberOfStolenCoins = Math.min(victim.getCoins(), 2);

        captain.setCoins(captain.getCoins()+numberOfStolenCoins);
        victim.setCoins(victim.getCoins()-numberOfStolenCoins);
        return true;
    }



    public boolean foreignAid(Player player){
        if(!player.isAlive())return false;

        player.setCoins(player.getCoins()+2);
        return true;
    }

    public boolean takeFromTreasury(Player player){
        if(!player.isAlive())return false;

        player.setCoins(player.getCoins()+3);
        return true;
    }



    public boolean income(Player player){
        if(!player.isAlive())return false;

        player.setCoins(player.getCoins()+1);
        return true;
    }

    public boolean coup(Player coup, Player victim, boolean attackLeftCard){
        if(!coup.isAlive()) return false;
        if(!victim.isAlive()) return false;

        if(coup.getCoins() < 7) return false;

        if(attackLeftCard) {
            if (!victim.getLeftCard().isAlive()) return false;
        }
        else{
            if (!victim.getRightCard().isAlive()) return false;
        }

        coup.setCoins(coup.getCoins() - 7);

        if(attackLeftCard) victim.getLeftCard().setAlive(false);
        else victim.getRightCard().setAlive(false);

        return true;
    }

    public boolean exchange(Player player, boolean exchangeLeftCard){
        if(!player.isAlive())return false;
        
        if(player.getCoins() < 1)return false;
        
        int drawnCardNumber = getOneDrawableRandomCard().getCardNumber();

        if(exchangeLeftCard) {
            if (!player.getLeftCard().isAlive()) return false;
            isDrawable[player.getLeftCard().getCardNumber()] = true;
            isDrawable[drawnCardNumber] = false;
            player.setLeftCard(cards[drawnCardNumber]);
        }
        else{
            if (!player.getRightCard().isAlive()) return false;
            isDrawable[player.getRightCard().getCardNumber()] = true;
            isDrawable[drawnCardNumber] = false;
            player.setRightCard(cards[drawnCardNumber]);
        }

        player.setCoins(player.getCoins()-1);
        return true;
    }



    public Card getOneDrawableRandomCard(){
        Random random = new Random();
        int cardNumber = random.nextInt(15);
        while(!isDrawable[cardNumber]){
            cardNumber = random.nextInt(15);
        }
        return cards[cardNumber];
    }

    public Card[] getTwoDrawableRandomCard(){
        Random random = new Random();

        int cardNumber = random.nextInt(15);

        Card[] result = new Card[2];
        while(!isDrawable[cardNumber]){
            cardNumber = random.nextInt(15);
        }
        result[0] = cards[cardNumber];

        int cardNumber1 = random.nextInt(15);
        while(!isDrawable[cardNumber1] || cardNumber1 == cardNumber){
            cardNumber1 = random.nextInt(15);
        }
        result[1] = cards[cardNumber1];
        return result;
    }


    public Player getPlayer(int num){
        return players[num];
    }
}
