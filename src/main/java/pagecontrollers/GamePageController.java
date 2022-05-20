package pagecontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import logic.GameLogicCenter;
import modules.ActionInfo;
import modules.ActionName;
import modules.Move;
import modules.Player;
import modules.cardtypes.Card;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.Objects;


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
    Label playerCoinsLabel;
    @FXML
    Label bot1CoinsLabel;
    @FXML
    Label bot2CoinsLabel;
    @FXML
    Label bot3CoinsLabel;


    @FXML
    Button incomeButton;
    @FXML
    Button foreignAidButton;
    @FXML
    Button coupButton;
    @FXML
    Button swapOneButton;

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
    Button interveneButton;
    @FXML
    Button challengeButton;


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
        tableView.getItems().clear();
        refresh();
    }


    public void pressButton(Button button){
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


    public Player getTargetedPlayer(){
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

        actionToBeConfirmedLabel.setText("");
        cardActionInfoLabel.setText("");
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
                    return;
                }
                cardActionInfoLabel.setText(ActionInfo.coupInfo()+"\ntarget:"+target);
                actionToBeConfirmedLabel.setText(ActionName.coup()+"\ntarget:"+target);
            }
            else if(selectedButton == swapOneButton){
                cardActionInfoLabel.setText(ActionInfo.swapOneInfo());
                actionToBeConfirmedLabel.setText(ActionName.swapOne());

            }
            else if(selectedButton == ambassadorExchangeButton){
                cardActionInfoLabel.setText(ActionInfo.ambassadorExchangeInfo());
                actionToBeConfirmedLabel.setText(ActionName.ambassadorExchange());

            }
            else if(selectedButton == stealButton){
                String target = getTarget();
                if(target == null){
                    massageLabel.setText("choose target to steal from");
                    return;
                }
                cardActionInfoLabel.setText(ActionInfo.stealInfo()+"\ntarget:"+target);
                actionToBeConfirmedLabel.setText(ActionName.steal()+"\ntarget:"+target);

            }
            else if(selectedButton == assassinateButton){
                String target = getTarget();
                if(target == null){
                    massageLabel.setText("choose target to assassinate");
                    return;
                }
                cardActionInfoLabel.setText(ActionInfo.assassinateInfo()+"\ntarget:"+target);
                actionToBeConfirmedLabel.setText(ActionName.assassinate()+"\ntarget:"+target);

            }
            else if(selectedButton == takeFromTreasuryButton){
                cardActionInfoLabel.setText(ActionInfo.takeFromTreasuryInfo());
                actionToBeConfirmedLabel.setText(ActionName.takeFromTreasury());

            }
            else if(selectedButton == interveneButton){
                cardActionInfoLabel.setText(ActionInfo.interveneInfo());
                actionToBeConfirmedLabel.setText(ActionName.intervene());

            }
            else if(selectedButton == challengeButton){
                cardActionInfoLabel.setText(ActionInfo.challengeInfo());
                actionToBeConfirmedLabel.setText(ActionName.challenge());

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
        massageLabel.setText("");
        loadCoinsAndCards();
    }



    public void loadCoinsAndCards(){
        loadCards();
        loadCoins();
    }

    public void loadCards(){
        GameLogicCenter backend = GameLogicCenter.getInstance();
        Player player = backend.getPlayer(0);
        Player bot1 = backend.getPlayer(1);
        Player bot2 = backend.getPlayer(2);
        Player bot3 = backend.getPlayer(3);

        BackgroundImage backgroundImage;

        BackgroundSize backgroundSize = new BackgroundSize(1, 1,
                true, true,
                false, false);
        backgroundImage = new BackgroundImage(player.getLeftCard().getImage(),
                null, null, null, backgroundSize);
        playerLeftCardButton.setBackground(new Background(backgroundImage));

        backgroundImage = new BackgroundImage(player.getRightCard().getImage(),
                null, null, null, backgroundSize);
        playerRightCardButton.setBackground(new Background(backgroundImage));


        if(!bot1.getLeftCard().isAlive()) {
            backgroundImage = new BackgroundImage(bot1.getLeftCard().getImage(),
                    null, null, null, backgroundSize);
            bot1LeftCardButton.setBackground(new Background(backgroundImage));
        }

        if(!bot1.getRightCard().isAlive()) {
            backgroundImage = new BackgroundImage(bot1.getRightCard().getImage(),
                    null, null, null, backgroundSize);
            bot1RightCardButton.setBackground(new Background(backgroundImage));
        }


        if(!bot2.getLeftCard().isAlive()) {
            backgroundImage = new BackgroundImage(bot2.getLeftCard().getImage(),
                    null, null, null, backgroundSize);
            bot2LeftCardButton.setBackground(new Background(backgroundImage));
        }

        if(!bot2.getRightCard().isAlive()) {
            backgroundImage = new BackgroundImage(bot2.getRightCard().getImage(),
                    null, null, null, backgroundSize);
            bot2RightCardButton.setBackground(new Background(backgroundImage));
        }


        if(!bot3.getLeftCard().isAlive()) {
            backgroundImage = new BackgroundImage(bot3.getLeftCard().getImage(),
                    null, null, null, backgroundSize);
            bot3LeftCardButton.setBackground(new Background(backgroundImage));
        }

        if(!bot3.getRightCard().isAlive()) {
            backgroundImage = new BackgroundImage(bot3.getRightCard().getImage(),
                    null, null, null, backgroundSize);
            bot3RightCardButton.setBackground(new Background(backgroundImage));
        }
    }

    public void loadCoins(){
        GameLogicCenter backend = GameLogicCenter.getInstance();
        Player player = backend.getPlayer(0);
        Player bot1 = backend.getPlayer(1);
        Player bot2 = backend.getPlayer(2);
        Player bot3 = backend.getPlayer(3);

        playerCoinsLabel.setText(""+player.getCoins());
        bot1CoinsLabel.setText(""+bot1.getCoins());
        bot2CoinsLabel.setText(""+bot2.getCoins());
        bot3CoinsLabel.setText(""+bot3.getCoins());
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
    void IncomeButtonOnAction(ActionEvent actionEvent) {
        pressButton(incomeButton);
    }

    @FXML
    void ForeignAidButtonOnAction(ActionEvent actionEvent) {
        pressButton(foreignAidButton);
    }

    @FXML
    void CoupButtonOnAction(ActionEvent actionEvent) {
        pressButton(coupButton);
    }

    @FXML
    void SwapOneButtonOnAction(ActionEvent actionEvent) {
        pressButton(swapOneButton);
    }


    @FXML
    void AmbassadorExchangeButtonOnAction(ActionEvent actionEvent) {
        pressButton(ambassadorExchangeButton);
    }

    @FXML
    void StealButtonOnAction(ActionEvent actionEvent) {
        pressButton(stealButton);
    }

    @FXML
    void AssassinateButtonOnAction(ActionEvent actionEvent) {
        pressButton(assassinateButton);
    }

    @FXML
    void TakeFromTreasuryButtonOnAction(ActionEvent actionEvent) {
        pressButton(takeFromTreasuryButton);
    }



    @FXML
    void InterveneButtonOnAction(ActionEvent actionEvent) {
        pressButton(interveneButton);
    }

    @FXML
    void ChallengeButtonOnAction(ActionEvent actionEvent) {
        pressButton(challengeButton);
    }


    @FXML
    void ConfirmButtonOnAction(ActionEvent actionEvent) {
        GameLogicCenter backend = GameLogicCenter.getInstance();
        Player player = backend.getPlayer(0);

        if(selectedButton == null){
            backend.play();
        }
        else{
            String result;
            if(selectedButton == incomeButton){
                result = backend.income(player);

            }
            else if(selectedButton == foreignAidButton){
                result = backend.foreignAid(player);

            }
            else if(selectedButton == coupButton){
                result = backend.coup(player, getTargetedPlayer(), false);

            }
            else if(selectedButton == swapOneButton){
                result = backend.swapOne(player, false);

            }
            else if(selectedButton == ambassadorExchangeButton){
                int[] cards = backend.getTwoDrawableRandomCardNumber();
                result = backend.ambassadorExchangeTwo(player, cards[0], cards[1]);

            }
            else if(selectedButton == stealButton){
                result = backend.steal(player, getTargetedPlayer());

            }
            else if(selectedButton == assassinateButton){
                result = backend.coup(player, getTargetedPlayer(), false);

            }
            else if(selectedButton == takeFromTreasuryButton){
                result = backend.takeFromTreasury(player);

            }
            else if(selectedButton == interveneButton){
                // TODO
                result = backend.takeFromTreasury(player);

            }
            else if(selectedButton == challengeButton){
                // TODO
                result = backend.takeFromTreasury(player);

            }
            else{
                massageLabel.setText("invalid action");
                return;
            }
            if(result.length() == 0) {
                backend.play();
            }
            else{
                massageLabel.setText(result);
                return;
            }
        }

        if(selectedButton != null) {
            pressButton(selectedButton);
        }
        refresh();
    }
}
