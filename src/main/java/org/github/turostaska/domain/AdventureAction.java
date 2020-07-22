package org.github.turostaska.domain;

import org.github.turostaska.Util;

import java.util.List;

public class AdventureAction extends Action {
    public AdventureAction(int ID, long timeToFinishInSeconds, List<Technique> techniqueRewards, List<Tool> toolRewards, int difficulty) {
        super(ID, timeToFinishInSeconds);
        this.techniqueRewards = techniqueRewards;
        this.toolRewards = toolRewards;
        this.difficulty = difficulty;
    }

    private int difficulty;
    private List<Technique> techniqueRewards;
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
