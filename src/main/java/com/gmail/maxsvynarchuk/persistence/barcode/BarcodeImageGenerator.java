package com.gmail.maxsvynarchuk.persistence.barcode;


import com.gmail.maxsvynarchuk.persistence.barcode.BitSequence;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

import static com.gmail.maxsvynarchuk.persistence.barcode.constant.BarcodeImageConstant.IMAGE_HEIGHT;
import static com.gmail.maxsvynarchuk.persistence.barcode.constant.BarcodeImageConstant.IMAGE_TYPE;
import static com.gmail.maxsvynarchuk.persistence.barcode.constant.BarcodeImageConstant.MODULE_LENGTH;
import static com.gmail.maxsvynarchuk.persistence.barcode.constant.BarcodeImageConstant.WHITE_COLOR_RGB;

public abstract class BarcodeImageGenerator {
    private final int moduleLength;
    private final int height;
    private final int type;

    public BarcodeImageGenerator() {
        this.moduleLength = MODULE_LENGTH;
        this.height = IMAGE_HEIGHT;
        this.type = IMAGE_TYPE;
    }

    protected abstract int getLeftStabilizationZoneLength();

    protected abstract int getRightStabilizationZoneLength();

    protected abstract int getBarcodeLength();

    public BufferedImage generateImage(BitSequence bitSequence) {
        BufferedImage bi = new BufferedImage(getLengthInPixels(), height, type);
        Graphics2D graphics = bi.createGraphics();
        int x = getLeftStabilizationZoneLength() * MODULE_LENGTH;
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, bi.getWidth(), bi.getHeight());

        graphics.setColor(Color.BLACK);
        for (int i = 0; i < bitSequence.getLength(); i++) {
            if (bitSequence.getBit(i)) {
                graphics.fillRect(x, 0, moduleLength, bi.getHeight());
            }
            x += moduleLength;
        }
        return bi;
    }

    public BitSequence decodeImage(BufferedImage image) {
        validateImage(image);
        BitSequence bits = new BitSequence(getLengthInModules());
        for (int i = 0; i < bits.getLength(); i++) {
            bits.append(readColor(image, i));
        }
        checkStabilizationZones(bits);
        return removeStabilizationZones(bits);
    }

    private int readColor(BufferedImage bi, int moduleIndex) {
        int Y = bi.getHeight() / 2;
        int startX = moduleIndex * MODULE_LENGTH;
        int color = bi.getRGB(startX, Y);
        for (int i = 1; i < MODULE_LENGTH; i++) {
            if (color != bi.getRGB(startX + i, Y)) {
                throw new IllegalArgumentException("Barcode is corrupt or incorrect");
            }
        }
        return color == WHITE_COLOR_RGB
                ? 0
                : 1;
    }

    private int getLengthInPixels() {
        return MODULE_LENGTH * getLengthInModules();
    }

    private int getLengthInModules() {
        return getLeftStabilizationZoneLength() + getBarcodeLength() + getRightStabilizationZoneLength();
    }

    private void validateImage(BufferedImage image) {
        Objects.requireNonNull(image);
        int width = image.getWidth();
        if (width != getLengthInPixels()) {
            throw new IllegalArgumentException("Invalid width of image");
        }
    }

    private void checkStabilizationZones(BitSequence bitSequence) {
        for (int i = 0; i < getLeftStabilizationZoneLength(); i++) {
            if (bitSequence.getBit(i)) {
                throw new IllegalArgumentException("Invalid left stabilization zone");
            }
        }

        int lengthInModules = getLengthInModules();
        for (int i = lengthInModules - 1; i >= lengthInModules - getRightStabilizationZoneLength(); i--) {
            if (bitSequence.getBit(i)) {
                throw new IllegalArgumentException("Invalid right stabilization zone");
            }
        }
    }

    private BitSequence removeStabilizationZones(BitSequence bitSequence) {
        bitSequence.truncate(0, getLeftStabilizationZoneLength());
        bitSequence.truncate(bitSequence.getLength() - getRightStabilizationZoneLength(), bitSequence.getLength());
        return bitSequence;
    }
}
