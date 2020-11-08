package com.gmail.barcode.generator.barcode.impl.ean8;

import com.gmail.barcode.generator.barcode.BarcodeGenerator;
import com.gmail.barcode.generator.BitSequence;

import java.util.Objects;

import static com.gmail.barcode.generator.barcode.constant.EAN8Constants.*;

public class EAN8BarcodeGenerator implements BarcodeGenerator {
    private static final int RADIX = 10;

    @Override
    public BitSequence encode(String msg) {
        validateMessage(msg);
        msg = handleChecksum(msg);
        BitSequence bits = new BitSequence(EAN8_BARCODE_LENGTH);

        bits.append(START_STOP_PATTERN);
        for (int i = 0; i < 4; i++) {
            int digit = Character.digit(msg.charAt(i), RADIX);
            bits.append(A_PATTERNS[digit], false);
        }
        bits.append(MIDDLE_PATTERN);
        for (int i = 4; i < 8; i++) {
            int digit = Character.digit(msg.charAt(i), RADIX);
            bits.append(A_PATTERNS[digit], true);
        }
        bits.append(START_STOP_PATTERN);

        return bits;
    }

    private void validateMessage(String msg) {
        if (Objects.isNull(msg)
                || msg.length() < EAN8_MESSAGE_MIN_LENGTH
                || msg.length() > EAN8_MESSAGE_MAX_LENGTH) {
            throw new IllegalArgumentException("Message must be 7 or 8 characters long. Message: " + msg);
        }
        if (!msg.matches("\\d+")) {
            throw new IllegalArgumentException("Invalid characters found. Valid are 0-9 only. Message: " + msg);
        }
    }

    private String handleChecksum(String msg) {
        if (msg.length() == EAN8_MESSAGE_MIN_LENGTH) {
            return msg + calcChecksum(msg);
        }
        char check = msg.charAt(7);
        char expected = calcChecksum(msg.substring(0, 7));
        if (check != expected) {
            throw new IllegalArgumentException("Checksum is bad (" + check + "). Expected: " + expected);
        }
        return msg;
    }

    private char calcChecksum(String msg) {
        int oddSum = 0;
        int evenSum = 0;
        for (int i = msg.length() - 1; i >= 0; i--) {
            if (i % 2 == 0) {
                oddSum += Character.digit(msg.charAt(i), RADIX);
            } else {
                evenSum += Character.digit(msg.charAt(i), RADIX);
            }
        }
        int checksum = 10 - (evenSum + 3 * oddSum) % 10;
        if (checksum >= 10) {
            checksum = 0;
        }
        return Character.forDigit(checksum, RADIX);
    }

}
