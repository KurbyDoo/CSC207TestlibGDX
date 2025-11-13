package domain.entities;

import java.util.Objects;

public class Item {

    private final String name;
    private final boolean stackable;

    public Item(String name, boolean stackable) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name must not be empty");
        }

        this.name = name;
        this.stackable = stackable;
    }

    public String getName() {
        return name;
    }

    public boolean isStackable() {
        return stackable;
    }

    /**
     * Determines whether this item is equal to another object.
     * @param o The object to compare with this item.
     * @return True if the specified object is an Item with the same ID; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return name.equals(item.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
