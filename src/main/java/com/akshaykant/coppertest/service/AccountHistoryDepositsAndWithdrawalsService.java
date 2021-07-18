package com.akshaykant.coppertest.service;

import com.akshaykant.coppertest.common.domain.AssetDeposit;
import com.akshaykant.coppertest.common.domain.AssetWithdrawals;
import com.akshaykant.coppertest.common.resources.CurrencyCode;
import com.akshaykant.coppertest.common.resources.WebConstants;
import com.akshaykant.coppertest.service.exchange.DeribitExchangeClient;
import com.akshaykant.coppertest.service.model.DepositResponse;
import com.akshaykant.coppertest.service.model.DepositResponseCurrency;
import com.akshaykant.coppertest.service.model.WithdrawalResponseCurrency;
import com.akshaykant.coppertest.service.model.WithdrawalsResponse;
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
public class AccountHistoryDepositsAndWithdrawalsService {

    private final AuthService authService;

    private String accessToken;

    public AccountHistoryDepositsAndWithdrawalsService(AuthService authService) {
        this.authService = authService;
    }

    public List<AssetDeposit> getUserDepositHistory(String clientID, String clientSecret, int count, int offset) throws IOException, ExecutionException, InterruptedException {

        List<AssetDeposit> assetDepositList = new ArrayList<>();

        // call the Auth service
        accessToken = authService.getAccessToken(clientID, clientSecret);

        log.info("AccountHistoryDepositsAndWithdrawals -> AuthService : accessToken " + accessToken);

        //Call the Get Deposit API for all the assets
        if (accessToken != null){
            assetDepositList = getUserDepositAssetHistory(count, offset);
        }

        return assetDepositList;
    }

    private List<AssetDeposit> getUserDepositAssetHistory(int count, int offset) throws ExecutionException, InterruptedException {


        // Stores list of responses of multiple currencies for a user
        List<DepositResponseCurrency> depositResponsesList = new ArrayList<>();

        log.info("AccountHistoryDepositsAndWithdrawals : Count " + count + " Offset " + offset);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WebConstants.API_PATH_PREFIX)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DeribitExchangeClient deribitExchangeClient = retrofit.create(DeribitExchangeClient.class);

        //Loop over CurrencyCode to hit all three request as a async
        for (CurrencyCode asset : CurrencyCode.values()) {

            log.info("CurrencyCode "+ asset.toString());

            CompletableFuture<DepositResponse> response = deribitExchangeClient.getAccountDeposits(
                    "Bearer " + accessToken,
                    count,
                    asset.toString(),
                    offset);


            DepositResponse depositResponse = response.get();


            depositResponsesList.add(new DepositResponseCurrency(depositResponse, asset.toString()));
        }

        log.info("AccountHistoryDepositsAndWithdrawals : Response List Size "+ depositResponsesList.size());

        if (depositResponsesList.size() != 0){
            return computeAssetDepositList(depositResponsesList);
        }

        return null;
    }

    private List<AssetDeposit> computeAssetDepositList( List<DepositResponseCurrency> depositResponsesList){

        List<AssetDeposit> assetDepositList = new ArrayList<>();

        for (DepositResponseCurrency depositResponse : depositResponsesList) {

            log.info("AccountHistoryDepositsAndWithdrawalsService - depositResponse :  currency"+ depositResponse.getCurrency() + " size " + depositResponse.getDepositResponse().result.count);

            assetDepositList.add(new AssetDeposit(
                    depositResponse.getDepositResponse().result.count,
                    depositResponse.getCurrency(),
                    depositResponse.getDepositResponse().result.data));
        }

        return assetDepositList;
    }

    public List<AssetWithdrawals> getUserWithdrawalHistory(String clientID, String clientSecret, int count, int offset) throws IOException, ExecutionException, InterruptedException {

        List<AssetWithdrawals> assetWithdrawalList = new ArrayList<>();

        // call the Auth service
        accessToken = authService.getAccessToken(clientID, clientSecret);

        log.info("AccountHistoryDepositsAndWithdrawals -> AuthService : accessToken " + accessToken);

        //Call the Get Withdrawal API for all the assets
        if (accessToken != null){
            assetWithdrawalList = getUserWithdrawalAssetHistory(count, offset);
        }

        return assetWithdrawalList;
    }

    private List<AssetWithdrawals> getUserWithdrawalAssetHistory(int count, int offset) throws ExecutionException, InterruptedException {


        // Stores list of responses of multiple currencies for a user
        List<WithdrawalResponseCurrency> withdrawalResponsesList = new ArrayList<>();

        log.info("AccountHistoryDepositsAndWithdrawals : Count " + count + " Offset " + offset);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WebConstants.API_PATH_PREFIX)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DeribitExchangeClient deribitExchangeClient = retrofit.create(DeribitExchangeClient.class);

        //Loop over CurrencyCode to hit all three request as a async
        for (CurrencyCode asset : CurrencyCode.values()) {

            log.info("CurrencyCode "+ asset.toString());

            CompletableFuture<WithdrawalsResponse> response = deribitExchangeClient.getAccountWithdrawals(
                    "Bearer " + accessToken,
                    count,
                    asset.toString(),
                    offset);


            WithdrawalsResponse withdrawalsResponse = response.get();


            withdrawalResponsesList.add(new WithdrawalResponseCurrency(withdrawalsResponse, asset.toString()));
        }

        log.info("AccountHistoryDepositsAndWithdrawals : Response List Size "+ withdrawalResponsesList.size());

        if (withdrawalResponsesList.size() != 0){
            return computeAssetWithdrawalList(withdrawalResponsesList);
        }

        return null;
    }

    private List<AssetWithdrawals> computeAssetWithdrawalList(List<WithdrawalResponseCurrency> withdrawalResponsesList){

        List<AssetWithdrawals> assetWithdrawalsCountsList = new ArrayList<>();

        for (WithdrawalResponseCurrency withdrawalResponse : withdrawalResponsesList) {

            log.info("AccountHistoryDepositsAndWithdrawalsService - withdrawalResponse :  currency"+ withdrawalResponse.getCurrency() + " size " + withdrawalResponse.getWithdrawalsResponse().result.count);

            assetWithdrawalsCountsList.add(new AssetWithdrawals(
                    withdrawalResponse.getWithdrawalsResponse().result.count,
                    withdrawalResponse.getCurrency(),
                    withdrawalResponse.getWithdrawalsResponse().result.data));
        }

        return assetWithdrawalsCountsList;
    }

}
