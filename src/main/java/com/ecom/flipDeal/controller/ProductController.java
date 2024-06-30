package com.ecom.flipDeal.controller;

import com.ecom.flipDeal.client.FlipDealClient;
import com.ecom.flipDeal.dto.Product;
import com.ecom.flipDeal.dto.Response;
import com.ecom.flipDeal.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("api/v1")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping()
    public String applyPromotion() throws IOException {
        return "Hello World";
    }

    @GetMapping("/discount/{promotionType}")
    public List<Response> fetchPromotionList(@PathVariable String promotionType) throws IOException {
        return productService.getProductResponseList(promotionType);
    }
}

