package com.gmail.barcode.generator;

import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

public final class BitSequence {
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    private final int length;
    private final Boolean[] bits;
    private int pos;

    public BitSequence(int length) {
        checkLength(length);
        this.length = length;
        this.bits = new Boolean[length];
        this.pos = 0;
    }

    public void append(int[] values) {
        Objects.requireNonNull(values);
        if (getEmpty() < values.length) {
            throw new IllegalArgumentException("BitSequence cannot accommodate " + values.length + " values. " +
                    getEmpty() + " free bits left");
        }
        for (int value : values) {
            bits[pos++] = (value != 0);
        }
    }

    public void append(int[] values, boolean startColor) {
        Objects.requireNonNull(values);
        boolean color = startColor;
        for (int value : values) {
            append(value, color);
            color = !color;
        }
    }

    private void append(int value, boolean color) {
        if (isFull()) {
            throw new IllegalArgumentException("BitSequence is already full");
        }
        for (int i = 0; i < value; i++) {
            bits[pos++] = color;
        }
    }

    public boolean get(int index) {
        checkIndex(index);
        return bits[index];
    }

    public boolean isFull() {
        return pos >= length;
    }

    private int getEmpty() {
        return length - pos;
    }

    public int getLength() {
        return length;
    }

    private void checkLength(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("The bit sequence must contain at least 1 bit");
        }
        if (length > MAX_ARRAY_SIZE) {
            throw new IllegalArgumentException("The bit sequence cannot contain more than " + MAX_ARRAY_SIZE + " bits");
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= length) {
            throw new IllegalArgumentException("The index must be in range [0," + length + "]");
        }
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", BitSequence.class.getSimpleName() + "[", "]")
                .add("length=" + length)
                .add("bits=" + Arrays.toString(bits))
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BitSequence that = (BitSequence) o;
        return length == that.length &&
                Arrays.equals(bits, that.bits);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(length);
        result = 31 * result + Arrays.hashCode(bits);
        return result;
    }
}
