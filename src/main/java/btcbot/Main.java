package btcbot;

import db.ConnectionDB;
import com.suchomsky.PersistentPreferences;

import java.util.Scanner;
import java.util.Timer;

/**
 * Created by Dennis Suchomsky on 31.03.17.
 */
public class Main {
    private static PersistentPreferences preferences;
    public static ConnectionDB dbConn;

    public static void main(String[] args){
        preferences = new PersistentPreferences("btcbot");
        if (preferences.getParam("sql_db") == null){
            setConfig();
        }

        dbConn = new ConnectionDB(
                "jdbc:mysql://" + preferences.getParam("sql_host") + ":3306/" + preferences.getParam("sql_db") + "?useUnicode=true&characterEncoding=UTF-8",
                "com.mysql.jdbc.Driver",
                preferences.getParam("sql_user"),
                preferences.getParam("sql_password"));

        Timer timer = new Timer();
        timer.schedule( new KrakenRequest(
                preferences.getParam("kraken_api_key"),
                preferences.getParam("kraken_api_secret"),
                dbConn), 1000 , 60000);
    }

    private static void setConfig(){

        Scanner in = new Scanner(System.in);
        System.out.println("Kraken api key:" +  preferences.getParam("kraken_api_key"));
        preferences.setParam("kraken_api_key",in.next());

        System.out.println("Kraken api secret:" +  preferences.getParam("kraken_api_secret"));
        preferences.setParam("kraken_api_secret",in.next());

        System.out.println("SQL Host:" +  preferences.getParam("sql_host"));
        preferences.setParam("sql_host",in.next());

        System.out.println("SQL DB" +  preferences.getParam("sql_db"));
        preferences.setParam("sql_db",in.next());

        System.out.println("SQL User" +  preferences.getParam("sql_user"));
        preferences.setParam("sql_user",in.next());

        System.out.println("SQL Password" +  preferences.getParam("sql_password"));
        preferences.setParam("sql_password",in.next());
    }
}
