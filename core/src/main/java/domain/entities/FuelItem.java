package domain.entities;

public class FuelItem extends Item {

    private final int fuelValue;

    public FuelItem(String name, boolean stackable, int fuelValue) {
        super(name, stackable);
        this.fuelValue = fuelValue;
    }

    public int getFuelValue() {
        return fuelValue;
    }

    @Override
    public String toString() {
        return getName() + " - Fuel: " + fuelValue;
    }
}
