package org.github.turostaska.adventuregame.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.github.turostaska.adventuregame.Util;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@DiscriminatorValue("MISSION")
public class MissionAction extends Action {
    @Getter @Setter private int reward;

    public MissionAction(long timeToFinishInSeconds, int reward, int difficulty) {
        super(timeToFinishInSeconds);
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

        Rank(int baseDifficulty) { this.baseDifficulty = baseDifficulty; }
        private int baseDifficulty;

        public int getBaseDifficulty() {
            return baseDifficulty;
        }
    }

    @Getter @Setter private int difficulty;
    @Getter @Setter private Rank rank;

    @Override
    public void takeEffect(Player player) {
        player.addMoney(reward);
    }

    @Override
    public boolean carryOutAndGetIfSuccessful(Player who) {
        int diceRoll = Util.getRandomInteger(difficulty / 2, difficulty);

        return who.getStrength() >= diceRoll;
    }
}
