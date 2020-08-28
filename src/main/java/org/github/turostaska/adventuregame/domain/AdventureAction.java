package org.github.turostaska.adventuregame.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.github.turostaska.adventuregame.Util;
import org.github.turostaska.adventuregame.service.ICharacterService;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
@DiscriminatorValue("ADVENTURE")
@NoArgsConstructor
public class AdventureAction extends Action {
    @Getter @Setter private int difficulty;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    @Getter @Setter
    private List<Technique> techniqueRewards;

    @LazyCollection(LazyCollectionOption.FALSE)
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
    public boolean carryOutAndGetIfSuccessful(Player who, ICharacterService characterService) {
        int diceRoll = Util.getRandomInteger((int)(difficulty * 0.75), difficulty);

        return who.getStrength() >= diceRoll;
    }

    @Override
    public String description() {
        return "Adventure";
    }


}
