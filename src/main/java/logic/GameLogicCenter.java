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


    protected static int startingCoins = 2;


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

    public String ambassadorExchangeOne(Player player, int drawnCardNumber, boolean exchangeLeftCard){
        if(player == null){
            return "player is null";
        }
        if(!player.isAlive())return "doer is dead";

        if(!isDrawable[drawnCardNumber]){
            return "targeted card("+drawnCardNumber+") is not drawable";
        }

        if(exchangeLeftCard) {
            if (!player.getLeftCard().isAlive()) return "targeted card(left) is not drawable";
            int exchangedCardNumber = player.getLeftCard().getCardNumber();
            isDrawable[exchangedCardNumber] = true;
            isDrawable[drawnCardNumber] = false;
            player.setLeftCard(cards[drawnCardNumber]);
        }
        else{
            if (!player.getRightCard().isAlive()) return "targeted card(right) is not drawable";
            int exchangedCardNumber = player.getRightCard().getCardNumber();
            isDrawable[exchangedCardNumber] = true;
            isDrawable[drawnCardNumber] = false;
            player.setRightCard(cards[drawnCardNumber]);
        }

        return "";
    }

    public String ambassadorExchangeTwo(Player player, int cardNumber1, int cardNumber2){
        if(player == null){
            return "player is null";
        }
        if(!player.isAlive())return "doer is dead";

        if(!isDrawable[cardNumber1]){
            return "targeted card("+cardNumber1+") is not drawable";
        }
        if(!isDrawable[cardNumber2]){
            return "targeted card("+cardNumber2+") is not drawable";
        }

        if(!player.getLeftCard().isAlive())return "targeted card(left) is not drawable";
        if(!player.getRightCard().isAlive())return "targeted card(right) is not drawable";

        isDrawable[player.getLeftCard().getCardNumber()] = true;
        isDrawable[player.getRightCard().getCardNumber()] = true;
        isDrawable[cardNumber1] = false;
        isDrawable[cardNumber2] = false;

        player.setLeftCard(cards[cardNumber1]);
        player.setRightCard(cards[cardNumber2]);
        return "";
    }



    public String assassinate(Player assassin, Player victim, boolean attackLeftCard){
        if(assassin == null){
            return "assassin is null";
        }
        if(victim == null){
            return "victim is null";
        }
        if(!assassin.isAlive())return "assassin is dead";
        if(!victim.isAlive())return "victim is dead";

        if(assassin.getCoins() < 3) return "not enough coins";

        if(attackLeftCard) {
            if (!victim.getLeftCard().isAlive()) return "targeted card(left) is dead";
        }
        else{
            if (!victim.getRightCard().isAlive()) return "targeted card(right) is dead";
        }

        assassin.setCoins(assassin.getCoins() - 3);

        if(attackLeftCard) victim.getLeftCard().setAlive(false);
        else victim.getRightCard().setAlive(false);

        return "";
    }



    public String steal(Player captain, Player victim){
        if(captain == null){
            return "captain is null";
        }
        if(victim == null){
            return "victim is null";
        }
        if(!captain.isAlive())return "doer is dead";
        if(!victim.isAlive())return "victim is dead";

        int numberOfStolenCoins = Math.min(victim.getCoins(), 2);

        captain.setCoins(captain.getCoins()+numberOfStolenCoins);
        victim.setCoins(victim.getCoins()-numberOfStolenCoins);
        return "";
    }



    public String foreignAid(Player player){
        if(!player.isAlive())return "doer is dead";

        player.setCoins(player.getCoins()+2);
        return "";
    }

    public String takeFromTreasury(Player player){
        if(!player.isAlive())return "doer is dead";

        player.setCoins(player.getCoins()+3);
        return "";
    }



    public String income(Player player){
        if(!player.isAlive())return "doer is dead";

        player.setCoins(player.getCoins()+1);
        return "";
    }

    public String coup(Player coup, Player victim, boolean attackLeftCard){
        if(victim == null) return "select a victim";
        if(!coup.isAlive()) return "doer is dead";
        if(!victim.isAlive()) return "victim is dead";

        if(coup.getCoins() < 7) return "not enough coins";

        if(attackLeftCard) {
            if (!victim.getLeftCard().isAlive()) return "targeted card(left) is dead";
        }
        else{
            if (!victim.getRightCard().isAlive()) return "targeted card(right) is dead";
        }

        coup.setCoins(coup.getCoins() - 7);

        if(attackLeftCard) victim.getLeftCard().setAlive(false);
        else victim.getRightCard().setAlive(false);

        return "";
    }

    public String swapOne(Player player, boolean exchangeLeftCard){
        if(!player.isAlive())return "doer is dead";
        
        if(player.getCoins() < 1)return "not enough coins";
        
        int drawnCardNumber = getOneDrawableRandomCard().getCardNumber();

        if(exchangeLeftCard) {
            if (!player.getLeftCard().isAlive()) return "targeted card(left) is dead";
            isDrawable[player.getLeftCard().getCardNumber()] = true;
            isDrawable[drawnCardNumber] = false;
            player.setLeftCard(cards[drawnCardNumber]);
        }
        else{
            if (!player.getRightCard().isAlive()) return "targeted card(right) is dead";
            isDrawable[player.getRightCard().getCardNumber()] = true;
            isDrawable[drawnCardNumber] = false;
            player.setRightCard(cards[drawnCardNumber]);
        }

        player.setCoins(player.getCoins()-1);
        return "";
    }



    public int getOneDrawableRandomCardNumber(){
        Random random = new Random();
        int cardNumber = random.nextInt(15);
        while(!isDrawable[cardNumber]){
            cardNumber = random.nextInt(15);
        }
        return cardNumber;
    }

    public Card getOneDrawableRandomCard(){
        return cards[getOneDrawableRandomCardNumber()];
    }


    public int[] getTwoDrawableRandomCardNumber(){
        Random random = new Random();

        int[] cardNumbers = new int[2];
        cardNumbers[0] = random.nextInt(15);
        cardNumbers[1] = random.nextInt(15);

        while(!isDrawable[cardNumbers[0]]){
            cardNumbers[0] = random.nextInt(15);
        }
        while((!isDrawable[cardNumbers[1]]) || cardNumbers[0] == cardNumbers[1]){
            cardNumbers[1] = random.nextInt(15);
        }

        return cardNumbers;
    }


    public Card[] getTwoDrawableRandomCard(){
        int[] cardNumber = getTwoDrawableRandomCardNumber();

        Card[] result = new Card[2];

        result[0] = cards[cardNumber[0]];
        result[1] = cards[cardNumber[1]];
        return result;
    }


    public Player getPlayer(int playerNumber){
        return players[playerNumber];
    }


    public void play(){
        for(int i = 1; i < 4; i++){
            income(players[i]);
        }
    }
}
