package com.gmail.maxsvynarchuk.config;

import com.gmail.maxsvynarchuk.persistence.barcode.Barcode;
import com.gmail.maxsvynarchuk.persistence.barcode.BarcodeImageGenerator;
import com.gmail.maxsvynarchuk.persistence.barcode.impl.EAN8BarcodeImageGenerator;
import com.gmail.maxsvynarchuk.persistence.barcode.impl.ean8.EAN8Barcode;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public BarcodeImageGenerator ean8Barcode() {
        return new EAN8BarcodeImageGenerator();
    }

    @Bean
    public Barcode imageGenerator() {
        return new EAN8Barcode();
    }
}
