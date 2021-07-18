package com.akshaykant.coppertest.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawalResponseCurrency {

    WithdrawalsResponse withdrawalsResponse;

    String currency;
}
