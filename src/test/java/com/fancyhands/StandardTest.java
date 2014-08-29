package com.fancyhands;

import com.fancyhands.sdk.FancyHandsClient;
import com.fancyhands.sdk.echo.Echo;
import com.fancyhands.sdk.standard.Standard;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class StandardTest
    extends TestCase
{
    public static final String API_KEY = "PuREN1kznQ4UyWI";
    public static final String API_SECRET = "dzvNP3hg0idkb0x";

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public StandardTest(String testName) {
        super( testName );
    }


    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite( StandardTest.class );
    }

    public void testStandardGET() {
        Standard standard = new Standard(StandardTest.API_KEY, StandardTest.API_SECRET);
        standard.setSync();
        standard.get(new FancyHandsClient.FancyRequestListener() {
            @Override
            public void onComplete(JSONObject result) {
                JSONArray requests = result.getJSONArray("requests");
                System.out.println("Got " + requests.length() + " standard requests");
                for(int i = 0; i < requests.length(); ++i) {
                    JSONObject request = requests.getJSONObject(i);
                    System.out.println(request.get("title") + " - " + request.get("key"));
                }
            }
        });
    }



    public void testStandardCreateAndGet() {
        System.out.println("Starting standard test");
        Standard standard = new Standard(StandardTest.API_KEY, StandardTest.API_SECRET);
        standard.setSync();
        standard.create(new FancyHandsClient.FancyRequestListener() {
            @Override
            public void onComplete(JSONObject result) {

                System.out.println("Created: " + result.getString("key"));
                StandardTest.getCreatedRequest(result.getString("key"));

            }
        }, "JAVA TEST", "JAVA TEST BODY", 1.0);
    }

    public static void getCreatedRequest(String key) {
        // ok, we got it
        Standard s = new Standard(StandardTest.API_KEY, StandardTest.API_SECRET);
        s.setSync();
        s.get(new FancyHandsClient.FancyRequestListener() {
            @Override
            public void onComplete(JSONObject result) {
                System.out.println("GOT THE NEWLY CREATED REQUEST");
                System.out.println(result.toString());
            }
        }, key);
    }

}
