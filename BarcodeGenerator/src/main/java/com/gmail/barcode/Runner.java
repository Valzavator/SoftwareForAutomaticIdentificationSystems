package com.gmail.barcode;

import com.gmail.barcode.generator.BitSequence;
import com.gmail.barcode.generator.barcode.impl.ean8.EAN8BarcodeGenerator;
import com.gmail.barcode.generator.image.BarcodeImage;
import com.gmail.barcode.generator.image.impl.EAN8BarcodeImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Runner {
    private static final String RESOURCES_PATH = "BarcodeGenerator/src/main/resources";

    public static void main(String[] args) throws IOException {
        generateBarcodeImage("12345670", "12345670.png");
//        generateBarcodeImage("0123456", "0123456.png");
        generateBarcodeImage("9031101", "9031101.png");
    }

    private static void generateBarcodeImage(String msg, String fileName) throws IOException {
        EAN8BarcodeGenerator ean8Barcode = new EAN8BarcodeGenerator();
        BitSequence bitSequence = ean8Barcode.encode(msg);
        System.out.println(bitSequence);
        BarcodeImage image = new EAN8BarcodeImage();
        saveImage(image.generateImage(bitSequence), fileName);
    }

    private static void saveImage(BufferedImage image, String fileName) throws IOException {
        File file = new File(RESOURCES_PATH, fileName);
        ImageIO.write(image, "png", file);
    }
}
