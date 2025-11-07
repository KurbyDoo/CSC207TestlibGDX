package Entity;

import java.util.Objects;

public class Item {

    private final String id;
    private final String displayName;

    public Item(String id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
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
        return id.equals(item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return displayName + " (" + id + ")";
    }
}
