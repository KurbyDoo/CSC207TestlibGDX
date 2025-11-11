package domain.entities;

public class MeleeWeapon extends Weapon {

    public MeleeWeapon(String name, int damage) {
        super(name, damage);
    }

    @Override
    public String toString() {
        return getName() + " [Melee] (Damage: " + damage + ")";
    }
}
