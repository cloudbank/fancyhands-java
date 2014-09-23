Fancy Hands Java SDK
--------------------

This is our first pass at a Java library / SDK.

We'd gladly accept any pull requests or advice.

This includes code for all of the Fancy Hands endpoints including:
 - fancyhands.call.Call
 - fancyhands.echo.Echo
 - fancyhands.echo.Echo 
 - fancyhands.standard.Standard 
 - fancyhands.standard.Message
 - fancyhands.custom.Custom

# Testing 

It should have 100% test coverage.

 - Update the test code with your API credentials
 - Run `mvn test`
 
# Android

This could should work on Android, *but doesn't require any android libraries*. So you can build any Java app you want.

# Building it

- `mvn compile`

# Using it

- `mvn package`
- `cp target/fancyhands-java-sdk-1.0.jar $YOURPROJECT/libs`

Write your code:

```
        Standard standard = new Standard(API_KEY, API_SECRET);
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
```



