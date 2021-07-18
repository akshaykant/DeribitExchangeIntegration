package com.akshaykant.coppertest.service;

import com.akshaykant.coppertest.common.domain.TransferToExternalAccount;
import com.akshaykant.coppertest.common.domain.TransferToSubAccount;
import com.akshaykant.coppertest.common.resources.WebConstants;
import com.akshaykant.coppertest.service.exchange.DeribitExchangeClient;
import com.akshaykant.coppertest.service.model.TransferToSubAccountResponse;
import com.akshaykant.coppertest.service.model.WithdrawResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class TransferService {

    private final AuthService authService;

    private String accessToken;

    public TransferService(AuthService authService) {
        this.authService = authService;
    }

    public TransferToSubAccount initiateTransferToSubAccount(String clientID, String clientSecret, double amount, String currency, int subAccount) throws IOException, ExecutionException, InterruptedException {

        TransferToSubAccount transferResult = null;
        // call the Auth service
        accessToken = authService.getAccessToken(clientID, clientSecret);

        log.info("TransferService -> AuthService : accessToken " + accessToken);

        //Call the Get Transfer To SubAccount API and store the result in database
        if (accessToken != null) {
            transferResult = initiateSubAccountTransfer(clientID, clientSecret, amount, currency, subAccount);
        }

        return transferResult;

    }

    private TransferToSubAccount initiateSubAccountTransfer(String clientID, String clientSecret, double amount, String currency, int subAccount) throws ExecutionException, InterruptedException {

        log.info("TransferService : Client ID " + clientID + " Client Secret " + clientSecret);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WebConstants.API_PATH_PREFIX)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DeribitExchangeClient deribitExchangeClient = retrofit.create(DeribitExchangeClient.class);


        CompletableFuture<TransferToSubAccountResponse> response = deribitExchangeClient.initiateTransferToSubAccount(
                "Bearer " + accessToken,
                currency,
                amount,
                subAccount);


        TransferToSubAccountResponse transferToSubAccountResponse = response.get();


        log.info("TransferService : Transfer State " + transferToSubAccountResponse.result.state);

        return transferToSubAccountResponse.result;
    }

    public TransferToExternalAccount initiateTransferToExternalAccount(String clientID, String clientSecret, double amount, String currency, String externalAccount) throws IOException, ExecutionException, InterruptedException {

        TransferToExternalAccount transferResult = null;
        // call the Auth service
        accessToken = authService.getAccessToken(clientID, clientSecret);

        log.info("TransferService -> AuthService : accessToken " + accessToken);

        //Call the Get Withdraw API and store the result in database
        if (accessToken != null) {
            transferResult = initiateExternalAccountTransfer(clientID, clientSecret, amount, currency, externalAccount);
        }

        return transferResult;

    }

    private TransferToExternalAccount initiateExternalAccountTransfer(String clientID, String clientSecret, double amount, String currency, String externalAccount) throws ExecutionException, InterruptedException {

        log.info("TransferService : Client ID " + clientID + " Client Secret " + clientSecret);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WebConstants.API_PATH_PREFIX)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DeribitExchangeClient deribitExchangeClient = retrofit.create(DeribitExchangeClient.class);


        CompletableFuture<WithdrawResponse> response = deribitExchangeClient.initiateWithdrawal(
                "Bearer " + accessToken,
                currency,
                externalAccount,
                amount,
                "high",
                "");


        WithdrawResponse withdrawResponse = response.get();


        log.info("TransferService : Transfer State " + withdrawResponse.result.state);

        return withdrawResponse.result;
    }

}
