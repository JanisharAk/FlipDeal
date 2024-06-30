package com.ecom.flipDeal.client;

import com.ecom.flipDeal.dto.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class FlipDealClient {

    @Autowired
    private RestTemplate restTemplate;

    public <T> T fetchData(String url, ParameterizedTypeReference<T> responseType) {
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
        return response.getBody();
    }


}
