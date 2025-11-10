package domain.entities;

import com.badlogic.gdx.math.Vector3;

public class Chunk {
    public static final int CHUNK_SIZE = 16;
    private static boolean enableCaves = false;

    private BlockType[][][] blocks = new BlockType[CHUNK_SIZE][CHUNK_SIZE][CHUNK_SIZE];
    private final Vector3 chunkCoordinates;
    private boolean isUnderground;
    public Chunk(int chunkX, int chunkY, int chunkZ) {
        chunkCoordinates = new Vector3(chunkX, chunkY, chunkZ);
    }

    public Chunk(Vector3 chunkCoordinates) {
        this.chunkCoordinates = chunkCoordinates;
    }


    public BlockType getBlock(int x, int y, int z) {
        return blocks[x][y][z];
    }

    public boolean getIsUnderground() {
        return isUnderground;
    }

    public void setBlock(int x, int y, int z, BlockType type) {
        blocks[x][y][z] = type;
    }

    public int getChunkX() {
        return (int) chunkCoordinates.x;
    }

    public int getChunkY() {
        return (int) chunkCoordinates.y;
    }

    public int getChunkZ() {
        return (int) chunkCoordinates.z;
    }

    public int getChunkWorldX() {
        return getChunkX() * CHUNK_SIZE;
    }

    public int getChunkWorldY() {
        return getChunkY() * CHUNK_SIZE;
    }

    public int getChunkWorldZ() {
        return getChunkZ() * CHUNK_SIZE;
    }

    public static void setEnableCaves(boolean value) {
        enableCaves = value;
    }
}
