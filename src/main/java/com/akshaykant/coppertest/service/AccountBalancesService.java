package com.akshaykant.coppertest.service;

import com.akshaykant.coppertest.common.domain.AssetBalance;
import com.akshaykant.coppertest.common.resources.CurrencyCode;
import com.akshaykant.coppertest.common.resources.WebConstants;
import com.akshaykant.coppertest.service.exchange.DeribitExchangeClient;
import com.akshaykant.coppertest.service.model.AccountSummaryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class AccountBalancesService {

    private final AuthService authService;
    private final UserBalanceService userBalanceService;

    private String accessToken;

    public AccountBalancesService(AuthService authService, UserBalanceService userBalanceService) {
        this.authService = authService;
        this.userBalanceService = userBalanceService;
    }

    public List<AssetBalance> getUserBalances(String clientID, String clientSecret) throws IOException, ExecutionException, InterruptedException {

        List<AssetBalance> assetBalancesList = new ArrayList<>();

        // call the Auth service
        accessToken = authService.getAccessToken(clientID, clientSecret);

        log.info("AccountBalancesService -> AuthService : accessToken " + accessToken);

        //Call the Get Account Summary API and store the result in database
        if (accessToken != null){
            assetBalancesList = getUserAssetBalances(clientID, clientSecret);

            if (assetBalancesList.size() != 0) {
                // Call the Repository to add it to the database with clientID and list of their asset balances
                //userBalanceService.saveUserBalance(clientID, assetBalancesList);
            }
        }

        return assetBalancesList;

    }

    private List<AssetBalance> getUserAssetBalances(String clientID, String clientSecret) throws ExecutionException, InterruptedException {

        // Stores list of responses of multiple currencies for a user
        List<AccountSummaryResponse> accountBalancesResponsesList = new ArrayList<>();

        log.info("AccountBalancesService : Client ID " + clientID + " Client Secret " + clientSecret);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WebConstants.API_PATH_PREFIX)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DeribitExchangeClient deribitExchangeClient = retrofit.create(DeribitExchangeClient.class);

        //Loop over CurrencyCode to hit all three request as a async
        for (CurrencyCode asset : CurrencyCode.values()) {

            log.info("CurrencyCode "+ asset.toString());

            CompletableFuture<AccountSummaryResponse> response = deribitExchangeClient.getAccountSummary(
                    "Bearer " + accessToken,
                    asset.toString(),
                    false);


            AccountSummaryResponse accountSummaryResponse = response.get();

            accountBalancesResponsesList.add(accountSummaryResponse);
        }

        log.info("AccountBalancesService : Response List Size "+ accountBalancesResponsesList.size());


        if (accountBalancesResponsesList.size() != 0){
            return computeAssetBalanceList(accountBalancesResponsesList);
        }

        return null;
    }

    private List<AssetBalance> computeAssetBalanceList( List<AccountSummaryResponse> accountBalancesResponsesList){

        List<AssetBalance> assetBalancesList = new ArrayList<>();

        for (AccountSummaryResponse accountBalancesResponse : accountBalancesResponsesList) {

            var reservedBalance =  accountBalancesResponse.result.available_funds -  accountBalancesResponse.result.available_withdrawal_funds;

            log.info("AccountBalancesService - accountBalancesResponse : "+ accountBalancesResponse.result.deposit_address);

            assetBalancesList.add(new AssetBalance(
                    accountBalancesResponse.result.currency,
                    accountBalancesResponse.result.balance,
                    reservedBalance,
                    accountBalancesResponse.result.initial_margin,
                    accountBalancesResponse.result.margin_balance,
                    accountBalancesResponse.result.deposit_address));
        }

        return assetBalancesList;
    }
}
