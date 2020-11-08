package com.gmail.barcode.generator.image;

import com.gmail.barcode.generator.BitSequence;

import java.awt.*;
import java.awt.image.BufferedImage;

import static com.gmail.barcode.generator.image.constant.BarcodeImageConstant.*;

public abstract class BarcodeImage {
    private final int moduleLength;
    private final int height;
    private final int type;

    public BarcodeImage() {
        this.moduleLength = MODULE_LENGTH;
        this.height = IMAGE_HEIGHT;
        this.type = IMAGE_TYPE;
    }

    protected abstract int getLeftStabilizationZoneLength();

    protected abstract int getRightStabilizationZoneLength();

    protected abstract int getBarcodeLength();

    public BufferedImage generateImage(BitSequence bitSequence) {
        BufferedImage bi = new BufferedImage(calcMinWidth(), height, type);
        Graphics2D graphics = bi.createGraphics();
        int x = getLeftStabilizationZoneLength() * MODULE_LENGTH;
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, bi.getWidth(), bi.getHeight());

        graphics.setColor(Color.BLACK);
        for (int i = 0; i < bitSequence.getLength(); i++) {
            if (bitSequence.get(i)) {
                graphics.fillRect(x, 0, moduleLength, bi.getHeight());
            }
            x += moduleLength;
        }
        return bi;
    }

    private int calcMinWidth() {
        return MODULE_LENGTH *
                (getLeftStabilizationZoneLength() + getBarcodeLength() + getRightStabilizationZoneLength());
    }
}
