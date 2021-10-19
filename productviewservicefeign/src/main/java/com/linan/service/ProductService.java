package com.linan.service;

import com.linan.client.ProductClientFeign;
import com.linan.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductClientFeign productClientFeign;

    public List<Product> listProducts(){
        return productClientFeign.listProducts();

    }
}
