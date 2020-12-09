package com.gmail.barcode.generator.barcode;

import com.gmail.barcode.generator.BitSequence;

public interface BarcodeGenerator {

    BitSequence encode(String msg);

    String decode(BitSequence bitSequence);

}
