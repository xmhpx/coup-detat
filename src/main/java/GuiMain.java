import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/* TODO:
    add bots
    add settings json
    add logs
    add home page
    if assassination gets intervened successfully, money won't come back
    if someone looses a challenge, they will refund the money they spent for the challenged action


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
