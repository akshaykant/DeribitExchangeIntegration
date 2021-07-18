package com.akshaykant.coppertest.service;

import com.akshaykant.coppertest.common.domain.AssetBalance;
import com.akshaykant.coppertest.repository.UserBalanceRepositoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Async
@Service
@Slf4j
public class UserBalanceService {

    private final UserBalanceRepositoryService userBalanceRepositoryService;


    public UserBalanceService(UserBalanceRepositoryService userBalanceRepositoryService) {
        this.userBalanceRepositoryService = userBalanceRepositoryService;
    }

    public void saveUserBalance(String clientID, List<AssetBalance> assetBalancesList) {

        for (AssetBalance accountBalance : assetBalancesList) {
           // userBalanceRepositoryService.saveUser(new UserBalance(clientID, accountBalance.getCurrency(), accountBalance.getAccount_balance(), accountBalance.getReserved_balance(), accountBalance.getReserved_balance(), accountBalance.getMargin_balance(), accountBalance.getDeposit_address()));

        }
    }
}
