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
            bits.append(A_PATTERNS[digit], true);
        }
        bits.append(MIDDLE_PATTERN);
        for (int i = 4; i < 8; i++) {
            int digit = Character.digit(msg.charAt(i), RADIX);
            bits.append(A_PATTERNS[digit], false);
        }
        bits.append(START_STOP_PATTERN);

        return bits;
    }

    @Override
    public String decode(BitSequence bitSequence) {
        validateBitSequence(bitSequence);
        StringBuilder sb = new StringBuilder();
        int charPatternLength = A_PATTERNS[0].length;

        for (int i = 0; i < 4; i++) {
            int offset = START_STOP_PATTERN.length + i * charPatternLength;
            sb.append(decodeChar(bitSequence, offset, true));
        }

        for (int i = 0; i < 4; i++) {
            int offset = START_STOP_PATTERN.length + MIDDLE_PATTERN.length + (4 + i) * charPatternLength;
            sb.append(decodeChar(bitSequence, offset, false));
        }
        return sb.toString();
    }

    private void validateBitSequence(BitSequence bitSequence) {
        if (bitSequence.getLength() != EAN8_BARCODE_LENGTH) {
            throw new IllegalArgumentException("Invalid Bit Sequence");
        }
        if (!bitSequence.isEqualSubArray(START_STOP_PATTERN, 0, true)) {
            throw new IllegalArgumentException("Invalid START pattern");
        }
        if (!bitSequence.isEqualSubArray(START_STOP_PATTERN, bitSequence.getLength() - START_STOP_PATTERN.length, true)) {
            throw new IllegalArgumentException("Invalid STOP pattern");
        }
        int charPatternLength = A_PATTERNS[0].length;
        if (!bitSequence.isEqualSubArray(MIDDLE_PATTERN, START_STOP_PATTERN.length + charPatternLength * 4, true)) {
            throw new IllegalArgumentException("Invalid MIDDLE pattern");
        }
    }

    private char decodeChar(BitSequence bitSequence, int offset, boolean color) {
        for (int i = 0; i < A_PATTERNS.length; i++) {
            if (bitSequence.isEqualSubArray(A_PATTERNS[i], offset, color)) {
                return Character.forDigit(i, 10);
            }
        }
        throw new IllegalArgumentException("Invalid barcode");
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
