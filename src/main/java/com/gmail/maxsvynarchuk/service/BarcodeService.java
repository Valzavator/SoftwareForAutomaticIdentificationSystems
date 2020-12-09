package com.gmail.maxsvynarchuk.service;

import com.gmail.maxsvynarchuk.persistence.barcode.Barcode;
import com.gmail.maxsvynarchuk.persistence.barcode.BarcodeImageGenerator;
import com.gmail.maxsvynarchuk.persistence.barcode.BitSequence;
import com.gmail.maxsvynarchuk.persistence.dao.entity.Product;
import com.gmail.maxsvynarchuk.presentation.dto.BarcodeDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static com.gmail.maxsvynarchuk.presentation.util.constant.Resources.RESOURCES_PATH;

@Service
@AllArgsConstructor
public class BarcodeService {
    private static final String IMAGE_FORMAT = "PNG";

    private final Barcode ean8Barcode;
    private final BarcodeImageGenerator imageGenerator;

    public BarcodeDto generateBarcodeForProduct(Product product) {
        String code = getCodeFromProduct(product);
        BitSequence bitSequence = ean8Barcode.encode(code);
        String fileName = bitSequence.getMessage() + "." + IMAGE_FORMAT;
        try {
            saveImage(imageGenerator.generateImage(bitSequence), fileName);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        return BarcodeDto.builder()
                .code(bitSequence.getMessage())
                .imageName(fileName)
                .build();
    }

    public String recognizeBarcode(BufferedImage bi) {
        BitSequence bitSequence = imageGenerator.decodeImage(bi);
        return ean8Barcode.decode(bitSequence);
    }

    public String[] getManufacturerCodes(String barcode) {
        return new String[] {
                barcode.substring(0, 2),
                barcode.substring(0, 3)
        };
    }

    public String getProductCode(String barcode) {
        return barcode.substring(2, barcode.length() - 1);
    }

    private String getCodeFromProduct(Product product) {
        String countryCode = product.getCountry().getCode();
        String productCode = product.getCode();
        if (countryCode.length() == 3) {
            countryCode = countryCode.substring(0, 2);
        }
        return countryCode + productCode;
    }

    private void saveImage(BufferedImage image, String fileName) throws IOException {
        File file = new File(RESOURCES_PATH, fileName);
        ImageIO.write(image, IMAGE_FORMAT, file);
    }

}
