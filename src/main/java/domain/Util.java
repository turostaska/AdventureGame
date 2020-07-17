package domain;

public final class Util {
    private Util() {}

    public static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
}
