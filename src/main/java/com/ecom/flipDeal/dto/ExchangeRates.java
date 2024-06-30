package com.ecom.flipDeal.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
@Getter
@Setter
public class ExchangeRates {
    String base;
    String date;
    Map<String , Double > rates;
    Boolean success;
    String timestamp;
}