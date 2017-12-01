package edu.self.kraken;

import edu.self.kraken.api.KrakenApi;
import edu.self.kraken.api.KrakenApi.Method;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class Examples {


    public static void main(String[] args) throws IOException, InvalidKeyException, NoSuchAlgorithmException {

        KrakenApi api = new KrakenApi();
        api.setKey("2eK6ciCd6NYGcPXMpbBA6wOBZP9weM3Yo+C/V6pKOd8UwolSe6m3skm7"); // FIXME
        api.setSecret("ZS8CP66igEiNIgiD1Exue8XHZK1t8A+p1ukuj1R/C2sVHLRMdGRzaTsONYT1/5lbzfp4/eDc161ElKKm8pmEpw=="); // FIXME

        String response;
        Map<String, String> input = new HashMap<>();

        input.put("pair", "XBTEUR");
        response = api.queryPublic(Method.TICKER, input);
        // System.out.println(response);

        JSONObject ticker = new JSONObject(response);
        ticker = ticker.getJSONObject("result");

        JSONArray ask = ticker.getJSONObject("XXBTZEUR").getJSONArray("a");
        System.out.println(ask.getDouble(0));
    }
}
