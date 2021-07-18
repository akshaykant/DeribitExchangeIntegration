package com.akshaykant.coppertest.service.exchange;

import com.akshaykant.coppertest.service.model.AccountSummaryResponse;
import com.akshaykant.coppertest.service.model.AuthResponse;
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

}