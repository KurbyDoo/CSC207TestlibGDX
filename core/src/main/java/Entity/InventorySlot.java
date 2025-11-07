package Entity;

public class InventorySlot {

    private Item item;
    private int quantity;

    public InventorySlot() {
        this.item = null;
        this.quantity = 0;
    }

    public boolean isEmpty() {
        return item == null || quantity <= 0;
    }

    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    /**
     * Checks whether this slot can stack the specified item.
     * @param newItem The item to check for stacking compatibility.
     * @return True if this slot is not empty and contains the same item; false otherwise.
     */
    public boolean canStack(Item newItem) {
        return !isEmpty() && item.equals(newItem);
    }

    /**
     * Adds one instance of the specified item to this inventory slot.
     * @param newItem The item to be added to this slot.
     */
    public void addOne(Item newItem) {
        if (isEmpty()) {
            item = newItem;
            quantity = 1;
        } else {
            quantity += 1;
        }
    }

    /**
     * Removes one instance of the specified item from this slot.
     * @param target The item to remove.
     * @return True if one instance of the item was successfully removed;
     * false if the slot was empty or contained a different item
     */
    public boolean removeOne(Item target) {
        if (isEmpty() || !item.equals(target)) {
            return false;
        }

        quantity -= 1;
        if (quantity <= 0) {
            item = null;
            quantity = 0;
        }
        return true;
    }
}
