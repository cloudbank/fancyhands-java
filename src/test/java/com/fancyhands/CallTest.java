package com.fancyhands;

import com.fancyhands.sdk.FancyHandsClient;
import com.fancyhands.sdk.call.Call;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Unit test for simple App.
 */
public class CallTest
    extends TestCase
{
    public static final String API_KEY = "PuREN1kznQ4UyWI";
    public static final String API_SECRET = "dzvNP3hg0idkb0x";

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public CallTest(String testName) {
        super( testName );
    }


    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite( CallTest.class );
    }

    public void testCustomGET() {
        Call call = new Call(CallTest.API_KEY, CallTest.API_SECRET);
        call.setSync();
        call.get(new FancyHandsClient.FancyRequestListener() {
            @Override
            public void onComplete(JSONObject result) {
                JSONArray requests = result.getJSONArray("calls");
                System.out.println("Got " + requests.length() + " call requests");
                for(int i = 0; i < requests.length(); ++i) {
                    JSONObject request = requests.getJSONObject(i);
                    System.out.println(request.get("key"));
                }
            }
        });
    }



    public void testCustomCreateAndGet() {
        System.out.println("Starting call test");
        Call call = new Call(CallTest.API_KEY, CallTest.API_SECRET);

        String phone = "815-455-3440";
        JSONObject conversation = new JSONObject(CallTest.CONVERSATION);

        call.setSync();
        call.create(new FancyHandsClient.FancyRequestListener() {
            @Override
            public void onComplete(JSONObject result) {
                System.out.println("Created call: " + result.getString("key"));
                CallTest.getCreatedRequest(result.getString("key"));

            }
        }, phone, conversation);
    }

    public static void getCreatedRequest(String key) {
        // ok, we got it
        Call s = new Call(CallTest.API_KEY, CallTest.API_SECRET);
        s.setSync();
        s.get(new FancyHandsClient.FancyRequestListener() {
            @Override
            public void onComplete(JSONObject result) {
                System.out.println("GOT THE NEWLY CREATED CALL REQUEST");
                System.out.println(result.toString());
            }
        }, key);
    }

    static public String CONVERSATION = "{\n" +
            "  \"id\": \"sample_conversation\",\n" +
            "  \"data\": null,\n" +
            "  \"name\": \"Sample Conversation\",\n" +
            "  \"version\": 1.1,\n" +
            "  \"scripts\": [\n" +
            "    {\n" +
            "      \"id\": \"start\",\n" +
            "      \"steps\": [\n" +
            "        {\n" +
            "          \"name\": \"time\",\n" +
            "          \"prompt\": \"What time is it?\",\n" +
            "          \"note\": \"The recording will tell you a time. What time is it?\",\n" +
            "          \"type\": \"text\",\n" +
            "          \"options\": []\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"thanks\",\n" +
            "          \"type\": \"logic_control\",\n" +
            "          \"note\": \"Nothing else to do, you can finish the call now.\",\n" +
            "          \"prompt\": \"Thanks!\",\n" +
            "          \"options\": []\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  ]\n" +
            "}";

}
