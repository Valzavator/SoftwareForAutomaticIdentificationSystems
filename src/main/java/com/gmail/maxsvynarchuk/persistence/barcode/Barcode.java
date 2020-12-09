package com.gmail.maxsvynarchuk.persistence.barcode;

public interface Barcode {

    BitSequence encode(String msg);

    String decode(BitSequence bitSequence);

}
