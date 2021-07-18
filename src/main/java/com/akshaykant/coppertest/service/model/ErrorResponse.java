package com.akshaykant.coppertest.service.model;

public class ErrorResponse{
    public String jsonrpc;
    public int id;
    public Error error;
    public boolean testnet;
    public long usIn;
    public long usOut;
    public int usDiff;
}
