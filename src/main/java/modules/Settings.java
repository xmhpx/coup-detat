package modules;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.*;

public class Settings {
    private static final Logger log = LogManager.getLogger(Settings.class);

    protected int[] initialCardNumbers = {
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
    };

    protected String[] bots = {
            "Killer",
            "Paranoid",
            "CheaterCoupper",
    };

    protected int startingCoins = 2;


    public static Settings read(){
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        Settings settings = new Settings();


        try {
            BufferedReader settingsReader = new BufferedReader(
                    new FileReader("settings.json"));
            settings = gson.fromJson(settingsReader, Settings.class);
        }
        catch (FileNotFoundException ignored){
            try {
                String settingsJson = gson.toJson(new Settings());
                FileWriter settingsWriter = new FileWriter("settings.json");
                settingsWriter.write(settingsJson);
                settingsWriter.close();
            } catch (IOException e) {
                log.error("unable to work with settings.json");
                e.printStackTrace();
            }
        }

        if(settings != null){
            int[] initialCardNumbers = settings.getInitialCardNumbers();
            String[] bots = settings.getBots();
            int startingCoins = settings.getStartingCoins();

            if(startingCoins < 0 || startingCoins > 20){
                log.warn("starting coins should be in range 0 to 20 (inclusive)");
                startingCoins = 2;
            }
            if(initialCardNumbers.length != 8){
                initialCardNumbers = settings.getInitialCardNumbers();
            }
            if(bots.length != 3){
                bots = settings.getBots();
            }
            if(bots[0] == null || bots[1] == null || bots[2] == null){
                bots = settings.getBots();
            }

            settings.setBots(bots);
            settings.setInitialCardNumbers(initialCardNumbers);
            settings.setStartingCoins(startingCoins);
        }
        return settings;
    }

    public void write(){
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        try {
            String settingsJson = gson.toJson(this);
            FileWriter settingsWriter = new FileWriter("settings.json");
            settingsWriter.write(settingsJson);
            settingsWriter.close();
        } catch (IOException e) {
            log.error("unable to work with settings.json");
            e.printStackTrace();
        }
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
