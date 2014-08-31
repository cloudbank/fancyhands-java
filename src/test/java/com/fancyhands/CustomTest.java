package com.fancyhands;

import com.fancyhands.sdk.FancyHandsClient;
import com.fancyhands.sdk.custom.Custom;

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
public class CustomTest
    extends TestCase
{
    public static final String API_KEY = "PuREN1kznQ4UyWI";
    public static final String API_SECRET = "dzvNP3hg0idkb0x";

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public CustomTest(String testName) {
        super( testName );
    }


    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite( CustomTest.class );
    }

    public void testCustomGET() {
        Custom standard = new Custom(CustomTest.API_KEY, CustomTest.API_SECRET);
        standard.setSync();
        standard.get(new FancyHandsClient.FancyRequestListener() {
            @Override
            public void onComplete(JSONObject result) {
                JSONArray requests = result.getJSONArray("requests");
                System.out.println("Got " + requests.length() + " custom requests");
                for(int i = 0; i < requests.length(); ++i) {
                    JSONObject request = requests.getJSONObject(i);
                    System.out.println(request.get("title") + " - " + request.get("key"));
                }
            }
        });
    }



    public void testCustomCreateAndGet() {
        System.out.println("Starting custom test");
        Custom custom = new Custom(CustomTest.API_KEY, CustomTest.API_SECRET);

        JSONArray fields = new JSONArray();
        JSONObject field = new JSONObject();
        field.put("type", "text");
        field.put("label", "First Name");
        field.put("description", "What is your first name?");
        field.put("field_name", "name");
        field.put("required", true);
        field.put("order", 1);

        fields.put(field);


        custom.setSync();
        custom.create(new FancyHandsClient.FancyRequestListener() {
            @Override
            public void onComplete(JSONObject result) {

                System.out.println("Created: " + result.getString("key"));
                CustomTest.getCreatedRequest(result.getString("key"));

            }
        }, "JAVA custom TEST", fields, 1.0);
    }

    public static void getCreatedRequest(String key) {
        // ok, we got it
        Custom s = new Custom(CustomTest.API_KEY, CustomTest.API_SECRET);
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
