import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/* TODO:
    add bots
    add fx ids and button actions
    add settings json
    add home page
    finish game page
 */


public class GuiMain extends Application{
    private static final Logger log = LogManager.getLogger(GuiMain.class);

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        log.info("application started");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gamePage.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop(){}
}
