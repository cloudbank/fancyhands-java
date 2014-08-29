package com.fancyhands.sdk;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by troden on 8/29/14.
 */
public class FancyHttpURLConnectionAdapter extends  oauth.signpost.basic.HttpURLConnectionRequestAdapter {

    private String mPayload;

    public FancyHttpURLConnectionAdapter(HttpURLConnection connection)
    {
        super(connection);
    }

    public FancyHttpURLConnectionAdapter(HttpURLConnection connection, String payload) {
        super(connection);
        mPayload = payload;
    }

    @Override
    public void setRequestUrl(String url) {
        try {
            this.connection = (HttpURLConnection) (new URL(url).openConnection());
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public InputStream getMessagePayload() throws IOException {
        return new ByteArrayInputStream(mPayload.getBytes("UTF-8"));
    }

}


