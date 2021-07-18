package com.akshaykant.coppertest.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferToSubAccount {

    public long updated_timestamp;
    public String type;
    public String state;
    public String other_side;
    public int id;
    public String direction;
    public String currency;
    public long created_timestamp;
    public double amount;
}
