package com.akshaykant.coppertest.web;

import com.akshaykant.coppertest.common.domain.AssetDeposit;
import com.akshaykant.coppertest.common.domain.AssetWithdrawals;
import com.akshaykant.coppertest.common.resources.CurrencyCode;
import com.akshaykant.coppertest.service.AccountBalancesService;
import com.akshaykant.coppertest.service.AccountHistoryDepositsAndWithdrawalsService;
import com.akshaykant.coppertest.service.TransferService;
import com.akshaykant.coppertest.web.resource.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exchanges/v1")
@Validated
@Slf4j
public class UserManagementController extends BaseController {

    private final AccountBalancesService accountBalancesService;
    private final AccountHistoryDepositsAndWithdrawalsService accountHistoryDepositsAndWithdrawalsService;
    private final TransferService transferService;

    public UserManagementController(AccountBalancesService accountBalancesService, AccountHistoryDepositsAndWithdrawalsService accountHistoryDepositsAndWithdrawalsService, TransferService transferService) {
        this.accountBalancesService = accountBalancesService;
        this.accountHistoryDepositsAndWithdrawalsService = accountHistoryDepositsAndWithdrawalsService;
        this.transferService = transferService;
    }

    @GetMapping(value = "/account/balances")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AccountBalancesResponse> getAccountBalances(
            @RequestHeader(value = "client_secret") String clientSecret,
            @RequestParam(value = "client_id") String clientID) throws Exception {


        log.info("Client ID " + clientID + "Client Secret " + clientSecret);

        // call the service
        var userAccountBalances = accountBalancesService.getUserBalances(clientID, clientSecret);

        log.info("UserManagementController -> AccountBalancesService ");

        return ResponseEntity.status(HttpStatus.OK).body(new AccountBalancesResponse(clientID, userAccountBalances));

    }

    @GetMapping(value = "/account/history/deposits")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DepositResponse> getAccountDeposits(
            @RequestHeader(value = "client_secret") String clientSecret,
            @RequestParam(value = "client_id") String clientID,
            @RequestParam(value = "count", required = false) int count,
            @RequestParam(value = "offset", required = false) int offset) throws Exception {


        log.info("Client ID " + clientID + "Client Secret " + clientSecret, " count " + count, " offset " + offset );

        List<AssetDeposit> userDepositHistory;
        // call the service
        if (count < 1 || offset < 0){

            userDepositHistory = accountHistoryDepositsAndWithdrawalsService.getUserDepositHistory(clientID, clientSecret, 10, 0);
        } else {

            userDepositHistory = accountHistoryDepositsAndWithdrawalsService.getUserDepositHistory(clientID, clientSecret, count, offset);
        }

        log.info("UserManagementController -> AccountHistoryDepositsAndWithdrawalsService ");

        return ResponseEntity.status(HttpStatus.OK).body(new DepositResponse(clientID, userDepositHistory));
    }

    @GetMapping(value = "/account/history/withdrawals")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<WithdrawalResponse> getAccountWithdrawals(
            @RequestHeader(value = "client_secret") String clientSecret,
            @RequestParam(value = "client_id") String clientID,
            @RequestParam(value = "count", required = false) int count,
            @RequestParam(value = "offset", required = false) int offset) throws Exception {


        log.info("Client ID " + clientID + "Client Secret " + clientSecret, " count " + count, " offset " + offset );

        List<AssetWithdrawals> userWithdrawHistory;
        // call the service
        if (count < 1 || offset < 0){

            userWithdrawHistory = accountHistoryDepositsAndWithdrawalsService.getUserWithdrawalHistory(clientID, clientSecret, 10, 0);
        } else {

            userWithdrawHistory = accountHistoryDepositsAndWithdrawalsService.getUserWithdrawalHistory(clientID, clientSecret, count, offset);
        }

        log.info("UserManagementController -> AccountHistoryDepositsAndWithdrawalsService ");

        return ResponseEntity.status(HttpStatus.OK).body(new WithdrawalResponse(clientID, userWithdrawHistory));
    }

    @GetMapping(value = "/account/transfer/external_account")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TransferToExternalAccountResponse> initiateTransferToExternalAccount(
            @RequestHeader(value = "client_secret") String clientSecret,
            @RequestParam(value = "client_id") String clientID,
            @RequestParam(value = "amount") double amount,
            @RequestParam(value = "currency") CurrencyCode currency,
            @RequestParam(value = "destination_external_account") String destinationExternalAccount) throws Exception {


        log.info("Client ID " + clientID + "Client Secret " + clientSecret);

        // call the service
        var transferToExternalAccount = transferService.initiateTransferToExternalAccount(clientID, clientSecret, amount, currency.toString(), destinationExternalAccount);

        log.info("UserManagementController -> TransferService ");

        return ResponseEntity.status(HttpStatus.OK).body(new TransferToExternalAccountResponse(clientID, transferToExternalAccount));

    }

    @GetMapping(value = "/account/transfer/sub_account")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TransferToSubAccountResponse> initiateTransferToSubAccount(
            @RequestHeader(value = "client_secret") String clientSecret,
            @RequestParam(value = "client_id") String clientID,
            @RequestParam(value = "amount") double amount,
            @RequestParam(value = "currency") CurrencyCode currency,
            @RequestParam(value = "destination_sub_account") int destinationSubAccount) throws Exception {


        log.info("Client ID " + clientID + "Client Secret " + clientSecret);

        // call the service
        var transferToSubAccount = transferService.initiateTransferToSubAccount(clientID, clientSecret, amount, currency.toString(), destinationSubAccount);

        log.info("UserManagementController -> TransferService ");

        return ResponseEntity.status(HttpStatus.OK).body(new TransferToSubAccountResponse(clientID, transferToSubAccount));

    }
}

