package com.gmail.barcode.generator.image.impl;

import com.gmail.barcode.generator.image.BarcodeImage;

import static com.gmail.barcode.generator.barcode.constant.EAN8Constants.EAN8_LEFT_STABILIZATION_ZONE_MIN_LENGTH;
import static com.gmail.barcode.generator.barcode.constant.EAN8Constants.EAN8_BARCODE_LENGTH;
import static com.gmail.barcode.generator.barcode.constant.EAN8Constants.EAN8_RIGHT_STABILIZATION_ZONE_MIN_LENGTH;

public class EAN8BarcodeImage extends BarcodeImage {
    @Override
    protected int getLeftStabilizationZoneLength() {
        return EAN8_LEFT_STABILIZATION_ZONE_MIN_LENGTH;
    }

    @Override
    protected int getRightStabilizationZoneLength() {
        return EAN8_RIGHT_STABILIZATION_ZONE_MIN_LENGTH;
    }

    @Override
    protected int getBarcodeLength() {
        return EAN8_BARCODE_LENGTH;
    }
}
