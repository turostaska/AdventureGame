package domain;

public abstract class Tool extends Buyable {
    private int maxQuantity;

    public Tool(String name, int damage, int heal, int costToBuy, int maxQuantity) {
        super(name, damage, heal, costToBuy);
        this.maxQuantity = maxQuantity;
    }

    public abstract void useTool();
}
