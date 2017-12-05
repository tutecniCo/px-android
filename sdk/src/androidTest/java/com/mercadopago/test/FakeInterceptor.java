package com.mercadopago.test;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by mreverter on 17/5/16.
 */
public class FakeInterceptor implements Interceptor {

    private final FakeAPI targetAPI;

    public FakeInterceptor(FakeAPI targetAPI) {
        this.targetAPI = targetAPI;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = null;
        if(BuildConfig.DEBUG) {
            FakeAPI.QueuedResponse nextResponse = targetAPI.getNextResponse();
            if(nextResponse.hasDelay()) {
                try {
                    Thread.sleep(nextResponse.getDelay());
                } catch (InterruptedException e) {
                    //Do nothing...
                }
            }
            String responseString = nextResponse.getBodyAsJson();
            response = new Response.Builder()
                    .code(nextResponse.getStatusCode())
                    .message(responseString)
                    .request(chain.request())
                    .protocol(Protocol.HTTP_1_0)
                    .body(ResponseBody.create(MediaType.parse("application/json"), responseString.getBytes()))
                    .addHeader("content-type", "application/json")
                    .build();
        }
        else {
            response = chain.proceed(chain.request());
        }

        return response;
    }
}
