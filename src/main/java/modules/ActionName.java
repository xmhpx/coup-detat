package modules;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ActionName {
    private static final Logger log = LogManager.getLogger(ActionName.class);

    public static String income(){
        return "Income";
    }

    public static String foreignAid(){
        return "Foreign Aid";
    }

    public static String coup(){
        return "Coup";
    }

    public static String swapOne(){
        return "Swap One";
    }

    public static String ambassadorExchange(){
        return "Ambassador Exchange";
    }

    public static String steal(){
        return "Steal";
    }

    public static String assassinate(){
        return "Assassinate";
    }

    public static String takeFromTreasury(){
        return "Take From Treasury";
    }

    public static String intervene(){
        return "Intervene";
    }

    public static String challenge(){
        return "Challenge";
    }
}
