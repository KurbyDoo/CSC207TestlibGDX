package Entity;

public class Chunk {
    public static final int CHUNK_SIZE = 16;
    private static boolean enableCaves = false;

    private BlockType[][][] blocks = new BlockType[CHUNK_SIZE][CHUNK_SIZE][CHUNK_SIZE];
    private final int chunkX, chunkY, chunkZ;
    private boolean isUnderground;
    public Chunk(int chunkX, int chunkY, int chunkZ) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.chunkY = chunkY;
    }

    public void generate() {
        float scaleFactor = 0.01f;

        // DO a quick check to see if we expect the chunk to be fully underground
        double chunkPerlinNoise = PerlinNoise.octavePerlin(chunkX * CHUNK_SIZE * CHUNK_SIZE * scaleFactor, 0, chunkZ * CHUNK_SIZE * CHUNK_SIZE * scaleFactor, 4, 0.5);
        int expectedHeight = (int)(chunkPerlinNoise * CHUNK_SIZE) / 4 + CHUNK_SIZE / 2;
        isUnderground = (chunkY < expectedHeight - 1);

        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int z = 0; z < CHUNK_SIZE; z++) {
                int worldX = x + chunkX * CHUNK_SIZE;
                int worldZ = z + chunkZ * CHUNK_SIZE;
                double perlinNoise = PerlinNoise.octavePerlin(worldX * 0.01, 0, worldZ * 0.01, 4, 0.5);
                for (int h = 0; h < CHUNK_SIZE; h++) {
                    int worldY = h + chunkY * CHUNK_SIZE;
                    double perlinNoiseCave = enableCaves ? PerlinNoise.octavePerlin(worldX * 0.02, worldY * 0.02, worldZ * 0.02, 4, 0.6) : 0;
                    int height = (int)(perlinNoise * CHUNK_SIZE * CHUNK_SIZE) / 4 + CHUNK_SIZE * CHUNK_SIZE / 2;
                    if (worldY > height) {
                        blocks[x][h][z] = BlockType.AIR;
                    } else if (enableCaves && perlinNoiseCave > 0.65) {
                        blocks[x][h][z] = BlockType.AIR;
                    } else if (worldY == height) {
                        blocks[x][h][z] = BlockType.GRASS;
                    } else if (worldY >= height - 3) {
                        blocks[x][h][z] = BlockType.DIRT;
                    } else {
                        blocks[x][h][z] = BlockType.STONE;
                    }
                }
            }
        }
    }

    public BlockType getBlock(int x, int y, int z) {
        try {
            return blocks[x][y][z];
        } catch (ArrayIndexOutOfBoundsException e) {
            return BlockType.AIR;
        }
    }

    public boolean getIsUnderground() {
        return isUnderground;
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

    public static void setEnableCaves(boolean value) {
        enableCaves = value;
    }
}
