package domain;

public class NonUsableTool extends Tool {
    public NonUsableTool(String name, int damage, int heal, int costToBuy, int maxQuantity) {
        super(name, damage, heal, costToBuy, maxQuantity);
    }

//    @Override
//    public void useInDuel(Character usedBy, Character usedAgainst) {
//        super.useTool(usedBy, usedAgainst);
//    }
}
