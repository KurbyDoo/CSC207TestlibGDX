package domain.entities;

public class RangedWeapon extends Weapon {

    private final String ammoType;

    public RangedWeapon(String name, int damage, String ammoType) {
        super(name, damage);
        this.ammoType = ammoType;
    }

    public String getAmmoType() {
        return ammoType;
    }

    @Override
    public String toString() {
        return getName() + " [Ranged] (Damage: " + damage + ", Ammo: " + ammoType + ")";
    }
}
