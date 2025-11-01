package domain.entities;

public class Chunk {
    public static final int CHUNK_SIZE = 16;
    private BlockType[][][] blocks = new BlockType[CHUNK_SIZE][CHUNK_SIZE][CHUNK_SIZE];
    private final int chunkX, chunkY, chunkZ;
    public Chunk(int chunkX, int chunkY, int chunkZ) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.chunkY = chunkY;
    }

    public BlockType getBlock(int x, int y, int z) {
        try {
            return blocks[x][y][z];
        } catch (ArrayIndexOutOfBoundsException e) {
            return BlockType.AIR;
        }
    }

    public void setBlock(int x, int y, int z, BlockType type) {
        blocks[x][y][z] = type;
    }

    public int getChunkX() {
        return chunkX;
    }

    public int getChunkY() {
        return chunkY;
    }

    public int getChunkZ() {
        return chunkZ;
    }
}
