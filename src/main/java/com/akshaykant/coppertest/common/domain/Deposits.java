package com.akshaykant.coppertest.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Deposits {

    private String address;
    private int amount;
    private String currency;
    private long received_timestamp;
    private String state;
    private String transaction_id;
    private long updated_timestamp;

}
