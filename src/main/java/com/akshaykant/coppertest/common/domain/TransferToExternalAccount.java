package com.akshaykant.coppertest.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferToExternalAccount {

    public String address;
    public double amount;
    public int confirmed_timestamp;
    public long created_timestamp;
    public String currency;
    public double fee;
    public int id;
    public int priority;
    public String state;
    public String transaction_id;
    public long updated_timestamp;
}