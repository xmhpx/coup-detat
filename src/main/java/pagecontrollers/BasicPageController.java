package pagecontrollers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class BasicPageController {
    private static final Logger log = LogManager.getLogger(BasicPageController.class);

    @FXML
    protected AnchorPane anchorPane;


    public void initialize(){

    }

    protected void goToHomePage(){
        goToPage("/homePage.fxml");
    }

    protected void goToGamePage() {
        goToPage("/gamePage.fxml");
    }


    protected void goToPage(String str){
        loadPage(str);
    }

    protected void loadPage(String str){
        Parent root;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(str));
            root = loader.load();
        }
        catch (Exception exception){
            log.error("couldn't load fxml file '"+str+"'");
            throw new RuntimeException("couldn't load fxml file '"+str+"'");
        }

        Scene scene = new Scene(root);
        changeScene(scene);
    }

    protected void changeScene(Scene scene){
        Stage primaryStage = (Stage) anchorPane.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
