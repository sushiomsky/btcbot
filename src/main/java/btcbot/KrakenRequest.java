package btcbot;

import db.ConnectionDB;
import edu.self.kraken.api.KrakenApi;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

/**
 * Created by Dennis Suchomsky on 31.03.17.
 */
public class KrakenRequest extends TimerTask {

    private KrakenApi api;
    private String response;
    private Map<String, String> input = new HashMap<>();
    private ConnectionDB dbConn;

    KrakenRequest(String key, String secret, ConnectionDB dbConn){
        api = new KrakenApi();
        api.setKey(key);
        api.setSecret(secret);
        input.put("pair", "XBTEUR");
        this.dbConn = dbConn;
    }

    /**
     * The action to be performed by this timer task.
     */
    @Override
    public void run() {
        try {
                response = api.queryPublic(KrakenApi.Method.TICKER, input);

                JSONObject ticker = new JSONObject(response);
                ticker = ticker.getJSONObject("result");

                JSONArray ask = ticker.getJSONObject("XXBTZEUR").getJSONArray("a");
                double ask_btc = ask.getDouble(0);
                JSONArray bid = ticker.getJSONObject("XXBTZEUR").getJSONArray("b");
                double bid_btc = ask.getDouble(0);
                System.out.println(ask.getDouble(0));
           // dbConn.runSqlQuery("INSERT INTO XBTEUR (ask,bid) VALUES(" + ask.getDouble(0) + "," + bid.getDouble(0) + ")");
            dbConn.executeQuery("INSERT INTO XBTEUR (ask,bid) VALUES(" + ask.getDouble(0) + "," + bid.getDouble(0) + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
