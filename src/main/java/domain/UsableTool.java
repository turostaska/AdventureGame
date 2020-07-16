package domain;

public class UsableTool extends Tool {
    public UsableTool(String name, int damage, int heal, int costToBuy, int maxQuantity) {
        super(name, damage, heal, costToBuy, maxQuantity);
    }

    @Override
    public void useInDuel(Character usedBy, Character usedAgainst) {
        super.useInDuel(usedBy, usedAgainst);
        usedBy.useUpUsableTool(this);
    }
}
