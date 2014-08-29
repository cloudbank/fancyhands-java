package com.fancyhands;

import com.fancyhands.sdk.FancyHandsClient;
import com.fancyhands.sdk.echo.Echo;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class EchoTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public EchoTest( String testName ) {
        super( testName );
    }


    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( EchoTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testEchoGET() {
        Echo echo = new Echo("PuREN1kznQ4UyWI", "dzvNP3hg0idkb0x");
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("Hello", "World"));
        params.add(new BasicNameValuePair("Goodbuye", "World"));
        echo.setSync();
        echo.get(params, new FancyHandsClient.FancyRequestListener() {
            @Override
            public void onComplete(JSONObject result) {
                System.out.println("GETed");
                System.out.println(result.toString());
            }
        });
    }


    public void testEchoPOST() {
        Echo echo = new Echo("PuREN1kznQ4UyWI", "dzvNP3hg0idkb0x");
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("Hello", "World"));
        params.add(new BasicNameValuePair("Goodbuye", "World"));
        echo.setSync();
        echo.post(params, new FancyHandsClient.FancyRequestListener() {
            @Override
            public void onComplete(JSONObject result) {
                System.out.println("POSTed");
                System.out.println(result.toString());
            }
        });
    }

}
