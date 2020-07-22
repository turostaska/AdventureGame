package org.github.turostaska.domain;

public class MissionAction extends Action {
    private int reward;

    public MissionAction(int ID, long timeToFinishInSeconds, int reward, int difficulty) {
        super(ID, timeToFinishInSeconds);
        this.reward = reward;
        this.difficulty = difficulty;

        this.rank = getRankFromDifficulty(difficulty);
    }

    private static Rank getRankFromDifficulty(int difficulty) {
        if (difficulty < Rank.D.getBaseDifficulty())
            return Rank.E;
        if (difficulty < Rank.C.getBaseDifficulty())
            return Rank.D;
        if (difficulty < Rank.B.getBaseDifficulty())
            return Rank.C;
        if (difficulty < Rank.A.getBaseDifficulty())
            return Rank.B;
        if (difficulty < Rank.S.getBaseDifficulty())
            return Rank.A;
        return Rank.S;
    }

    public enum Rank {
        E(0), D(50), C(200), B(400), A(600), S(1000);

        private Rank(int baseDifficulty) { this.baseDifficulty = baseDifficulty; }
        private int baseDifficulty;

        public int getBaseDifficulty() {
            return baseDifficulty;
        }
    };

    private int difficulty;
    private Rank rank;

    public int getReward() {
        return reward;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public Rank getRank() {
        return rank;
    }

    @Override
    public void takeEffect(Player player) {
        player.addMoney(reward);
    }

    @Override
    public boolean carryOutAndGetIfSuccessful(Player who) {
        int diceRoll = Util.getRandomInteger(difficulty / 2, difficulty);

        if (who.getStrength() >= diceRoll)
            return true;

        return false;
    }
}
