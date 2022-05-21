package pagecontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import modules.Settings;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class HomePageController extends BasicPageController{
    private static final Logger log = LogManager.getLogger(HomePageController.class);

    public static final String fxmlFileName = "homePage.fxml";

    @FXML
    Button startButton;

    @FXML
    TextField playerTextField;
    @FXML
    TextField playerLeftTextField;
    @FXML
    TextField playerRightTextField;
    @FXML
    TextField bot1TextField;
    @FXML
    TextField bot1LeftTextField;
    @FXML
    TextField bot1RightTextField;
    @FXML
    TextField bot2TextField;
    @FXML
    TextField bot2LeftTextField;
    @FXML
    TextField bot2RightTextField;
    @FXML
    TextField bot3TextField;
    @FXML
    TextField bot3LeftTextField;
    @FXML
    TextField bot3RightTextField;

    @FXML
    TextField startingCoinsTextField;

    @FXML
    Button saveToJsonButton;
    @FXML
    Button loadDefaultButton;
    @FXML
    Button loadFromJsonButton;


    protected Settings settings;


    @Override
    public void initialize(){
        super.initialize();

        PageControllerStorage.getInstance().setHomePageController(this);

        loadFromSettings(settings = Settings.read());
    }


    public void loadFromSettings(Settings settings){
        int[] initialCardNumbers = settings.getInitialCardNumbers();
        String[] bots = settings.getBots();
        int startingCoins = settings.getStartingCoins();

        bot1TextField.setText(bots[0]);
        bot2TextField.setText(bots[1]);
        bot3TextField.setText(bots[2]);

        playerLeftTextField.setText(""+initialCardNumbers[0]);
        playerRightTextField.setText(""+initialCardNumbers[1]);
        bot1LeftTextField.setText(""+initialCardNumbers[2]);
        bot1RightTextField.setText(""+initialCardNumbers[3]);
        bot2LeftTextField.setText(""+initialCardNumbers[4]);
        bot2RightTextField.setText(""+initialCardNumbers[5]);
        bot3LeftTextField.setText(""+initialCardNumbers[6]);
        bot3RightTextField.setText(""+initialCardNumbers[7]);

        startingCoinsTextField.setText(""+startingCoins);
    }

    public void loadDefault(){
        loadFromSettings(settings = new Settings());
    }



    public void storeInSettings(Settings settings){

        int[] initialCardNumbers = settings.getInitialCardNumbers();
        String[] bots = settings.getBots();
        int startingCoins = settings.getStartingCoins();

        bots[0] = bot1TextField.getText();
        bots[1] = bot2TextField.getText();
        bots[2] = bot3TextField.getText();

        try {
            initialCardNumbers[0] = Integer.parseInt(playerLeftTextField.getText());
        }
        catch (NumberFormatException ignored){}
        try {
            initialCardNumbers[1] = Integer.parseInt(playerRightTextField.getText());
        }
        catch (NumberFormatException ignored){}


        try {
            initialCardNumbers[2] = Integer.parseInt(bot1LeftTextField.getText());
        }
        catch (NumberFormatException ignored){}
        try {
            initialCardNumbers[3] = Integer.parseInt(bot1RightTextField.getText());
        }
        catch (NumberFormatException ignored){}


        try {
            initialCardNumbers[4] = Integer.parseInt(bot2LeftTextField.getText());
        }
        catch (NumberFormatException ignored){}
        try {
            initialCardNumbers[5] = Integer.parseInt(bot2RightTextField.getText());
        }
        catch (NumberFormatException ignored){}


        try {
            initialCardNumbers[6] = Integer.parseInt(bot3LeftTextField.getText());
        }
        catch (NumberFormatException ignored){}
        try {
            initialCardNumbers[7] = Integer.parseInt(bot3RightTextField.getText());
        }
        catch (NumberFormatException ignored){}

        try {
            startingCoins = Integer.parseInt(startingCoinsTextField.getText());
        }
        catch (NumberFormatException ignored){}

        settings.setBots(bots);
        settings.setInitialCardNumbers(initialCardNumbers);
        settings.setStartingCoins(startingCoins);
    }



    @FXML
    void startButtonOnAction(ActionEvent actionEvent){
        storeInSettings(settings);
        settings.write();
        goToGamePage();
    }


    @FXML
    void saveToJsonButtonOnAction(ActionEvent actionEvent){
        storeInSettings(settings);
        settings.write();
    }


    @FXML
    void loadDefaultButtonOnAction(ActionEvent actionEvent){
        loadDefault();
    }


    @FXML
    void loadFromJsonButtonOnAction(ActionEvent actionEvent){
        loadFromSettings(Settings.read());
    }



    // getters and setters

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }
}
