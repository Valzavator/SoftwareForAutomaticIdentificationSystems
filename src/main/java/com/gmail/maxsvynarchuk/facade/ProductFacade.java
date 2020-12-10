package com.gmail.maxsvynarchuk.facade;

import com.gmail.maxsvynarchuk.persistence.dao.entity.Country;
import com.gmail.maxsvynarchuk.persistence.dao.entity.Manufacturer;
import com.gmail.maxsvynarchuk.persistence.dao.entity.Product;
import com.gmail.maxsvynarchuk.presentation.dto.ProductDto;
import com.gmail.maxsvynarchuk.presentation.dto.BarcodeDto;
import com.gmail.maxsvynarchuk.service.BarcodeService;
import com.gmail.maxsvynarchuk.service.CountryService;
import com.gmail.maxsvynarchuk.service.ManufacturerService;
import com.gmail.maxsvynarchuk.service.ProductService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class ProductFacade {
    private final CountryService countryService;
    private final ProductService productService;
    private final BarcodeService barcodeService;
    private final ManufacturerService manufacturerService;
    private final ModelMapper mapper;

    public BarcodeDto createNewProduct(ProductDto productDto) {
        Country country = countryService.findByCode(productDto.getCountryCode())
                .orElseThrow();
        Manufacturer manufacturer = manufacturerService.getOrCreate(
                productDto.getManufacturerName(), productDto.getManufacturerAddress());
        Product newProduct = mapper.map(productDto, Product.class);
        newProduct.setManufacturer(manufacturer);
        newProduct.setCountry(country);

        newProduct = productService.createNewProduct(newProduct);
        return barcodeService.generateBarcodeForProduct(newProduct);
    }

    public ProductDto recognizeBarcode(MultipartFile barcodeImage) throws IOException {
        BufferedImage bi = ImageIO.read(barcodeImage.getInputStream());
        String barcodeString = barcodeService.recognizeBarcode(bi);
        Optional<Product> productOpt = productService.findByCode(
                barcodeService.getProductCode(barcodeString));
        if (productOpt.isEmpty()) {
            return null;
        }
        Product product = productOpt.get();
        ProductDto dto = mapper.map(product, ProductDto.class);
        dto.setBarcode(barcodeString);
        return dto;
    }

}
