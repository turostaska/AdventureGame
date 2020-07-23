package org.github.turostaska.domain;

import org.github.turostaska.Util;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
@DiscriminatorValue("ADVENTURE")
public class AdventureAction extends Action {
    public AdventureAction(long timeToFinishInSeconds, List<Technique> techniqueRewards, List<Tool> toolRewards, int difficulty) {
        super(timeToFinishInSeconds);
        this.techniqueRewards = techniqueRewards;
        this.toolRewards = toolRewards;
        this.difficulty = difficulty;
    }

    private int difficulty;
    @ManyToMany
    private List<Technique> techniqueRewards;

    @ManyToMany
    private List<Tool> toolRewards;

    public List<Technique> getTechniqueRewards() {
        return techniqueRewards;
    }
    public List<Tool> getToolRewards() {
        return toolRewards;
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

        if (who.getStrength() >= diceRoll)
            return true;

        return false;
    }


}
