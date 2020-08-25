package org.github.turostaska.adventuregame;

import org.github.turostaska.adventuregame.domain.MissionAction;

import java.util.concurrent.ThreadLocalRandom;

public final class Util {
    private Util() {}

    public static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    public static int getRandomInteger(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static String formatTime(long seconds) {
        String time = "";
        if (seconds > 3600) {
            time = time.concat(seconds / 3600 + " h ");
            seconds = seconds % 3600;
        }
        if (seconds > 60) {
            time = time.concat(seconds / 60 + " min ");
            seconds = seconds % 60;
        }
        if (seconds != 0)
            time = time.concat(seconds + " sec");

        return time;
    }

    public static MissionAction.Rank getRankFromDifficulty(int difficulty) {
        if (difficulty < MissionAction.Rank.D.getBaseDifficulty())
            return MissionAction.Rank.E;
        if (difficulty < MissionAction.Rank.C.getBaseDifficulty())
            return MissionAction.Rank.D;
        if (difficulty < MissionAction.Rank.B.getBaseDifficulty())
            return MissionAction.Rank.C;
        if (difficulty < MissionAction.Rank.A.getBaseDifficulty())
            return MissionAction.Rank.B;
        if (difficulty < MissionAction.Rank.S.getBaseDifficulty())
            return MissionAction.Rank.A;
        return MissionAction.Rank.S;
    }
}
