package com.gmail.barcode;

import com.gmail.barcode.generator.barcode.BarcodeGenerator;
import com.gmail.barcode.generator.barcode.impl.ean8.EAN8BarcodeGenerator;
import com.gmail.barcode.generator.image.BarcodeImage;
import com.gmail.barcode.generator.image.impl.EAN8BarcodeImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Reader {
    private static final String RESOURCES_PATH = "BarcodeGenerator/src/main/resources";

    public static void main(String[] args) throws IOException {
        File input = new File(RESOURCES_PATH, "0123456.png");
        BufferedImage image = ImageIO.read(input);
        BarcodeImage barcodeImage = new EAN8BarcodeImage();
        BarcodeGenerator bg = new EAN8BarcodeGenerator();
        System.out.println(barcodeImage.decodeImage(image));
        System.out.println(bg.decode(barcodeImage.decodeImage(image)));
    }
}
