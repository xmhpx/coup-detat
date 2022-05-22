package pagecontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.text.Text;
import logic.GameLogicCenter;
import modules.ActionInfo;
import modules.ActionName;
import modules.DefaultPlayer;
import modules.Move;
import modules.UIPlayer;
import modules.cardtypes.Card;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class GamePageController extends BasicPageController{
    private static final Logger log = LogManager.getLogger(GamePageController.class);

    public static final String fxmlFileName = "gamePage.fxml";


    @FXML
    Button playerLeftCardButton;
    @FXML
    Button playerRightCardButton;

    @FXML
    Button bot1LeftCardButton;
    @FXML
    Button bot1RightCardButton;

    @FXML
    Button bot2LeftCardButton;
    @FXML
    Button bot2RightCardButton;

    @FXML
    Button bot3LeftCardButton;
    @FXML
    Button bot3RightCardButton;


    @FXML
    Text playerCoinsText;
    @FXML
    Text bot1CoinsText;
    @FXML
    Text bot2CoinsText;
    @FXML
    Text bot3CoinsText;


    @FXML
    Button incomeButton;
    @FXML
    Button foreignAidButton;
    @FXML
    Button coupButton;
    @FXML
    Button swapOneButton;

    Boolean swapLeft = null;


    @FXML
    Button ambassadorExchangeButton;
    @FXML
    Button stealButton;
    @FXML
    Button assassinateButton;
    @FXML
    Button takeFromTreasuryButton;

    @FXML
    TextField targetTextField;
    @FXML
    TextField leftOrRightTextField;



    @FXML
    Button confirmButton;

    @FXML
    Label massageLabel;


    @FXML
    TableView<Move> tableView;


    @FXML
    Label cardActionInfoLabel;
    @FXML
    Label actionToBeConfirmedLabel;


    private Button selectedButton;


    @Override
    public void initialize(){
        super.initialize();

        selectedButton = null;
        var data = tableView.getItems();
        data.clear();
        GameLogicCenter.resetInstance();
        data.addAll(GameLogicCenter.getInstance().getMoves());
        refresh();

        PageControllerStorage.getInstance().setGamePageController(this);

        Thread clock = new Thread() {
            public void run() {
                for (int i = 1; i < 10; i++) {
                    i--;
                    loadCoinsAndCardsAndTable();
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        clock.start();
    }


    public void pressButton(Button button){
        swapLeft = null;
        if(selectedButton != null){
            selectedButton.setStyle("");
        }

        if(selectedButton == button){
            selectedButton = null;
        }
        else {
            selectedButton = button;
        }

        if(selectedButton != null){
            selectedButton.setStyle("-fx-border-color:#ff0000");
        }
        refresh();
    }


    public String getTarget(){
        String target = null;
        if(targetTextField.getText().equals("1"))
            target = "bot1";
        if(targetTextField.getText().equals("2"))
            target = "bot2";
        if(targetTextField.getText().equals("3"))
            target = "bot3";
        return target;
    }

    public Boolean getIsLeft(){
        Boolean isLeft = null;
        if(leftOrRightTextField.getText().equals("left"))isLeft = true;
        if(leftOrRightTextField.getText().equals("right"))isLeft = false;
        return isLeft;
    }


    public DefaultPlayer getTargetedPlayer(){
        GameLogicCenter backend = GameLogicCenter.getInstance();

        String target = getTarget();
        if(target == null)return null;
        if(target.equals("player")){
            return backend.getPlayer(0);

        }
        else if(target.equals("bot1")){
            return backend.getPlayer(1);

        }
        else if(target.equals("bot2")){
            return backend.getPlayer(2);

        }
        else if(target.equals("bot3")){
            return backend.getPlayer(3);

        }
        else return null;
    }


    public void refresh(){
        GameLogicCenter backend = GameLogicCenter.getInstance();

        DefaultPlayer defaultPlayer = backend.getPlayer(0);
        actionToBeConfirmedLabel.setText("");
        cardActionInfoLabel.setText("");
        massageLabel.setText("");
        if(selectedButton != null){
            if(selectedButton == playerLeftCardButton){
                Card card = backend.getPlayer(0).getLeftCard();
                cardActionInfoLabel.setText(card.getDescription());

            }
            else if(selectedButton == playerRightCardButton){
                Card card = backend.getPlayer(0).getRightCard();
                cardActionInfoLabel.setText(card.getDescription());

            }
            else if(selectedButton == bot1LeftCardButton){
                Card card = backend.getPlayer(1).getLeftCard();
                if(!card.isAlive()) {
                    cardActionInfoLabel.setText(card.getDescription());
                }

            }
            else if(selectedButton == bot1RightCardButton){
                Card card = backend.getPlayer(1).getRightCard();
                if(!card.isAlive()) {
                    cardActionInfoLabel.setText(card.getDescription());
                }

            }
            else if(selectedButton == bot2LeftCardButton){
                Card card = backend.getPlayer(2).getLeftCard();
                if(!card.isAlive()) {
                    cardActionInfoLabel.setText(card.getDescription());
                }

            }
            else if(selectedButton == bot2RightCardButton){
                Card card = backend.getPlayer(2).getRightCard();
                if(!card.isAlive()) {
                    cardActionInfoLabel.setText(card.getDescription());
                }

            }
            else if(selectedButton == bot3LeftCardButton){
                Card card = backend.getPlayer(3).getLeftCard();
                if(!card.isAlive()) {
                    cardActionInfoLabel.setText(card.getDescription());
                }

            }
            else if(selectedButton == bot3RightCardButton){
                Card card = backend.getPlayer(3).getRightCard();
                if(!card.isAlive()) {
                    cardActionInfoLabel.setText(card.getDescription());
                }

            }
            else if(selectedButton == incomeButton){
                cardActionInfoLabel.setText(ActionInfo.incomeInfo());
                actionToBeConfirmedLabel.setText(ActionName.income());

            }
            else if(selectedButton == foreignAidButton){
                cardActionInfoLabel.setText(ActionInfo.foreignAidInfo());
                actionToBeConfirmedLabel.setText(ActionName.foreignAid());

            }
            else if(selectedButton == coupButton){
                String target = getTarget();
                if(target == null){
                    massageLabel.setText("choose target to coup d'etat against");
                }
                else {
                    cardActionInfoLabel.setText(ActionInfo.coupInfo() + "\ntarget:" + target);
                    actionToBeConfirmedLabel.setText(ActionName.coup() + "\ntarget:" + target);
                }

            }
            else if(selectedButton == swapOneButton){
                Boolean isLeft = getIsLeft();
                cardActionInfoLabel.setText(ActionInfo.swapOneInfo());
                if(isLeft == null){
                    massageLabel.setText("choose which card to swap (left/right)");
                }
                else {
                    if (isLeft) {
                        if (!defaultPlayer.getLeftCard().isAlive()) {
                            massageLabel.setText("your selected card(left) is dead");
                        } else {
                            actionToBeConfirmedLabel.setText(ActionName.swapOne()+"(left)");
                            swapLeft = true;
                        }
                    } else {
                        if (!defaultPlayer.getRightCard().isAlive()) {
                            massageLabel.setText("your selected card(right) is dead");
                        } else {
                            actionToBeConfirmedLabel.setText(ActionName.swapOne()+"(right)");
                            swapLeft = false;
                        }

                    }
                }

            }
            else if(selectedButton == ambassadorExchangeButton){
                cardActionInfoLabel.setText(ActionInfo.ambassadorExchangeInfo());
                actionToBeConfirmedLabel.setText(ActionName.ambassadorExchange());

            }
            else if(selectedButton == stealButton){
                String target = getTarget();
                if(target == null){
                    massageLabel.setText("choose target to steal from");
                }
                else {
                    cardActionInfoLabel.setText(ActionInfo.stealInfo() + "\ntarget:" + target);
                    actionToBeConfirmedLabel.setText(ActionName.steal() + "\ntarget:" + target);
                }

            }
            else if(selectedButton == assassinateButton){
                String target = getTarget();
                if(target == null){
                    massageLabel.setText("choose target to assassinate");
                }
                else {
                    cardActionInfoLabel.setText(ActionInfo.assassinateInfo() + "\ntarget:" + target);
                    actionToBeConfirmedLabel.setText(ActionName.assassinate() + "\ntarget:" + target);
                }

            }
            else if(selectedButton == takeFromTreasuryButton){
                cardActionInfoLabel.setText(ActionInfo.takeFromTreasuryInfo());
                actionToBeConfirmedLabel.setText(ActionName.takeFromTreasury());

            }
            else{
                cardActionInfoLabel.setText("Selected action/card not found");
                actionToBeConfirmedLabel.setText("Selected action not found");

            }
        }
        else{
            actionToBeConfirmedLabel.setText("Skip");
            cardActionInfoLabel.setText("don't challenge");
        }
        loadCoinsAndCardsAndTable();
    }



    public void loadCoinsAndCardsAndTable(){
        loadCards();
        loadCoins();
        loadTable();
    }

    public void loadTable(){
        var data = tableView.getItems();
        data.clear();
        data.addAll(GameLogicCenter.getInstance().getMoves());
    }

    public void loadCards(){
        GameLogicCenter backend = GameLogicCenter.getInstance();
        DefaultPlayer defaultPlayer = backend.getPlayer(0);
        DefaultPlayer bot1 = backend.getPlayer(1);
        DefaultPlayer bot2 = backend.getPlayer(2);
        DefaultPlayer bot3 = backend.getPlayer(3);

        BackgroundImage backgroundImage;

        BackgroundSize backgroundSize = new BackgroundSize(1, 1,
                true, true,
                false, false);

        if(defaultPlayer.getLeftCard().isAlive()) {
            backgroundImage = new BackgroundImage(defaultPlayer.getLeftCard().getImage(),
                    null, null, null, backgroundSize);
        }
        else{
            backgroundImage = new BackgroundImage(defaultPlayer.getLeftCard().getDeadImage(),
                    null, null, null, backgroundSize);
        }
        playerLeftCardButton.setBackground(new Background(backgroundImage));

        if(defaultPlayer.getRightCard().isAlive()) {
            backgroundImage = new BackgroundImage(defaultPlayer.getRightCard().getImage(),
                    null, null, null, backgroundSize);
        }
        else {
            backgroundImage = new BackgroundImage(defaultPlayer.getRightCard().getDeadImage(),
                    null, null, null, backgroundSize);
        }
        playerRightCardButton.setBackground(new Background(backgroundImage));


        if (bot1.getLeftCard().isAlive()) {
            backgroundImage = new BackgroundImage(Card.getBackImage(),
                    null, null, null, backgroundSize);
        } else {
            backgroundImage = new BackgroundImage(bot1.getLeftCard().getDeadImage(),
                    null, null, null, backgroundSize);
        }
        bot1LeftCardButton.setBackground(new Background(backgroundImage));

        if (bot1.getRightCard().isAlive()) {
            backgroundImage = new BackgroundImage(Card.getBackImage(),
                    null, null, null, backgroundSize);
        } else {
            backgroundImage = new BackgroundImage(bot1.getRightCard().getDeadImage(),
                    null, null, null, backgroundSize);
        }
        bot1RightCardButton.setBackground(new Background(backgroundImage));


        if(bot2.getLeftCard().isAlive()) {
            backgroundImage = new BackgroundImage(Card.getBackImage(),
                    null, null, null, backgroundSize);
        }
        else {
            backgroundImage = new BackgroundImage(bot2.getLeftCard().getDeadImage(),
                    null, null, null, backgroundSize);
        }
        bot2LeftCardButton.setBackground(new Background(backgroundImage));

        if (bot2.getRightCard().isAlive()) {
            backgroundImage = new BackgroundImage(Card.getBackImage(),
                    null, null, null, backgroundSize);
        } else {
            backgroundImage = new BackgroundImage(bot2.getRightCard().getDeadImage(),
                    null, null, null, backgroundSize);
        }
        bot2RightCardButton.setBackground(new Background(backgroundImage));


        if (bot3.getLeftCard().isAlive()) {
            backgroundImage = new BackgroundImage(Card.getBackImage(),
                    null, null, null, backgroundSize);
        } else {
            backgroundImage = new BackgroundImage(bot3.getLeftCard().getDeadImage(),
                    null, null, null, backgroundSize);
        }
        bot3LeftCardButton.setBackground(new Background(backgroundImage));

        if (bot3.getRightCard().isAlive()) {
            backgroundImage = new BackgroundImage(Card.getBackImage(),
                    null, null, null, backgroundSize);
        } else {
            backgroundImage = new BackgroundImage(bot3.getRightCard().getDeadImage(),
                    null, null, null, backgroundSize);
        }
        bot3RightCardButton.setBackground(new Background(backgroundImage));
    }

    public void loadCoins(){
        GameLogicCenter backend = GameLogicCenter.getInstance();
        DefaultPlayer defaultPlayer = backend.getPlayer(0);
        DefaultPlayer bot1 = backend.getPlayer(1);
        DefaultPlayer bot2 = backend.getPlayer(2);
        DefaultPlayer bot3 = backend.getPlayer(3);

        playerCoinsText.setText(""+ defaultPlayer.getCoins());
        bot1CoinsText.setText(""+bot1.getCoins());
        bot2CoinsText.setText(""+bot2.getCoins());
        bot3CoinsText.setText(""+bot3.getCoins());
    }




    @FXML
    Button exchangeCard1Button;
    @FXML
    Button exchangeCard2Button;
    @FXML
    Button exchangeCard3Button;
    @FXML
    Button exchangeCard4Button;

    protected Card[] exchangeCards = null;

    public void showExchangeCards(){
        GameLogicCenter backend = GameLogicCenter.getInstance();
        UIPlayer uiPlayer = (UIPlayer) backend.getPlayer(0);

        BackgroundImage backgroundImage;

        BackgroundSize backgroundSize = new BackgroundSize(1, 1,
                true, true,
                false, false);

        if(exchangeCards[0].isAlive()) {
            backgroundImage = new BackgroundImage(exchangeCards[0].getImage(),
                    null, null, null, backgroundSize);
        }
        else{
            backgroundImage = new BackgroundImage(exchangeCards[0].getDeadImage(),
                    null, null, null, backgroundSize);
        }
        exchangeCard1Button.setBackground(new Background(backgroundImage));


        if(exchangeCards[1].isAlive()) {
            backgroundImage = new BackgroundImage(exchangeCards[1].getImage(),
                    null, null, null, backgroundSize);
        }
        else{
            backgroundImage = new BackgroundImage(exchangeCards[1].getDeadImage(),
                    null, null, null, backgroundSize);
        }
        exchangeCard2Button.setBackground(new Background(backgroundImage));


        if(exchangeCards[2].isAlive()) {
            backgroundImage = new BackgroundImage(exchangeCards[2].getImage(),
                    null, null, null, backgroundSize);
        }
        else{
            backgroundImage = new BackgroundImage(exchangeCards[2].getDeadImage(),
                    null, null, null, backgroundSize);
        }
        exchangeCard3Button.setBackground(new Background(backgroundImage));


        if(exchangeCards[3].isAlive()) {
            backgroundImage = new BackgroundImage(exchangeCards[3].getImage(),
                    null, null, null, backgroundSize);
        }
        else{
            backgroundImage = new BackgroundImage(exchangeCards[3].getDeadImage(),
                    null, null, null, backgroundSize);
        }
        exchangeCard4Button.setBackground(new Background(backgroundImage));

        setExchangeCardsVisibility(true);
    }


    public void setExchangeCardsVisibility(boolean visibility){
        exchangeCard1Button.setVisible(visibility);
        exchangeCard2Button.setVisible(visibility);
        exchangeCard3Button.setVisible(visibility);
        exchangeCard4Button.setVisible(visibility);
    }



    @FXML
    void playerLeftCardButtonOnAction(ActionEvent actionEvent) {
        pressButton(playerLeftCardButton);
    }

    @FXML
    void playerRightCardButtonOnAction(ActionEvent actionEvent) {
        pressButton(playerRightCardButton);
    }


    @FXML
    void bot1LeftCardButtonOnAction(ActionEvent actionEvent) {
        pressButton(bot1LeftCardButton);
    }

    @FXML
    void bot1RightCardButtonOnAction(ActionEvent actionEvent) {
        pressButton(bot1RightCardButton);
    }


    @FXML
    void bot2LeftCardButtonOnAction(ActionEvent actionEvent) {
        pressButton(bot2LeftCardButton);
    }

    @FXML
    void bot2RightCardButtonOnAction(ActionEvent actionEvent) {
        pressButton(bot2RightCardButton);
    }


    @FXML
    void bot3LeftCardButtonOnAction(ActionEvent actionEvent) {
        pressButton(bot3LeftCardButton);
    }

    @FXML
    void bot3RightCardButtonOnAction(ActionEvent actionEvent) {
        pressButton(bot3RightCardButton);
    }



    @FXML
    void incomeButtonOnAction(ActionEvent actionEvent) {
        pressButton(incomeButton);
    }

    @FXML
    void foreignAidButtonOnAction(ActionEvent actionEvent) {
        pressButton(foreignAidButton);
    }

    @FXML
    void coupButtonOnAction(ActionEvent actionEvent) {
        pressButton(coupButton);
    }

    @FXML
    void swapOneButtonOnAction(ActionEvent actionEvent) {
        pressButton(swapOneButton);
    }


    @FXML
    void ambassadorExchangeButtonOnAction(ActionEvent actionEvent) {
        pressButton(ambassadorExchangeButton);
    }

    @FXML
    void stealButtonOnAction(ActionEvent actionEvent) {
        pressButton(stealButton);
    }

    @FXML
    void assassinateButtonOnAction(ActionEvent actionEvent) {
        pressButton(assassinateButton);
    }

    @FXML
    void takeFromTreasuryButtonOnAction(ActionEvent actionEvent) {
        pressButton(takeFromTreasuryButton);
    }



    @FXML
    void confirmButtonOnAction(ActionEvent actionEvent) {

        GameLogicCenter backend = GameLogicCenter.getInstance();

        if(decideIntervene || decideChallenge || decideKillLeftCard || decideShowLeftCardWhenChallenged) {
            UIPlayer uiPlayer = (UIPlayer) backend.getPlayer(0);

            uiPlayer.setDecided(true);

            clearInterveneDecision();
            clearChallengeDecision();
            clearKillLeftCardDecision();
            clearShowLeftCardWhenChallengedDecision();

            return;
        }

        if(decideAmbassadorExchange){
            UIPlayer uiPlayer = (UIPlayer) backend.getPlayer(0);

            int aliveCardsCnt = uiPlayer.getNumberOfAliveCards();
            int selectedCardsCnt = 0;

            if(chosenCard1 != null)selectedCardsCnt++;
            if(chosenCard2 != null)selectedCardsCnt++;
            if(chosenCard3 != null)selectedCardsCnt++;
            if(chosenCard4 != null)selectedCardsCnt++;

            if(aliveCardsCnt != selectedCardsCnt){
                massageLabel.setText("choose "+aliveCardsCnt+" cards");
                return;
            }

            uiPlayer.setDecided(true);

            clearAmbassadorExchange();

            return;
        }

        if(backend.getLock()){
            massageLabel.setText("LOCKED");
            return;
        }



        UIPlayer uIPlayer = (UIPlayer) backend.getPlayer(0);

        if(!uIPlayer.isAlive() || backend.getWhoToPlay() != 0){
            backend.play();
            return;
        }

        if(selectedButton == null){
            if(backend.getWhoToPlay() == 0){
                refresh();
                massageLabel.setText("you can't skip your turn");
                return;
            }
            else {
                backend.play();
            }
        }
        else{
            Move move = null;
            String result;
            if(selectedButton == incomeButton){
                result = backend.canIncome(uIPlayer);
                move = Move.getIncomeMove(uIPlayer);

            }
            else if(selectedButton == foreignAidButton){
                result = backend.canForeignAid(uIPlayer);
                move = Move.getForeignAidMove(uIPlayer);

            }
            else if(selectedButton == coupButton){
                DefaultPlayer victim = getTargetedPlayer();
                result = backend.canCoup(uIPlayer, victim);
                move = Move.getCoupMove(uIPlayer, victim);

            }
            else if(selectedButton == swapOneButton){
                if(swapLeft == null){
                    massageLabel.setText("choose left/right to swap");
                    return;
                }
                result = backend.canSwapOne(uIPlayer, swapLeft);
                move = Move.getSwapOneMove(uIPlayer, swapLeft);

            }
            else if(selectedButton == ambassadorExchangeButton){
                result = backend.canAmbassadorExchange(uIPlayer);
                move = Move.getAmbassadorExchangeMove(uIPlayer);

            }
            else if(selectedButton == stealButton){
                DefaultPlayer victim = getTargetedPlayer();
                result = backend.canSteal(uIPlayer, victim);
                move = Move.getStealMove(uIPlayer, victim);

            }
            else if(selectedButton == assassinateButton){
                DefaultPlayer victim = getTargetedPlayer();
                result = backend.canAssassinate(uIPlayer, victim);
                move = Move.getAssassinateMove(uIPlayer, victim);

            }
            else if(selectedButton == takeFromTreasuryButton){
                result = backend.canTakeFromTreasury(uIPlayer);
                move = Move.getTakeFromTreasuryMove(uIPlayer);

            }
            else{
                massageLabel.setText("invalid action");
                refresh();
                return;
            }

            if(result.length() == 0) {
                uIPlayer.setMove(move);
                backend.play();
            }
            else{
                refresh();
                massageLabel.setText(result);
                return;
            }
        }

        if(selectedButton != null) {
            pressButton(selectedButton);
        }
    }



    protected boolean decideAmbassadorExchange = false;
    protected boolean decideIntervene = false;
    protected boolean decideChallenge = false;
    protected boolean decideKillLeftCard = false;
    protected boolean decideShowLeftCardWhenChallenged = false;

    protected Card chosenCard1 = null;
    protected Card chosenCard2 = null;
    protected Card chosenCard3 = null;
    protected Card chosenCard4 = null;

    @FXML
    Button interveneButton;
    @FXML
    Button challengeButton;


    protected boolean doesIntervene = false;
    protected boolean doesChallenge = false;
    protected boolean doesKillLeftCard = false;
    protected boolean doesShowLeftCardWhenChallenged = false;



    public void decideAmbassadorExchange(Move move){
        chosenCard1 = null;
        chosenCard2 = null;
        chosenCard3 = null;
        chosenCard4 = null;

        exchangeCard1Button.setStyle("-fx-border-color:#ff0000");
        exchangeCard2Button.setStyle("-fx-border-color:#ff0000");
        exchangeCard3Button.setStyle("-fx-border-color:#ff0000");
        exchangeCard4Button.setStyle("-fx-border-color:#ff0000");

        UIPlayer uiPlayer = (UIPlayer) GameLogicCenter.getInstance().getPlayer(0);
        Card[] randomCards = GameLogicCenter.getInstance().getTwoDrawableRandomCard();
        exchangeCards = new Card[4];
        exchangeCards[0] = uiPlayer.getLeftCard();
        exchangeCards[1] = uiPlayer.getRightCard();
        exchangeCards[2] = randomCards[0];
        exchangeCards[3] = randomCards[1];

        setExchangeCardsVisibility(true);
        decideAmbassadorExchange = true;
        showExchangeCards();
    }

    public void decideIntervene(Move move){
        doesIntervene = false;
        interveneButton.setStyle("-fx-border-color:#ff0000");
        decideIntervene = true;
    }

    public void decideChallenge(Move move){
        doesChallenge = false;
        challengeButton.setStyle("-fx-border-color:#ff0000");
        decideChallenge = true;
    }

    public void decideKillLeftCard(Move move){
        doesKillLeftCard = false;
        leftOrRightTextField.setStyle("-fx-border-color:#ff0000");
        decideKillLeftCard = true;
    }

    public void decideShowLeftCardWhenChallenged(Move move){
        doesShowLeftCardWhenChallenged = false;
        leftOrRightTextField.setStyle("-fx-border-color:#ff0000");
        decideShowLeftCardWhenChallenged = true;
    }



    public void clearAmbassadorExchange(){
        exchangeCard1Button.setStyle("");
        exchangeCard2Button.setStyle("");
        exchangeCard3Button.setStyle("");
        exchangeCard4Button.setStyle("");
        setExchangeCardsVisibility(false);
        decideAmbassadorExchange = false;
    }

    public void clearInterveneDecision(){
        interveneButton.setStyle("");
        decideIntervene = false;
    }

    public void clearChallengeDecision(){
        challengeButton.setStyle("");
        decideChallenge = false;
    }

    public void clearKillLeftCardDecision(){
        leftOrRightTextField.setStyle("");
        decideKillLeftCard = false;
    }

    public void clearShowLeftCardWhenChallengedDecision(){
        leftOrRightTextField.setStyle("");
        decideShowLeftCardWhenChallenged = false;
    }




    @FXML
    void exchangeCard1ButtonOnAction(ActionEvent actionEvent) {
        if(GameLogicCenter.getInstance().getLock()){
            massageLabel.setText("LOCKED");
            return;
        }
        if(decideAmbassadorExchange) {
            if (chosenCard1 == null) {
                exchangeCard1Button.setStyle("-fx-border-color:#00ff00");
                chosenCard1 = exchangeCards[0];
            } else {
                exchangeCard1Button.setStyle("-fx-border-color:#ff0000");
                chosenCard1 = null;
            }
        }
    }
    @FXML
    void exchangeCard2ButtonOnAction(ActionEvent actionEvent) {
        if(GameLogicCenter.getInstance().getLock()){
            massageLabel.setText("LOCKED");
            return;
        }
        if(decideAmbassadorExchange) {
            if (chosenCard2 == null) {
                exchangeCard2Button.setStyle("-fx-border-color:#00ff00");
                chosenCard2 = exchangeCards[1];
            } else {
                exchangeCard2Button.setStyle("-fx-border-color:#ff0000");
                chosenCard2 = null;
            }
        }
    }
    @FXML
    void exchangeCard3ButtonOnAction(ActionEvent actionEvent) {
        if(GameLogicCenter.getInstance().getLock()){
            massageLabel.setText("LOCKED");
            return;
        }
        if(decideAmbassadorExchange) {
            if (chosenCard3 == null) {
                exchangeCard3Button.setStyle("-fx-border-color:#00ff00");
                chosenCard3 = exchangeCards[2];
            } else {
                exchangeCard3Button.setStyle("-fx-border-color:#ff0000");
                chosenCard3 = null;
            }
        }
    }
    @FXML
    void exchangeCard4ButtonOnAction(ActionEvent actionEvent) {
        if(GameLogicCenter.getInstance().getLock()){
            massageLabel.setText("LOCKED");
            return;
        }
        if(decideAmbassadorExchange) {
            if (chosenCard4 == null) {
                exchangeCard4Button.setStyle("-fx-border-color:#00ff00");
                chosenCard4 = exchangeCards[3];
            } else {
                exchangeCard4Button.setStyle("-fx-border-color:#ff0000");
                chosenCard4 = null;
            }
        }
    }


    @FXML
    void interveneButtonOnAction(ActionEvent actionEvent) {
        if(GameLogicCenter.getInstance().getLock()){
            massageLabel.setText("LOCKED");
            return;
        }
        if(decideIntervene) {
            if (!doesIntervene) {
                interveneButton.setStyle("-fx-border-color:#00ff00");
                doesIntervene = true;
            } else {
                interveneButton.setStyle("-fx-border-color:#ff0000");
                doesIntervene = false;
            }
        }
    }

    @FXML
    void challengeButtonOnAction(ActionEvent actionEvent) {
        if(GameLogicCenter.getInstance().getLock()){
            massageLabel.setText("LOCKED");
            return;
        }
        if(decideChallenge) {
            if (!doesChallenge) {
                challengeButton.setStyle("-fx-border-color:#00ff00");
                doesChallenge = true;
            } else {
                challengeButton.setStyle("-fx-border-color:#ff0000");
                doesChallenge = false;
            }
        }
    }




    public Card[] ambassadorExchange(Move move){
        Card[] exchangedCards = new Card[2];

        UIPlayer uiPlayer = (UIPlayer) GameLogicCenter.getInstance().getPlayer(0);

        int selectedCardsCnt = 0;

        if(!uiPlayer.getLeftCard().isAlive()){
            exchangedCards[0] = uiPlayer.getLeftCard();
            selectedCardsCnt++;
        }

        if(chosenCard1 != null && chosenCard1.isAlive()){
            exchangedCards[selectedCardsCnt] = chosenCard1;
            selectedCardsCnt++;
        }
        if(chosenCard2 != null && chosenCard2.isAlive()){
            exchangedCards[selectedCardsCnt] = chosenCard2;
            selectedCardsCnt++;
        }
        if(chosenCard3 != null && chosenCard3.isAlive()){
            exchangedCards[selectedCardsCnt] = chosenCard3;
            selectedCardsCnt++;
        }
        if(chosenCard4 != null && chosenCard4.isAlive()){
            exchangedCards[selectedCardsCnt] = chosenCard4;
            selectedCardsCnt++;
        }

        return exchangedCards;
    }

    public boolean doesChallenge(Move move){
        return doesChallenge;
    }

    public boolean doesIntervene(Move move){
        return doesIntervene;
    }

    public boolean doesKillLeftCard(Move move){
        Boolean killLeft = getIsLeft();
        if(killLeft == null) {
            return GameLogicCenter.getInstance().getPlayer(0).getLeftCard().isAlive();
        }
        return killLeft;
    }

    public boolean doesShowLeftCardWhenChallenged(Move move){

        Boolean showLeft = getIsLeft();
        if(showLeft == null) {
            return GameLogicCenter.getInstance().getPlayer(0).getLeftCard().isAlive();
        }
        return showLeft;
    }



    @FXML
    Button reloadButton;

    @FXML
    void reloadButtonOnAction(ActionEvent actionEvent) {
        if(GameLogicCenter.getInstance().getLock()){
            massageLabel.setText("steal processing");
        }
        loadCoinsAndCardsAndTable();
    }
}
