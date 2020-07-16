package domain;

public abstract class Buyable {
    protected String name;
    protected int ID;
    protected int damage;
    protected int heal;
    protected int costToBuy;

    public String getName() {
        return name;
    }

    public int getID() {
        return ID;
    }

    public int getDamage() {
        return damage;
    }

    public int getHeal() {
        return heal;
    }

    public int getCostToBuy() {
        return costToBuy;
    }

    public Buyable(String name, int damage, int heal, int costToBuy) {
        this.name = name;
        this.damage = damage;
        this.heal = heal;
        this.costToBuy = costToBuy;
    }
}
