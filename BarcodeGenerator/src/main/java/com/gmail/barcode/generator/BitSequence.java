package com.gmail.barcode.generator;

import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Stream;

public final class BitSequence {
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    private int length;
    private Integer[] bits;
    private int pos;

    public BitSequence(int length) {
        checkLength(length);
        this.length = length;
        this.bits = new Integer[length];
        this.pos = 0;
    }

    public BitSequence(Boolean[] bits) {
        this.length = bits.length;
        this.pos = this.length;
        this.bits = new Integer[length];
        System.arraycopy(bits, 0, bits, 0, this.length);
    }

    public void append(int[] values) {
        Objects.requireNonNull(values);
        if (getEmpty() < values.length) {
            throw new IllegalArgumentException("BitSequence cannot accommodate " + values.length + " values. " +
                    getEmpty() + " free bits left");
        }
        for (int value : values) {
            bits[pos++] = value;
        }
    }

    public void append(int[] values, boolean color) {
        Objects.requireNonNull(values);
        for (int value : values) {
            append(value, color);
        }
    }

    public void append(int value) {
        append(value, true);
    }

    private void append(int value, boolean color) {
        if (isFull()) {
            throw new IllegalArgumentException("BitSequence is already full");
        }
        bits[pos++] = color ? value : inverseBitValue(value);
    }

    public boolean getBit(int index) {
        return get(index) == 1;
    }

    public int get(int index) {
        checkIndex(index);
        return bits[index];
    }

    public boolean isFull() {
        return pos >= length;
    }

    public void truncate(int from, int to) {
        if (from < 0 || to > length || from >= to) {
            throw new IllegalArgumentException("Invalid for truncating");
        }
        Integer[] newBits = new Integer[length - to + from];
        for (int i = 0, j = 0; i < bits.length; i++) {
            if (i >= from && i < to) {
                continue;
            }
            newBits[j++] = bits[i];
        }
        this.bits = newBits;
        this.length = newBits.length;
    }

    public boolean isEqualSubArray(int[] arr, int from, boolean color) {
        int[] simpleIntArr = Arrays.stream(bits).mapToInt(i -> i).toArray();
        if (!color) {
            arr = Arrays.stream(arr).map(v -> v == 0 ? 1 : 0).toArray();
        }
        return Arrays.equals(simpleIntArr, from, from + arr.length, arr, 0, arr.length);
    }

    private int getEmpty() {
        return length - pos;
    }

    public int getLength() {
        return length;
    }

    private int inverseBitValue(int value) {
        return value == 0 ? 1 : 0;
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
