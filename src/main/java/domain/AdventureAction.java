package domain;

import java.util.List;

public class AdventureAction extends Action {
    public AdventureAction(int ID, long timeToFinishInSeconds, List<Technique> techniqueRewards, List<Tool> toolRewards) {
        super(ID, timeToFinishInSeconds);
        this.techniqueRewards = techniqueRewards;
        this.toolRewards = toolRewards;
    }

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
        //todo
        return false;
    }


}
