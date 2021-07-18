package com.akshaykant.coppertest.service;
import com.akshaykant.coppertest.common.resources.WebConstants;
import com.akshaykant.coppertest.service.exchange.DeribitExchangeClient;
import com.akshaykant.coppertest.service.model.AuthResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class AuthService {

    public String getAccessToken(String clientID, String clientSecret) throws IOException, ExecutionException, InterruptedException {

        log.info("AuthService : Client ID " + clientID + " Client Secret " + clientSecret);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WebConstants.API_PATH_PREFIX)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DeribitExchangeClient deribitExchangeClient = retrofit.create(DeribitExchangeClient.class);

        log.info("deribitExchangeClient " + deribitExchangeClient);

        CompletableFuture<AuthResponse> response = deribitExchangeClient.getAuthKey(
                clientID,
                clientSecret,
                "client_credentials");


        if(response == null){
            log.error("Issue with the Auth API Call : /public/auth");
            throw new IOException("Issue with the Auth API Call");
        }

        AuthResponse authResponse = response.get();

        if (authResponse.result.access_token == null){
            log.error("access_token not received : /public/auth");
            throw new IOException("access_token not received");
        }

        return authResponse.result.access_token;
    }
}
