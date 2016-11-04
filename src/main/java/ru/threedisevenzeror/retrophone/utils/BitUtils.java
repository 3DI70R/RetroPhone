package ru.threedisevenzeror.retrophone.utils;

/**
 * Created by Three on 04.11.2016.
 */
public class BitUtils {

    public static boolean isBitSet(int value, int mask) {
        return (((value >>> mask) & 1) != 0);
    }
}
