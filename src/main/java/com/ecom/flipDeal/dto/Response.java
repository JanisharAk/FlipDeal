package com.ecom.flipDeal.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Response {

    String category;
    Integer inventory;
    Double rating;
    String currency;
    Double price;
    String origin;
    String product;
    Discount discount;

}