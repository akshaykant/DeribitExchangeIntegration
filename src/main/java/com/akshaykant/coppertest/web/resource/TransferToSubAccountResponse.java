package com.akshaykant.coppertest.web.resource;

import com.akshaykant.coppertest.common.domain.TransferToSubAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferToSubAccountResponse {

    private String clientId;

    private TransferToSubAccount result;
}

