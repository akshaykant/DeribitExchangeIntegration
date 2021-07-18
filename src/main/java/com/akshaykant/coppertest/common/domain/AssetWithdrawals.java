package com.akshaykant.coppertest.common.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssetWithdrawals {

    public int count;

    private String currency;

    public List<Withdrawals> data;
}
