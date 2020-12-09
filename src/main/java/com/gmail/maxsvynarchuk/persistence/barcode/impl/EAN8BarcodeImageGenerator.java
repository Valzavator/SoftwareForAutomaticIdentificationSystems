package com.gmail.maxsvynarchuk.persistence.barcode.impl;

import com.gmail.maxsvynarchuk.persistence.barcode.BarcodeImageGenerator;

import static com.gmail.maxsvynarchuk.persistence.barcode.constant.EAN8Constants.EAN8_BARCODE_LENGTH;
import static com.gmail.maxsvynarchuk.persistence.barcode.constant.EAN8Constants.EAN8_LEFT_STABILIZATION_ZONE_MIN_LENGTH;
import static com.gmail.maxsvynarchuk.persistence.barcode.constant.EAN8Constants.EAN8_RIGHT_STABILIZATION_ZONE_MIN_LENGTH;

public class EAN8BarcodeImageGenerator extends BarcodeImageGenerator {
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
