package domain;

public class NonUsableTool extends Tool {
    public NonUsableTool(String name, int ID, int damage, int heal, int costToBuy, int maxQuantity) {
        super(name, ID, damage, heal, costToBuy, maxQuantity);
    }

    @Override
    public void useTool() {
        //TODO
    }
}
