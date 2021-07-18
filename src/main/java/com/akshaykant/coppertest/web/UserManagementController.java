package com.akshaykant.coppertest.web;

import com.akshaykant.coppertest.service.AccountBalancesService;
import com.akshaykant.coppertest.web.resource.AccountBalancesResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exchanges/v1")
@Validated
@Slf4j
public class UserManagementController extends BaseController {

    private final AccountBalancesService accountBalancesService;

    public UserManagementController(AccountBalancesService accountBalancesService) {
        this.accountBalancesService = accountBalancesService;
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
}

