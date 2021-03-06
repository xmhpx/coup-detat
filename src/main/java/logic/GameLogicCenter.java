package logic;

import modules.*;
import modules.bots.CheaterCoupper;
import modules.bots.Coupper;
import modules.bots.Killer;
import modules.bots.Paranoid;
import modules.cardtypes.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.*;


public class GameLogicCenter {
    private static final Logger log = LogManager.getLogger(GameLogicCenter.class);

    private static GameLogicCenter INSTANCE;

    public static GameLogicCenter getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GameLogicCenter();
        }
        return INSTANCE;
    }

    public static void resetInstance(){
        INSTANCE = new GameLogicCenter();
    }


    protected static int startingCoins;


    protected DefaultPlayer[] players;
    protected Card[] cards;
    protected ArrayList<Move> moves;

    protected boolean[] isDrawable;
    protected int whoToPlay;


    private GameLogicCenter() {

        startingCoins = 2;
        players = new DefaultPlayer[4];
        cards = new Card[15];
        moves = new ArrayList<>();
        isDrawable = new boolean[15];
        Arrays.fill(isDrawable, true);
        whoToPlay = 0;

        for (int i = 0; i < 15; i++) {
            if (i < 3) {
                cards[i] = new Ambassador(true, i);
            } else if (i < 6) {
                cards[i] = new Assassin(true, i);
            } else if (i < 9) {
                cards[i] = new Captain(true, i);
            } else if (i < 12) {
                cards[i] = new Contessa(true, i);
            } else {
                cards[i] = new Duke(true, i);
            }
        }

        Settings settings = Settings.read();
        String[] bots = settings.getBots();
        int[] initialCardNumbers = settings.getInitialCardNumbers();
        startingCoins = settings.getStartingCoins();

        players[0] = new UIPlayer("PLAYER", null, null, startingCoins, DoerType.PLAYER);
        for(int i = 1; i < 4; i++){
            switch (bots[i - 1]) {
                case "CheaterCoupper" -> players[i] = new CheaterCoupper("BOT" + i, null, null, startingCoins, DoerType.valueOf("BOT" + i));
                case "Coupper" -> players[i] = new Coupper("BOT" + i, null, null, startingCoins, DoerType.valueOf("BOT" + i));
                case "Killer" -> players[i] = new Killer("BOT" + i, null, null, startingCoins, DoerType.valueOf("BOT" + i));
                case "Paranoid" -> players[i] = new Paranoid("BOT" + i, null, null, startingCoins, DoerType.valueOf("BOT" + i));
                case "DefaultPlayer" -> players[i] = new DefaultPlayer("BOT" + i, null, null, startingCoins, DoerType.valueOf("BOT" + i));
                default -> {
                    log.warn("bot" + i + " is an invalid bot name");
                    players[i] = new DefaultPlayer("BOT" + i, null, null, startingCoins, DoerType.valueOf("BOT" + i));
                }
            }
        }


        for(int i = 0; i < 8; i++) {
            int cardNumber = initialCardNumbers[i];
            if (cardNumber < 0 || cardNumber >= 15 || !isDrawable[cardNumber]) {
                log.info("initialCardNumber["+i+"] is random");
                cardNumber = getOneDrawableRandomCardNumber();
            }
            isDrawable[cardNumber] = false;
            initialCardNumbers[i] = cardNumber;
        }


        for (int i = 0; i < 4; i++) {
            Card leftCard = cards[initialCardNumbers[i*2]];
            players[i].setLeftCard(leftCard);

            Card rightCard = cards[initialCardNumbers[i*2+1]];
            players[i].setRightCard(rightCard);

            log.info("player(" + i + ") has " + leftCard.getName() + " and " + rightCard.getName());
        }
    }


    public boolean mustCoup(DefaultPlayer defaultPlayer) {
        return defaultPlayer.getCoins() >= 10;
    }

    public void addMove(DoerType doerType, MoveTarget moveTarget, MoveType moveType) {
        Move move = new Move(doerType, moveTarget, moveType);
        addMove(move);
    }

    public void addMove(Move move) {
        moves.add(move);
    }



    public String canAmbassadorExchange(DefaultPlayer defaultPlayer) {
        if (defaultPlayer == null) {
            return "defaultPlayer is null";
        }
        if (!defaultPlayer.isAlive()) return "doer is dead";
        if (defaultPlayer != players[whoToPlay]) {
            return "not your turn";
        }

        if (mustCoup(defaultPlayer)) return "must coup";

        return "";
    }

    public String ambassadorExchange(DefaultPlayer defaultPlayer) {
        String result = canAmbassadorExchange(defaultPlayer);
        if(result.length() > 0)return result;

        Move move = Move.getAmbassadorExchangeMove(defaultPlayer);
        addMove(move);


        lock = true;
        Thread thread = new Thread(()-> {
            String[] correctCardNames = {Ambassador.name};
            if (!continueActionAfterChallenges(defaultPlayer, move, correctCardNames)) {
                return;
            }

            Card[] newCards = defaultPlayer.ambassadorExchange(move);

            isDrawable[defaultPlayer.getLeftCard().getCardNumber()] = true;
            isDrawable[defaultPlayer.getRightCard().getCardNumber()] = true;
            String usedToHave = defaultPlayer.getLeftCard().getName() + ", " + defaultPlayer.getRightCard().getName();

            defaultPlayer.setLeftCard(newCards[0]);
            defaultPlayer.setRightCard(newCards[1]);

            isDrawable[defaultPlayer.getLeftCard().getCardNumber()] = false;
            isDrawable[defaultPlayer.getRightCard().getCardNumber()] = false;
            log.info(defaultPlayer.getName() + " used to have (" + usedToHave + ") now has (" + defaultPlayer.getLeftCard().getName() + " " + defaultPlayer.getRightCard().getName() + ")");

            lock = false;
        });

        thread.start();

        return "";
    }



    public String canAssassinate(DefaultPlayer assassin, DefaultPlayer victim) {
        if (assassin == null) {
            return "assassin is null";
        }
        if (victim == null) {
            return "victim is null";
        }
        if (!assassin.isAlive()) return "assassin is dead";
        if (!victim.isAlive()) return "victim is dead";
        if (assassin != players[whoToPlay]) {
            return "not your turn";
        }

        if (mustCoup(assassin)) return "must coup";

        if (assassin.getCoins() < 3) return "not enough coins";
        return "";
    }

    public String assassinate(DefaultPlayer assassin, DefaultPlayer victim) {
        String result = canAssassinate(assassin, victim);
        if(result.length() > 0)return result;

        Move move = Move.getAssassinateMove(assassin, victim);
        addMove(move);


        lock = true;
        Thread thread = new Thread(()-> {
            String[] correctCardNames = {Assassin.name};
            if (!continueActionAfterChallenges(assassin, move, correctCardNames)) {
                return;
            }

            if(!victim.isAlive())return;

            assassin.setCoins(assassin.getCoins() - 3);

            String[] correctInterveneCardNames = {Contessa.name};
            if (!continueActionAfterIntervene(victim, move, correctInterveneCardNames)) {
                return;
            }

            if(!victim.isAlive())return;

            boolean killLeftCard = victim.doesKillLeftCard(move);
            if (killLeftCard) {
                if (!victim.getLeftCard().isAlive()) killLeftCard = false;
            } else {
                if (!victim.getRightCard().isAlive()) killLeftCard = true;
            }
            killCardAndAddMove(victim, killLeftCard);
            lock = false;
        });

        thread.start();

        return "";
    }


    public String canSteal(DefaultPlayer captain, DefaultPlayer victim) {
        if (captain == null) {
            return "captain is null";
        }
        if (victim == null) {
            return "victim is null";
        }
        if (!captain.isAlive()) return "doer is dead";
        if (!victim.isAlive()) return "victim is dead";
        if (captain != players[whoToPlay]) {
            return "not your turn";
        }

        if (mustCoup(captain)) return "must coup";
        return "";
    }

    public String steal(DefaultPlayer captain, DefaultPlayer victim){
        String result = canSteal(captain, victim);
        if(result.length() > 0)return result;

        Move move = Move.getStealMove(captain, victim);
        addMove(move);

        lock = true;
        Thread thread = new Thread(()-> {
            String[] correctCardNames = {Captain.name};
            if(!continueActionAfterChallenges(captain, move, correctCardNames)){
                return;
            }


            String[] correctInterveneCardNames = {Captain.name, Ambassador.name};
            if(!continueActionAfterIntervene(victim, move, correctInterveneCardNames)){
                return;
            }

            int numberOfStolenCoins = Math.min(victim.getCoins(), 2);

            captain.setCoins(captain.getCoins()+numberOfStolenCoins);
            victim.setCoins(victim.getCoins()-numberOfStolenCoins);
            lock = false;
        });

        thread.start();
        return "";
    }



    public String canForeignAid(DefaultPlayer defaultPlayer) {
        if (defaultPlayer == null) {
            return "defaultPlayer is null";
        }
        if (!defaultPlayer.isAlive()) return "doer is dead";
        if (defaultPlayer != players[whoToPlay]) {
            return "not your turn";
        }

        if (mustCoup(defaultPlayer)) return "must coup";
        return "";
    }

    public String foreignAid(DefaultPlayer defaultPlayer) {
        String result = canForeignAid(defaultPlayer);
        if(result.length() > 0)return result;

        Move move = Move.getForeignAidMove(defaultPlayer);
        addMove(move);

        lock = true;
        Thread thread = new Thread(()-> {
            String[] correctInterveneCardNames = {Duke.name};
            if (!continueActionAfterInterveneAll(defaultPlayer, move, correctInterveneCardNames)) {
                return;
            }

            defaultPlayer.setCoins(defaultPlayer.getCoins() + 2);
            lock = false;
        });

        thread.start();
        return "";
    }



    public String canTakeFromTreasury(DefaultPlayer duke) {
        if(duke == null){
            return "duke is null";
        }
        if(!duke.isAlive())return "doer is dead";
        if(duke != players[whoToPlay]){
            return "not your turn";
        }

        if(mustCoup(duke))return "must coup";

        return "";
    }

    public String takeFromTreasury(DefaultPlayer duke){
        String result = canTakeFromTreasury(duke);
        if(result.length() > 0)return result;

        Move move = Move.getTakeFromTreasuryMove(duke);
        addMove(move);

        lock = true;
        Thread thread = new Thread(()-> {
            String[] correctCardNames = {Duke.name};
            if(!continueActionAfterChallenges(duke, move, correctCardNames)){
                return;
            }


            duke.setCoins(duke.getCoins()+3);
            lock = false;
        });

        thread.start();
        return "";
    }



    public String canIncome(DefaultPlayer defaultPlayer){
        if(defaultPlayer == null){
            return "defaultPlayer is null";
        }
        if(!defaultPlayer.isAlive())return "doer is dead";
        if(defaultPlayer != players[whoToPlay]){
            return "not your turn";
        }

        if(mustCoup(defaultPlayer))return "must coup";
        return "";
    }

    public String income(DefaultPlayer defaultPlayer){
        String result = canIncome(defaultPlayer);
        if(result.length() > 0)return result;

        defaultPlayer.setCoins(defaultPlayer.getCoins()+1);

        addMove(defaultPlayer.getType(), MoveTarget.CENTER, MoveType.INCOME);
        return "";
    }



    public String canCoup(DefaultPlayer coup, DefaultPlayer victim){
        if(coup == null){
            return "doer is null";
        }
        if(victim == null){
            return "victim is null";
        }
        if(!coup.isAlive()) return "doer is dead";
        if(!victim.isAlive()) return "victim is dead";
        if(coup != players[whoToPlay]){
            return "not your turn";
        }

        if(coup.getCoins() < 7) return "not enough coins";

        return "";
    }

    public String coup(DefaultPlayer coup, DefaultPlayer victim){
        String result = canCoup(coup, victim);
        if(result.length() > 0)return result;

        Move move = Move.getCoupMove(coup, victim);


        coup.setCoins(coup.getCoins() - 7);

        addMove(move);

        lock = true;
        Thread thread = new Thread(()-> {

            boolean killLeftCard = victim.doesKillLeftCard(move);
            if (killLeftCard) {
                if (!victim.getLeftCard().isAlive()) killLeftCard = false;
            } else {
                if (!victim.getRightCard().isAlive()) killLeftCard = true;
            }

            if (killLeftCard) victim.getLeftCard().setAlive(false);
            else victim.getRightCard().setAlive(false);

            lock = false;
        });
        thread.start();

        return "";
    }



    public String canSwapOne(DefaultPlayer defaultPlayer, Boolean exchangeLeftCard) {
        if (defaultPlayer == null) {
            return "doer is null";
        }
        if (exchangeLeftCard == null) {
            return "exchangeLeftCard is null";
        }

        if (!defaultPlayer.isAlive()) return "doer is dead";
        if (defaultPlayer != players[whoToPlay]) {
            return "not your turn";
        }

        if (defaultPlayer.getCoins() < 1) return "not enough coins";

        if (mustCoup(defaultPlayer)) return "must coup";

        return "";
    }

    public String swapOne(DefaultPlayer defaultPlayer, Boolean exchangeLeftCard){
        String result = canSwapOne(defaultPlayer, exchangeLeftCard);
        if(result.length() > 0)return result;
        
        int drawnCardNumber = getOneDrawableRandomCard().getCardNumber();

        if(exchangeLeftCard) {
            if (!defaultPlayer.getLeftCard().isAlive()) return "targeted card(left) is dead";
            isDrawable[defaultPlayer.getLeftCard().getCardNumber()] = true;
            isDrawable[drawnCardNumber] = false;
            defaultPlayer.setLeftCard(cards[drawnCardNumber]);
        }
        else{
            if (!defaultPlayer.getRightCard().isAlive()) return "targeted card(right) is dead";
            isDrawable[defaultPlayer.getRightCard().getCardNumber()] = true;
            isDrawable[drawnCardNumber] = false;
            defaultPlayer.setRightCard(cards[drawnCardNumber]);
        }

        defaultPlayer.setCoins(defaultPlayer.getCoins()-1);

        addMove(defaultPlayer.getType(), MoveTarget.CENTER, MoveType.SWAP_ONE);
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



    public void changeCard(DefaultPlayer defaultPlayer, boolean changeLeft){
        if(defaultPlayer == null){
            log.error("defaultPlayer is null");
            return;
        }
        if(changeLeft) {
            if (!defaultPlayer.getLeftCard().isAlive()) {
                log.error("can't change a dead card");
            }
            isDrawable[defaultPlayer.getLeftCard().getCardNumber()] = true;
            Card newCard = getOneDrawableRandomCard();
            log.info(defaultPlayer.getName() + " changed " + defaultPlayer.getLeftCard().getName() + " to " + newCard.getName());
            isDrawable[newCard.getCardNumber()] = false;
            defaultPlayer.setLeftCard(newCard);


        }
        else{
            if (!defaultPlayer.getRightCard().isAlive()) {
                log.error("can't change a dead card");
            }
            isDrawable[defaultPlayer.getRightCard().getCardNumber()] = true;
            Card newCard = getOneDrawableRandomCard();
            log.info(defaultPlayer + " changed " + defaultPlayer.getRightCard().getName() + " to " + newCard.getName());
            isDrawable[newCard.getCardNumber()] = false;
            defaultPlayer.setRightCard(newCard);

        }
    }

    public void killCardAndAddMove(DefaultPlayer defaultPlayer, boolean killLeft){
        killCard(defaultPlayer, killLeft);

        Card shownCard;
        if(killLeft){
            shownCard = defaultPlayer.getLeftCard();
        }
        else{
            shownCard = defaultPlayer.getRightCard();
        }

        Move showCardMove = Move.getShowCardMove(defaultPlayer, shownCard);
        addMove(showCardMove);
    }

    public void killCard(DefaultPlayer defaultPlayer, boolean killLeft){
        if(defaultPlayer == null){
            log.error("defaultPlayer is null");
            return;
        }

        if(killLeft) {
            if (!defaultPlayer.getLeftCard().isAlive()) {
                log.error("can't kill a dead card");
            }
            defaultPlayer.getLeftCard().setAlive(false);

        }
        else{
            if (!defaultPlayer.getRightCard().isAlive()) {
                log.error("can't kill a dead card");
            }
            defaultPlayer.getRightCard().setAlive(false);

        }

    }



    public boolean continueActionAfterChallenges(DefaultPlayer doer, Move move, String[] correctCardNames){
        for(int i = 3; i >= 0; i--){
            DefaultPlayer challenger = getPlayer(i);
            if(challenger != doer && challenger.isAlive()){
                if(challenger.doesChallenge(move)){
                    Move challengeMove = Move.getChallengeMove(doer, challenger);
                    log.info(challengeMove + " |challenged| " + move);

                    addMove(challengeMove);

                    Card shownCard;
                    boolean shownLeft;
                    if(doer.doesShowLeftCardWhenChallenged(move)){
                        shownCard = doer.getLeftCard();
                        shownLeft = true;
                    }
                    else{
                        shownCard = doer.getRightCard();
                        shownLeft = false;
                    }

                    if(Arrays.asList(correctCardNames).contains(shownCard.getName())) {
                        changeCard(doer, shownLeft);
                        boolean killLeftCard = challenger.doesKillLeftCard(challengeMove);
                        if (killLeftCard) {
                            if (!challenger.getLeftCard().isAlive()) killLeftCard = false;
                        } else {
                            if (!challenger.getRightCard().isAlive()) killLeftCard = true;
                        }
                        killCardAndAddMove(challenger, killLeftCard);
                    }
                    else{
                        killCardAndAddMove(doer, shownLeft);
                        return false;
                    }
                    break;
                }
            }
        }
        return true;
    }

    public boolean continueActionAfterIntervene(DefaultPlayer intervener, Move move, String[] correctInterveneCardNames){
        if(intervener.doesIntervene(move)){
            Move interveneMove = Move.getInterveneMove(intervener, move);
            log.info(interveneMove + " |intervened| " + move);

            addMove(interveneMove);

            return !continueActionAfterChallenges(intervener, interveneMove, correctInterveneCardNames);
        }
        return true;
    }

    public boolean continueActionAfterInterveneAll(DefaultPlayer doer, Move move, String[] correctInterveneCardNames){
        for(int i = 3; i >= 0; i--) {
            DefaultPlayer intervener = getPlayer(i);
            if (intervener != doer && intervener.isAlive()) {
                if (intervener.doesIntervene(move)) {
                    Move interveneMove = Move.getInterveneMove(intervener, move);
                    log.info(interveneMove + " |intervened| " + move);

                    addMove(interveneMove);

                    if(continueActionAfterChallenges(intervener, interveneMove, correctInterveneCardNames)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }



    protected boolean lock = false;

    public void play(){
        whoToPlay = getWhoToPlay();

        DefaultPlayer doer = players[whoToPlay];
        Move move = doer.getMove();

        if (!takeAction(doer, move)) {
            log.error(move + " is an invalid move");
            return;
        }

        whoToPlay++;
        whoToPlay = getWhoToPlay();
        lock = false;
    }


    private boolean takeAction(DefaultPlayer doer, Move move) {
        log.info(move);

        if(move.getMoveType() == MoveType.COUP) {
            DefaultPlayer victim = getPlayerFromMoveTarget(move.getMoveTarget());

            String result = coup(doer, victim);
            if(result.length() > 0){
                log.error("bot's move is invalid " + move);
                return false;
            }
            return true;
        }

        if(move.getMoveType() == MoveType.TAKE_FROM_TREASURY) {
            String result = takeFromTreasury(doer);
            if(result.length() > 0){
                log.error("bot's move is invalid " + move);
                return false;
            }
            return true;
        }

        if(move.getMoveType() == MoveType.INCOME) {
            String result = income(doer);
            if(result.length() > 0){
                log.error("bot's move is invalid " + move);
                return false;
            }
            return true;
        }

        if(move.getMoveType() == MoveType.FOREIGN_AID) {
            String result = foreignAid(doer);
            if(result.length() > 0){
                log.error("bot's move is invalid " + move);
                return false;
            }
            return true;
        }

        if(move.getMoveType() == MoveType.SWAP_ONE) {
            boolean swapLeft = MoveTarget.LEFT == move.getMoveTarget();
            if(swapLeft){
                if(!doer.getLeftCard().isAlive()) {
                    swapLeft = false;
                }
            }
            else{
                if(!doer.getRightCard().isAlive()) {
                    swapLeft = true;
                }
            }

            String result = swapOne(doer, swapLeft);
            if(result.length() > 0){
                log.error("bot's move is invalid " + move);
                return false;
            }
            return true;
        }

        if(move.getMoveType() == MoveType.ASSASSINATE) {
            DefaultPlayer victim = getPlayerFromMoveTarget(move.getMoveTarget());

            String result = assassinate(doer, victim);
            if(result.length() > 0){
                log.error("bot's move is invalid " + move);
                return false;
            }
            return true;
        }

        if(move.getMoveType() == MoveType.STEAL) {
            DefaultPlayer victim = getPlayerFromMoveTarget(move.getMoveTarget());

            String result = steal(doer, victim);
            if(result.length() > 0){
                log.error("bot's move is invalid " + move);
                return false;
            }
            return true;
        }

        if(move.getMoveType() == MoveType.AMBASSADOR_EXCHANGE) {

            String result = ambassadorExchange(doer);
            if(result.length() > 0){
                log.error("bot's move is invalid " + move);
                return false;
            }
            return true;
        }


        return false;
    }



    private DefaultPlayer getPlayerFromMoveTarget(MoveTarget moveTarget) {
        DefaultPlayer victim = getPlayer(0);

        if (moveTarget == MoveTarget.PLAYER) {
            victim = getPlayer(0);
        }

        if (moveTarget == MoveTarget.BOT1) {
            victim = getPlayer(1);
        }

        if (moveTarget == MoveTarget.BOT2) {
            victim = getPlayer(2);
        }

        if (moveTarget == MoveTarget.BOT3) {
            victim = getPlayer(3);
        }

        return victim;
    }

    public DefaultPlayer getPlayer(int playerNumber){
        return players[playerNumber];
    }



    // getters and setters

    public ArrayList<Move> getMoves() {
        return moves;
    }


    public DefaultPlayer[] getPlayers() {
        return players;
    }

    public void setPlayers(DefaultPlayer[] defaultPlayers) {
        this.players = defaultPlayers;
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


    public int getWhoToPlay(){
        if(whoToPlay >= 4)whoToPlay -= 4;
        while(!players[whoToPlay].isAlive()){
            whoToPlay++;
            if(whoToPlay >= 4)whoToPlay -= 4;
        }
        return whoToPlay;
    }


    public boolean getLock(){
        return lock;
    }
}
