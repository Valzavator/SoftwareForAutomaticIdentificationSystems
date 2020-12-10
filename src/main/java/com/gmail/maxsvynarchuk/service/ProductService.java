package com.gmail.maxsvynarchuk.service;

import com.gmail.maxsvynarchuk.persistence.dao.entity.Product;
import com.gmail.maxsvynarchuk.persistence.dao.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product createNewProduct(Product product) {
        product.setCode(generateProductCode(product));
        return productRepository.save(product);
    }

    public Optional<Product> findByCode(String code) {
        return productRepository.findByCode(code);
    }


    private String generateProductCode(Product product) {
        String countryCode = product.getCountry().getCode();
        StringBuilder sb = new StringBuilder();
        long productNum = productRepository.getMaxId() + 1;

        // Simple check for product limit
        if (productNum > 9999) {
            throw new IllegalArgumentException("Product limit reached");
        }

        if (countryCode.length() == 3) {
            sb.append(countryCode.charAt(countryCode.length() - 1));
        }
        sb.append(productNum);
        while (sb.length() != 5) {
            sb.insert(0, 0);
        }
        return sb.toString();
    }

}
