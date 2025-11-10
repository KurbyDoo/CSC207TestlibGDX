package domain.entities;

public class InventorySlot {

    private Item item;
    private int quantity;

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
     * Adds one instance of the specified item to this inventory slot.
     * Do nothing if unable to add the item to this slot.
     * @param newItem The item to be added to this slot.
     */
    public void addOne(Item newItem) {
        if (isEmpty()) {
            item = newItem;
            quantity = 1;
        }
        if (item.equals(newItem) && item.isStackable()) {
            quantity++;
        }
    }

    /**
     * Removes one instance of the specified item from this inventory slot.
     * Do nothing if this slot is empty.
     */
    public void removeOne() {
        if (isEmpty()) return;

        quantity--;
        if (quantity <= 0) {
            item = null;
            quantity = 0;
        }
    }
}
