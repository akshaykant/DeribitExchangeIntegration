package com.akshaykant.coppertest.web.resource;

import com.akshaykant.coppertest.common.domain.AssetBalance;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountBalancesResponse {

    private String clientId;

    private List<AssetBalance> result;
}
