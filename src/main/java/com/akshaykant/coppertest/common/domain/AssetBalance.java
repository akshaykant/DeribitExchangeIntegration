package com.akshaykant.coppertest.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssetBalance {

    private String currency;

    private double account_balance; //balance

    private double reserved_balance; //  available_funds - available_withdrawal_funds

    private double initial_margin;

    private double margin_balance;

    private String deposit_address;
}
