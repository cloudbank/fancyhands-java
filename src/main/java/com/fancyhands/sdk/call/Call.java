package com.fancyhands.sdk.call;
import com.fancyhands.sdk.FancyHandsClient;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class Call extends FancyHandsClient  {
    static private String PIECE = "call";
    public Call(String api_key, String api_secret) {
        super(api_key, api_secret);
    }

    public void get(FancyRequestListener listener, String key) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("key", key));
        super.get(Call.PIECE, params, listener);
    }

    public void get(FancyRequestListener listener) {
        super.get(Call.PIECE, null, listener);
    }

    // Create a request, if you don't provide an expiration time, we'll set it to 24 hours from now.
    public void create(FancyRequestListener listener, String phone, JSONObject conversation) {
        create(listener, phone, conversation, null, false, 0, 0);
    }

    public void create(FancyRequestListener listener, String phone, JSONObject conversation, String title, boolean retry, int retry_delay, int retry_limit) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("phone", phone));
        params.add(new BasicNameValuePair("conversation", conversation.toString()));
        if(title != null && !"".equals(title)) {
            params.add(new BasicNameValuePair("title", title));
        }
        if(retry) {
            params.add(new BasicNameValuePair("retry", "1"));
            params.add(new BasicNameValuePair("retry_delay", String.valueOf(retry_delay)));
            params.add(new BasicNameValuePair("retry_limit", String.valueOf(retry_limit)));
        }
        super.post(Call.PIECE, params, listener);
    }

}
