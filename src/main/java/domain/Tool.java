package domain;

public abstract class Tool extends Buyable {
    private int maxQuantity;

    public Tool(String name, int ID, int damage, int heal, int costToBuy, int maxQuantity) {
        super(name, ID, damage, heal, costToBuy);
        this.maxQuantity = maxQuantity;
    }

    public abstract void useTool();
}
