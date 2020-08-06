package org.github.turostaska.adventuregame.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.github.turostaska.adventuregame.Util;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
@DiscriminatorValue("ADVENTURE")
@NoArgsConstructor
public class AdventureAction extends Action {
    @Getter @Setter private int difficulty;

    @ManyToMany
    @Getter @Setter
    private List<Technique> techniqueRewards;

    @ManyToMany
    @Getter @Setter
    private List<Tool> toolRewards;

    public AdventureAction(long timeToFinishInSeconds, List<Technique> techniqueRewards, List<Tool> toolRewards, int difficulty) {
        super(timeToFinishInSeconds);
        this.techniqueRewards = techniqueRewards;
        this.toolRewards = toolRewards;
        this.difficulty = difficulty;
    }

    @Override
    public void takeEffect(Player player) {
        for (Technique technique : techniqueRewards)
            player.tryToLearnTechnique(technique);

        for (Tool tool : toolRewards)
            player.obtainTool(tool);
    }

    @Override
    public boolean carryOutAndGetIfSuccessful(Player who) {
        int diceRoll = Util.getRandomInteger((int)(difficulty * 0.75), difficulty);

        return who.getStrength() >= diceRoll;
    }


}