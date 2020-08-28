package org.github.turostaska.adventuregame.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.github.turostaska.adventuregame.Util;
import org.github.turostaska.adventuregame.service.ICharacterService;

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
        return Util.getRankFromDifficulty(difficulty);
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
    public boolean carryOutAndGetIfSuccessful(Player who, ICharacterService characterService) {
        int diceRoll = Util.getRandomInteger(difficulty / 2, difficulty);

        return who.getStrength() >= diceRoll;
    }

    @Override
    public String description() {
        return rank.name() + " level mission";
    }
}
