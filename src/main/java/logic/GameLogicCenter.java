package logic;

import modules.*;
import modules.cardtypes.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
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
    protected ArrayList<Move> moves;

    protected boolean[] isDrawable;


    private GameLogicCenter(){
        players = new Player[4];
        moves = new ArrayList<>();
        cards = new Card[15];
        isDrawable = new boolean[15];

        players[0] = new Player("PLAYER", null, null, startingCoins, DoerType.PLAYER);
        players[1] = new Player("BOT1", null, null, startingCoins, DoerType.BOT1);
        players[2] = new Player("BOT2", null, null, startingCoins, DoerType.BOT2);
        players[3] = new Player("BOT3", null, null, startingCoins, DoerType.BOT3);


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


    public boolean mustCoup(Player player){
        return player.getCoins() >= 10;
    }

    public void addMove(DoerType doerType, MoveTarget moveTarget, MoveType moveType){
        Move move = new Move(doerType, moveTarget, moveType);
        moves.add(move);
    }


    //TODO buggy if exchanges cards with his own cards

    public String ambassadorExchangeOne(Player player, int drawnCardNumber, boolean exchangeLeftCard){
        if(player == null){
            return "player is null";
        }
        if(!player.isAlive())return "doer is dead";

        if(mustCoup(player))return "must coup";

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

        addMove(player.getType(), MoveTarget.CENTER, MoveType.AMBASSADOR_EXCHANGE);
        return "";
    }

    public String ambassadorExchangeTwo(Player player, int cardNumber1, int cardNumber2){
        if(player == null){
            return "player is null";
        }
        if(!player.isAlive())return "doer is dead";

        if(mustCoup(player))return "must coup";

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

        addMove(player.getType(), MoveTarget.CENTER, MoveType.AMBASSADOR_EXCHANGE);
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

        if(mustCoup(assassin))return "must coup";

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

        addMove(assassin.getType(), MoveTarget.valueOf(""+victim.getType()), MoveType.ASSASSINATE);
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

        if(mustCoup(captain))return "must coup";

        int numberOfStolenCoins = Math.min(victim.getCoins(), 2);

        captain.setCoins(captain.getCoins()+numberOfStolenCoins);
        victim.setCoins(victim.getCoins()-numberOfStolenCoins);

        addMove(captain.getType(), MoveTarget.valueOf(""+victim.getType()), MoveType.STEAL);
        return "";
    }



    public String foreignAid(Player player){
        if(player == null){
            return "player is null";
        }
        if(!player.isAlive())return "doer is dead";

        if(mustCoup(player))return "must coup";

        player.setCoins(player.getCoins()+2);

        addMove(player.getType(), MoveTarget.CENTER, MoveType.FOREIGN_AID);
        return "";
    }

    public String takeFromTreasury(Player player){
        if(player == null){
            return "player is null";
        }
        if(!player.isAlive())return "doer is dead";

        if(mustCoup(player))return "must coup";

        player.setCoins(player.getCoins()+3);

        addMove(player.getType(), MoveTarget.CENTER, MoveType.TAKE_FROM_TREASURY);
        return "";
    }



    public String income(Player player){
        if(player == null){
            return "player is null";
        }
        if(!player.isAlive())return "doer is dead";

        if(mustCoup(player))return "must coup";

        player.setCoins(player.getCoins()+1);

        addMove(player.getType(), MoveTarget.CENTER, MoveType.INCOME);
        return "";
    }

    public String coup(Player coup, Player victim, boolean attackLeftCard){
        if(coup == null){
            return "doer is null";
        }
        if(victim == null){
            return "victim is null";
        }
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

        addMove(coup.getType(), MoveTarget.valueOf(""+victim.getType()), MoveType.COUP);
        return "";
    }

    public String swapOne(Player player, boolean exchangeLeftCard){
        if(player == null){
            return "doer is null";
        }
        if(!player.isAlive())return "doer is dead";
        
        if(player.getCoins() < 1)return "not enough coins";

        if(mustCoup(player))return "must coup";
        
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

        addMove(player.getType(), MoveTarget.CENTER, MoveType.SWAP_ONE);
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
        int aliveCount = 0;
        for(int i = 1; i < 4; i++){
            if(players[i].isAlive()) {
                aliveCount++;
                if (income(players[i]).length() > 0) log.error("bot" + i + "'s move is invalid");
            }
        }
        if(aliveCount == 0){
            return;
        }
    }


    // getters and setters

    public ArrayList<Move> getMoves() {
        return moves;
    }

    public void setMoves(ArrayList<Move> moves) {
        this.moves = moves;
    }


    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }


    public Card[] getCards() {
        return cards;
    }

    public void setCards(Card[] cards) {
        this.cards = cards;
    }


    public boolean[] getIsDrawable() {
        return isDrawable;
    }

    public void setIsDrawable(boolean[] isDrawable) {
        this.isDrawable = isDrawable;
    }
}
