package com.ecom.flipDeal.dao;

import com.ecom.flipDeal.client.FlipDealClient;
import com.ecom.flipDeal.dto.ExchangeRates;
import com.ecom.flipDeal.dto.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ProductDao {
    @Autowired
    private FlipDealClient flipDealClient;

    public List<Product> fetchProductDetails(String url) {
        return flipDealClient.fetchData(url, new ParameterizedTypeReference<List<Product>>() {});
    }

    public ExchangeRates fetchExchangeRates(String url) {
        return flipDealClient.fetchData(url, new ParameterizedTypeReference<ExchangeRates>() {});
    }
}
