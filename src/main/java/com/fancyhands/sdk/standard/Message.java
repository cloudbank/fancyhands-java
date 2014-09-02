package com.fancyhands.sdk.standard;
import com.fancyhands.sdk.FancyHandsClient;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class Message extends FancyHandsClient  {

    private static String PIECE = "request/standard/messages";

    public Message(String api_key, String api_secret) {
        super(api_key, api_secret);
    }

    public void create(FancyRequestListener listener, String key, String message) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("key", key));
        params.add(new BasicNameValuePair("message", message));
        super.post(Message.PIECE, params, listener);
    }

}
