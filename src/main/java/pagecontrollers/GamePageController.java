package pagecontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    Label playerCoinsLabel;
    @FXML
    Label bot1CoinsLabel;
    @FXML
    Label bot2CoinsLabel;
    @FXML
    Label bot3CoinsLabel;


    @FXML
    Button IncomeButton;
    @FXML
    Button ForeignAidButton;
    @FXML
    Button CoupButton;
    @FXML
    Button SwapOneButton;

    @FXML
    Button ExchangeButton;
    @FXML
    Button StealButton;
    @FXML
    Button AssassinateButton;
    @FXML
    Button TakeFromTreasuryButton;

    @FXML
    TextField TargetTextField;


    @FXML
    Button InterveneButton;
    @FXML
    Button ChallengeButton;


    @FXML
    Button ConfirmButton;

    @FXML
    Label MassageLabel;


//    @FXML
//    TableView<Move> tableView;


    @FXML
    Label CardActionInfoLabel;
    @FXML
    Label ActionToBeConfirmedLabel;


    @Override
    public void initialize(){
        super.initialize();
    }



    @FXML
    void playerLeftCardButtonOnAction(ActionEvent actionEvent) {
    }

    @FXML
    void playerRightCardButtonOnAction(ActionEvent actionEvent) {
    }


    @FXML
    void bot1LeftCardButtonOnAction(ActionEvent actionEvent) {
    }

    @FXML
    void bot1RightCardButtonOnAction(ActionEvent actionEvent) {
    }


    @FXML
    void bot2LeftCardButtonOnAction(ActionEvent actionEvent) {
    }

    @FXML
    void bot2RightCardButtonOnAction(ActionEvent actionEvent) {
    }


    @FXML
    void bot3LeftCardButtonOnAction(ActionEvent actionEvent) {
    }

    @FXML
    void bot3RightCardButtonOnAction(ActionEvent actionEvent) {
    }



    @FXML
    void IncomeButtonOnAction(ActionEvent actionEvent) {
    }

    @FXML
    void ForeignAidButtonOnAction(ActionEvent actionEvent) {
    }

    @FXML
    void CoupButtonOnAction(ActionEvent actionEvent) {
    }

    @FXML
    void SwapOneButtonOnAction(ActionEvent actionEvent) {
    }


    @FXML
    void ExchangeButtonOnAction(ActionEvent actionEvent) {
    }

    @FXML
    void StealButtonOnAction(ActionEvent actionEvent) {
    }

    @FXML
    void AssassinateButtonOnAction(ActionEvent actionEvent) {
    }

    @FXML
    void TakeFromTreasuryButtonOnAction(ActionEvent actionEvent) {
    }



    @FXML
    void InterveneButtonOnAction(ActionEvent actionEvent) {
    }

    @FXML
    void ChallengeButtonOnAction(ActionEvent actionEvent) {
    }


    @FXML
    void ConfirmButtonOnAction(ActionEvent actionEvent) {
    }
}
