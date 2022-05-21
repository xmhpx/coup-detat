package modules;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Settings {
    private static final Logger log = LogManager.getLogger(Settings.class);

    protected int[] initialCardNumbers = new int[8];
    protected String[] bots = new String[3];
    protected int startingCoins = 2;


    public Settings(){
        for(int i = 0; i < 8; i++)initialCardNumbers[i] = -1;
        for(int i = 0; i < 3; i++)bots[i] = "";
    }


    // getters and setters

    public int[] getInitialCardNumbers() {
        return initialCardNumbers;
    }

    public void setInitialCardNumbers(int[] initialCardNumbers) {
        this.initialCardNumbers = initialCardNumbers;
    }


    public String[] getBots() {
        return bots;
    }

    public void setBots(String[] bots) {
        this.bots = bots;
    }


    public int getStartingCoins() {
        return startingCoins;
    }

    public void setStartingCoins(int startingCoins) {
        this.startingCoins = startingCoins;
    }
}
