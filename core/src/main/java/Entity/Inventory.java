package Entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Inventory {

    private final List<InventorySlot> slots = new ArrayList<>();

    public Inventory() {
        slots.add(new InventorySlot());
    }

    /** for debugging purposes */
    public List<InventorySlot> getSlots() {
        return Collections.unmodifiableList(slots);
    }

    /**
     * Adds the specified item to the inventory.
     * @param item The item to add to the inventory.
     */
    public void addItem(Item item) {
        for (InventorySlot slot : slots) {
            if (slot.canStack(item)) {
                slot.addOne(item);
                return;
            }
        }

        for (InventorySlot slot : slots) {
            if (slot.isEmpty()) {
                slot.addOne(item);
                return;
            }
        }

        InventorySlot newSlot = new InventorySlot();
        newSlot.addOne(item);
        slots.add(newSlot);
    }

    /**
     * Removes one instance of the specified item from the inventory.
     * @param item The item to remove.
     * @return True if one instance of the item was successfully removed; false otherwise.
     */
    public boolean removeItem(Item item) {
        for (InventorySlot slot : slots) {
            if (slot.removeOne(item)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Counts the total number of instances of the specified item in the inventory.
     * @param item The item to count.
     * @return The total quantity of the specified item across slots.
     */
    public int countItem(Item item) {
        int total = 0;
        for (InventorySlot slot : slots) {
            if (!slot.isEmpty() && item.equals(slot.getItem())) {
                total += slot.getQuantity();
            }
        }
        return total;
    }
}


