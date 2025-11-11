package domain.entities;

public abstract class Weapon extends Item {

    protected final int damage;

    public Weapon(String name, int damage) {
        super(name, false); // weapons aren't stackable
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public String toString() {
        return getName() + " (Damage: " + damage + ")";
    }
}
