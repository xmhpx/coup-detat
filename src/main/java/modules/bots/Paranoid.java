package modules.bots;

import modules.DefaultPlayer;
import modules.DoerType;
import modules.Move;
import modules.cardtypes.Card;
import modules.cardtypes.Duke;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Paranoid extends DefaultPlayer {
    private static final Logger log = LogManager.getLogger(Paranoid.class);

    public Paranoid(String name, Card leftCard, Card rightCard, int coins, DoerType type) {
        super(name, leftCard, rightCard, coins, type);
    }

    private int cnt = 0;

    @Override
    public boolean doesChallenge(Move move){
        return (cnt++%2) == 0;
    }
}
