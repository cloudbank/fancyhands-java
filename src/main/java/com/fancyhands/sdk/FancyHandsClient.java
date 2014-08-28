package com.fancyhands.sdk;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.NameValuePair;



import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

import org.json.JSONObject;


/**
 * Created by troden on 8/21/14.
 */
public class FancyHandsClient    {
    private String API_BASE = "http://192.168.1.10:8080/api/v1/";

    protected String API_KEY = null;
    protected String API_SECRET = null;
    private OAuthConsumer consumer = null;

    private boolean sync = false;

    public void setSync() {
        this.sync = true;
    }

    public interface FancyRequestListener {
        public void onComplete(JSONObject result);
    }


    public FancyHandsClient(String api_key, String api_secret) {
        this.API_KEY = api_key;
        this.API_SECRET = api_secret;
        consumer = new CommonsHttpOAuthConsumer(API_KEY, API_SECRET);
        System.out.println( "Initialized Fancy Hands client!" );
    }

    public FancyHandsClient() {
        System.err.println("Oops. you're going to need to provide api key/secret");
    }

    private String getUrl(String piece) {
        return this.API_BASE + piece;
    }


    protected void post(String piece, List<NameValuePair> params, FancyRequestListener l) {
        String url = this.getUrl(piece);
        RequestHandler handles = new RequestHandler("POST", url, params);
        handles.setKeyAndSecret(API_KEY, API_SECRET);
        handles.setListener(l);
        handles.start();
        if(this.sync) {
            try {
                synchronized (handles) {
                    handles.wait();
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected void get(String piece, List<NameValuePair> params, FancyRequestListener l) {
        String url = this.getUrl(piece) + "?" + URLEncodedUtils.format(params, "utf-8");
        RequestHandler handles = new RequestHandler("GET", url, null);
        handles.setKeyAndSecret(API_KEY, API_SECRET);
        handles.setListener(l);
        handles.start();
        if(this.sync) {
            try {
                synchronized (handles) {
                    handles.wait();
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
/*
    protected void post(String piece, List<NameValuePair> params, FancyRequestListener l) {
        String url = this.getUrl(piece); //  + "?" + URLEncodedUtils.format(params, "utf-8");
        GetHandler handles = new GetHandler(l, url, API_KEY, API_SECRET);
        handles.start();
        if (this.sync) {
            try {
                synchronized (handles) {
                    handles.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    */
/*

    private static class GetHandler extends Thread  {
        String url;
        String key;
        String secret;
        FancyRequestListener listener;
        GetHandler(FancyRequestListener l, String _url, String _key, String _secret) {
            url = _url;
            key = _key;
            secret = _secret;
            listener = l;
        }


        public void run() {
            try {
                OAuthConsumer consumer = new DefaultOAuthConsumer(this.key, this.secret);
                URL url = new URL(this.url);
                HttpURLConnection request = (HttpURLConnection) url.openConnection();
                request.setDoOutput(false);
                request.setDoInput(true);

                // sign the request
                consumer.sign(request);
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
                    String line;
                    StringBuilder sb = new StringBuilder();
                    while ((line = in.readLine()) != null) {
                        sb.append(line);
                    }
                    listener.onComplete(new JSONObject(sb.toString()));
                } catch (Exception e) {
                    // FIXME: return an error
                    e.printStackTrace();
                }

            } catch (Exception e) {
                // FIXME: return an error
                e.printStackTrace();
            }
        }
    }

    */
    private static class RequestHandler extends Thread  {
        private String url;
        private String key = null;
        private String secret = null;
        private List<NameValuePair> params;
        private FancyRequestListener listener;
        private String method;
        RequestHandler(String _method, String _url, List<NameValuePair> _params) {
            method = _method;
            url = _url;
            params = _params;
        }

        public void setListener(FancyRequestListener l) {
            listener = l;
        }

        public void setKeyAndSecret(String k, String s) {
            key = k;
            secret = s;
        }

        public void run() {
            assert key != null;
            assert secret != null;
            try {
                OAuthConsumer consumer = new DefaultOAuthConsumer(this.key, this.secret);
                URL url = new URL(this.url);
                HttpURLConnection request = (HttpURLConnection) url.openConnection();

                request.setRequestMethod(this.method);
                request.setDoInput(true);
                // sign the request
                consumer.sign(request);

                try {
                    if("POST".equals(method)) {
                        request.setDoOutput(true);
                        OutputStream os = request.getOutputStream();
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "utf-8"));
                        System.out.println(params);
                        writer.write(URLEncodedUtils.format(params, "utf-8"));
                        writer.flush();
                        writer.close();
                        os.close();
                    }



                    BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
                    String line;
                    StringBuilder sb = new StringBuilder();
                    while ((line = in.readLine()) != null) {
                        sb.append(line);
                    }
                    listener.onComplete(new JSONObject(sb.toString()));
                } catch (Exception e) {
                    // FIXME: return an error
                    e.printStackTrace();
                }

            } catch (Exception e) {
                // FIXME: return an error
                e.printStackTrace();
            }
        }
    }



}
