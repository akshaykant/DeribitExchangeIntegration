package com.akshaykant.coppertest.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepositResponseCurrency {

   DepositResponse depositResponse;

    String currency;
}
