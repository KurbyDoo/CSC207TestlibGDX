package Entity;

public class ChunkPosition {
    public final int x, y, z;

    public ChunkPosition(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChunkPosition)) return false;
        ChunkPosition other = (ChunkPosition) o;
        return x == other.x && y == other.y && z == other.z;
    }

    @Override
    public int hashCode() {
        return 31 * (31 * x + y) + z;
    }
}
