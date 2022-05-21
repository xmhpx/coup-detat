package pagecontrollers;

import modules.UIPlayer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class PageControllerStorage {
    private static final Logger log = LogManager.getLogger(PageControllerStorage.class);

    private static PageControllerStorage INSTANCE;

    public static PageControllerStorage getInstance(){
        if(INSTANCE == null){
            INSTANCE = new PageControllerStorage();
        }
        return INSTANCE;
    }

    private GamePageController gamePageController = null;
    private HomePageController homePageController = null;

    private PageControllerStorage(){}


    // getters and setters

    public GamePageController getGamePageController() {
        return gamePageController;
    }

    public void setGamePageController(GamePageController gamePageController) {
        this.gamePageController = gamePageController;
    }


    public HomePageController getHomePageController() {
        return homePageController;
    }

    public void setHomePageController(HomePageController homePageController) {
        this.homePageController = homePageController;
    }
}
