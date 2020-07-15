package domain;

public class Technique extends Buyable {
    private int costToCast;

    public Technique(String name, int ID, int damage, int heal, int costToBuy, int costToCast) {
        super(name, ID, damage, heal, costToBuy);
        this.costToCast = costToCast;
    }

    public int getCostToCast() {
        return costToCast;
    }


}
