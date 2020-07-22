package org.github.turostaska.domain;

import java.util.concurrent.ThreadLocalRandom;

public final class Util {
    private Util() {}

    public static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    public static int getRandomInteger(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
