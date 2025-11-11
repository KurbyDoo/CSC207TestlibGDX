package domain.entities;

public class ItemTypes {

    private ItemTypes() {}

    public static final Item PISTOL_BULLET = new Item("Pistol Bullet", true);
    public static final Item RIFLE_BULLET = new Item("Rifle Bullet", true);

    // Fuel Items
    public static final FuelItem WOOD_LOG   = new FuelItem("Wood Log", true, 10);
    public static final FuelItem COAL       = new FuelItem("Coal", true, 25);
    public static final FuelItem OIL_BARREL = new FuelItem("Oil Barrel", false, 50);

    // Melee Weapons
    public static final MeleeWeapon BASEBALL_BAT = new MeleeWeapon("Baseball Bat", 10);
    public static final MeleeWeapon KATANA   = new MeleeWeapon("Katana", 20);
    public static final MeleeWeapon TOMAHAWK_AXE   = new MeleeWeapon("Tomahawk Axe", 30);
    public static final MeleeWeapon MASTER_SWORD   = new MeleeWeapon("Master Sword", 50);

    // Ranged Weapons
    public static final RangedWeapon RUSTY_PISTOL   = new RangedWeapon("Rusty Pistol", 15, "Pistol Bullet");
    public static final RangedWeapon COMBAT_PISTOL  = new RangedWeapon("Combat Pistol", 25, "Pistol Bullet");
    public static final RangedWeapon TACTICAL_RIFLE = new RangedWeapon("Tactical Rifle", 35, "Rifle Bullet");
    public static final RangedWeapon GOLDEN_RIFLE   = new RangedWeapon("Golden Rifle", 45, "Rifle Bullet");
}
