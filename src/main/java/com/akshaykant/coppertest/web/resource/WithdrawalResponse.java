package com.akshaykant.coppertest.web.resource;

import com.akshaykant.coppertest.common.domain.AssetWithdrawals;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawalResponse{

    private String clientId;

    private List<AssetWithdrawals> result;
}
