package domain;

public class UsableTool extends Tool {
    public UsableTool(String name, int ID, int damage, int heal, int costToBuy, int maxQuantity) {
        super(name, ID, damage, heal, costToBuy, maxQuantity);
    }

    @Override
    public void useTool() {
        //TODO
    }
}
