package com.akshaykant.coppertest.web;

import com.akshaykant.coppertest.common.domain.AssetDeposit;
import com.akshaykant.coppertest.common.domain.AssetWithdrawals;
import com.akshaykant.coppertest.service.AccountBalancesService;
import com.akshaykant.coppertest.service.AccountHistoryDepositsAndWithdrawalsService;
import com.akshaykant.coppertest.web.resource.AccountBalancesResponse;
import com.akshaykant.coppertest.web.resource.DepositResponse;
import com.akshaykant.coppertest.web.resource.WithdrawalResponse;
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


    public UserManagementController(AccountBalancesService accountBalancesService, AccountHistoryDepositsAndWithdrawalsService accountHistoryDepositsAndWithdrawalsService) {
        this.accountBalancesService = accountBalancesService;
        this.accountHistoryDepositsAndWithdrawalsService = accountHistoryDepositsAndWithdrawalsService;
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
}

