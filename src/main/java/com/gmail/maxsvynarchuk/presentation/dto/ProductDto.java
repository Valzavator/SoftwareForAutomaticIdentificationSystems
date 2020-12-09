package com.gmail.maxsvynarchuk.presentation.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDto {
    private String productName;
    private String productDescription;
    private String countryCode;
    private String countryName;
    private String manufacturerName;
    private String manufacturerAddress;
    private String barcode;
}
