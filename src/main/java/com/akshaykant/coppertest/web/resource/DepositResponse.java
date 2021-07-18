package com.akshaykant.coppertest.web.resource;

import com.akshaykant.coppertest.common.domain.AssetDeposit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepositResponse {

    private String clientId;

    private List<AssetDeposit> result;
}
