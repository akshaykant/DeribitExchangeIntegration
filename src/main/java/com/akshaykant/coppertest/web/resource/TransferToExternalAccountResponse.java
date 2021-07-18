package com.akshaykant.coppertest.web.resource;

import com.akshaykant.coppertest.common.domain.TransferToExternalAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferToExternalAccountResponse {

    private String clientId;

    private TransferToExternalAccount result;
}
