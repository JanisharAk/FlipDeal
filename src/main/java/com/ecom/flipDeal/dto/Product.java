package com.ecom.flipDeal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
      String category;
      Integer inventory;
      Double rating;
      String currency;
      Double price;
      String origin;
      String product;
      String arrival;
}