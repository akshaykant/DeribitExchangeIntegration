package com.akshaykant.coppertest.repository.model;


//@Entity
//@Table(name = "user_balance")
public class UserBalance {
    private int id;
    private String clientID;
    private String currency;
    private double accountBalance;
    private double reservedBalance;
    private double initialMargin;
    private double marginBalance;
    private String deposit_address;

    public UserBalance() {
    }

    public UserBalance(String clientID, String currency, double accountBalance, double reservedBalance, double initialMargin, double marginBalance, String deposit_address) {
        this.clientID = clientID;
        this.currency = currency;
        this.accountBalance = accountBalance;
        this.reservedBalance = reservedBalance;
        this.initialMargin = initialMargin;
        this.marginBalance = marginBalance;
        this.deposit_address = deposit_address;

    }

    public void setId(int id) {
        this.id = id;
    }

 //   @Id
    public int getId() {
        return id;
    }
}
