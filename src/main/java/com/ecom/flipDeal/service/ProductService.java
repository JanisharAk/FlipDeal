package com.ecom.flipDeal.service;

import com.ecom.flipDeal.client.FlipDealClient;
import com.ecom.flipDeal.dao.ProductDao;
import com.ecom.flipDeal.dto.Discount;
import com.ecom.flipDeal.dto.ExchangeRates;
import com.ecom.flipDeal.dto.Product;
import com.ecom.flipDeal.dto.Response;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    private static final String PRODUCTS_API_URL = "https://mock.coverself.net/rest/hiring/products";
    private static final String EXCHANGE_RATES_API_URL = "https://mock.coverself.net/rest/hiring/exchange-rates";
    @Autowired
    private ProductDao productDao;

    public List<Product> fetchProductDetails( ) {
        return productDao.fetchProductDetails(PRODUCTS_API_URL);
    }

    public ExchangeRates fetchExchangeRates( ) {
        return productDao.fetchExchangeRates(EXCHANGE_RATES_API_URL);
    }

    public List<Response> getProductResponseList(String promotionType){
        List<Product> products = fetchProductDetails( );
        ExchangeRates exchangeRates = fetchExchangeRates( );
        List<Response> responseList = new ArrayList<>();
        for ( Product product : products ) {
            Discount discount = applyConversionAndPromotions(product, exchangeRates, promotionType);
            Response response = new Response();
            response.setProduct(product.getProduct());
            response.setCategory(product.getCategory());
            response.setCurrency(product.getCurrency());
            response.setPrice(product.getPrice());
            response.setInventory(product.getInventory());
            response.setOrigin(product.getOrigin());
            response.setRating(product.getRating());

            response.setDiscount(discount);
            responseList.add(response);

        }
        return responseList;
    }

    private Discount applyConversionAndPromotions(Product product, ExchangeRates exchangeRates, String promotionSet) {
        Double price = product.getPrice();
        String currency = product.getCurrency();
        Double priceInINR = convertToINR(price, currency, exchangeRates);

        Double discount = 0D;
        String discountTag = "";

        // Apply promotion logic based on the promotion set
        if ("promotionSetA".equalsIgnoreCase(promotionSet)) {
            // Promotion Set A
            if ("Africa".equalsIgnoreCase(product.getOrigin())) {
                discount = priceInINR * 0.07;
                discountTag = "get 7% off";
            } else if (product.getRating() == 2) {
                discount = priceInINR * 0.04;
                discountTag = "get 4% off";
            } else if (product.getRating() < 2) {
                discount = priceInINR * 0.08;
                discountTag = "get 8% off";
            } else if (product.getCategory().matches("electronics|furnishing") && priceInINR >= 500) {
                discount = 100.0;
                discountTag = "get Rs 100 off";
            }
        } else if ("promotionSetB".equalsIgnoreCase(promotionSet)) {
            // Promotion Set B
            if (product.getInventory() > 20) {
                discount = priceInINR * 0.12;
                discountTag = "get 12% off";
            } else if ("new".equalsIgnoreCase(product.getArrival())) {
                discount = priceInINR * 0.07;
                discountTag = "get 7% off";
            }
        }

        // Common rule
        if (priceInINR > 1000 && discount == 0) {
            discount = priceInINR * 0.02;
            discountTag = "get 2% off";
        }

        Discount discountObj = new Discount();
        discountObj.setAmount(discount);
        discountObj.setDiscountTag(discountTag);
        return discountObj;
    }

    private static Double convertToINR(Double price, String currency, ExchangeRates exchangeRates) {
        if ("INR".equalsIgnoreCase(currency)) {
            return price;
        }
        Double conversionRate = exchangeRates.getRates().get(currency);
        return price * conversionRate;
    }
}
