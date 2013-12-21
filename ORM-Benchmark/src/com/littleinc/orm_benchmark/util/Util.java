package com.littleinc.orm_benchmark.util;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Util {

    private static final char[] FILENAME_CHARS = new char[62];

    private static final Random sRandom = new Random();

    static {
        for (int idx = 0; idx < 10; ++idx)
            FILENAME_CHARS[idx] = (char) ('0' + idx);
        for (int idx = 10; idx < 36; ++idx)
            FILENAME_CHARS[idx] = (char) ('a' + idx - 10);
        for (int idx = 36; idx < 62; ++idx)
            FILENAME_CHARS[idx] = (char) ('A' + idx - 36);
    }

    /**
     * Get a random String with the provided length, containing only characters
     * in {@link #FILENAME_CHARS}, e.g. "2pKfEz".
     */
    public static String getRandomString(int length) {
        char[] buf = new char[length];

        for (int idx = 0; idx < buf.length; ++idx) {
            buf[idx] = FILENAME_CHARS[sRandom.nextInt(FILENAME_CHARS.length)];
        }

        return new String(buf);
    }

    /**
     * @return millis
     */
    public static String formatElapsedTime(long nanos) {
        return String.valueOf(TimeUnit.NANOSECONDS.toMillis(nanos));
    }
}
