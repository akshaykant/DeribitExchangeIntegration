package com.akshaykant.coppertest.service.exchange;

import com.akshaykant.coppertest.service.model.*;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

import java.util.concurrent.CompletableFuture;

public interface DeribitExchangeClient {
    @GET("public/auth")
    @Headers("accept: application/json")
    CompletableFuture<AuthResponse> getAuthKey(@Query("client_id") String clientId,
                                               @Query("client_secret") String clientSecret,
                                               @Query("grant_type") String grantType);

    @GET("private/get_account_summary")
    @Headers("accept: application/json")
    CompletableFuture<AccountSummaryResponse> getAccountSummary(@Header("Authorization") String header,
                                                                @Query("currency") String currency,
                                                                @Query("extended") Boolean extended);

    @GET("private/get_deposits")
    @Headers("accept: application/json")
    CompletableFuture<DepositResponse> getAccountDeposits(@Header("Authorization") String header,
                                                          @Query("count") int count,
                                                          @Query("currency") String currency,
                                                          @Query("offset") int offset);

    @GET("private/get_withdrawals")
    @Headers("accept: application/json")
    CompletableFuture<WithdrawalsResponse> getAccountWithdrawals(@Header("Authorization") String header,
                                                                 @Query("count") int count,
                                                                 @Query("currency") String currency,
                                                                 @Query("offset") int offset);


    @GET("private/withdrawal")
    @Headers("accept: application/json")
    CompletableFuture<WithdrawResponse> initiateWithdrawal(@Header("Authorization") String header,
                                                           @Query("currency") String currency,
                                                           @Query("address") String address,
                                                           @Query("amount") double amount,
                                                           @Query("priority") String priority,
                                                           @Query("tfa") String tfa);

    @GET("private/submit_transfer_to_subaccount")
    @Headers("accept: application/json")
    CompletableFuture<TransferToSubAccountResponse> initiateTransferToSubAccount(@Header("Authorization") String header,
                                                                                 @Query("currency") String currency,
                                                                                 @Query("amount") double amount,
                                                                                 @Query("destination") int destination);

}
