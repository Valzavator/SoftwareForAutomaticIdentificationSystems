package com.gmail.maxsvynarchuk.persistence.barcode.constant;

public final class EAN8Constants {
    public static final int EAN8_MESSAGE_MIN_LENGTH = 7;
    public static final int EAN8_MESSAGE_MAX_LENGTH = 8;
    public static final int EAN8_BARCODE_LENGTH = 67;

    public static final int EAN8_LEFT_STABILIZATION_ZONE_MIN_LENGTH = 7;
    public static final int EAN8_RIGHT_STABILIZATION_ZONE_MIN_LENGTH = 7;

    public static final int[] START_STOP_PATTERN = new int[]{1, 0, 1};
    public static final int[] MIDDLE_PATTERN = new int[]{0, 1, 0, 1, 0};
    public static final int[][] A_PATTERNS = new int[][]{
            {0, 0, 0, 1, 1, 0, 1},
            {0, 0, 1, 1, 0, 0, 1},
            {0, 0, 1, 0, 0, 1, 1},
            {0, 1, 1, 1, 1, 0, 1},
            {0, 1, 0, 0, 0, 1, 1},
            {0, 1, 1, 0, 0, 0, 1},
            {0, 1, 0, 1, 1, 1, 1},
            {0, 1, 1, 1, 0, 1, 1},
            {0, 1, 1, 0, 1, 1, 1},
            {0, 0, 0, 1, 0, 1, 1}};

    public static final int[][] A_PATTERNS_B = new int[][]{
            {3, 2, 1, 1},
            {2, 2, 2, 1},
            {2, 1, 2, 2},
            {1, 4, 1, 1},
            {1, 1, 3, 2},
            {1, 2, 3, 1},
            {1, 1, 1, 4},
            {1, 3, 1, 2},
            {1, 2, 1, 3},
            {3, 1, 1, 2}};

    private EAN8Constants() {
    }
}
