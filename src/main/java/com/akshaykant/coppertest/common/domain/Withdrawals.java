package com.akshaykant.coppertest.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Withdrawals {
    public String address;
    public double amount;
    public Object confirmed_timestamp;
    public long created_timestamp;
    public String currency;
    public double fee;
    public int id;
    public double priority;
    public String state;
    public Object transaction_id;
    public long updated_timestamp;
}
