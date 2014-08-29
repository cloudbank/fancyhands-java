package com.fancyhands.sdk.standard;
import com.fancyhands.sdk.FancyHandsClient;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


/**
 * Hello world!
 *
 */
public class Standard extends FancyHandsClient  {

    public Standard(String api_key, String api_secret) {
        super(api_key, api_secret);
    }

    public void get(FancyRequestListener listener, String key) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("key", key));
        super.get("request/standard", params, listener);
    }

    public void get(FancyRequestListener listener) {
        super.get("request/standard", null, listener);
    }

    // Create a request, if you don't provide an expiration time, we'll set it to 24 hours from now.
    public void create(FancyRequestListener listener, String title, String description, double bid) {
        Date d = new Date(System.currentTimeMillis() + (86400 * 1000));
        create(listener, title, description, bid, d);
    }

    public void create(FancyRequestListener listener, String title, String description, double bid, Date expire) {
        // convert the time to our format and UTC.
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd'T'kk:mm:ss'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String ds = sdf.format(expire);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("title", title));
        params.add(new BasicNameValuePair("description", description));
        params.add(new BasicNameValuePair("bid", String.valueOf(bid)));
        params.add(new BasicNameValuePair("expiration_date", ds));
        super.post("request/standard", params, listener);
    }

}
