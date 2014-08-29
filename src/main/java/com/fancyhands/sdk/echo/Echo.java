package com.fancyhands.sdk.echo;
import com.fancyhands.sdk.FancyHandsClient;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import java.util.List;

	
/**
 * Hello world!
 *
 */
public class Echo extends FancyHandsClient  {

    public Echo(String api_key, String api_secret) {
        super(api_key, api_secret);
    }

	public void get(List<NameValuePair> params, FancyRequestListener listener) {
        super.get("echo", params, listener);
    }

    public void post(List<NameValuePair> params, FancyRequestListener listener) {
        super.post("echo", params, listener);
    }
}
