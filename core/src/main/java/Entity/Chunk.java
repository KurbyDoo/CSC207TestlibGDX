package Entity;

public class Chunk {
    public static final int CHUNK_SIZE = 16;
    private static boolean enableCaves = false;

    private BlockType[][][] blocks = new BlockType[CHUNK_SIZE][CHUNK_SIZE][CHUNK_SIZE];
    private final int chunkX, chunkY, chunkZ;
    private boolean isUnderground;
    public Chunk(int chunkX, int chunkY, int chunkZ) {
        this.chunkX = chunkX;
        this.chunkY = chunkY;
        this.chunkZ = chunkZ;
    }

    public void generate() {
        float scaleFactor = 0.05f;
        double valleyScale = (double) 1.0f / (CHUNK_SIZE * CHUNK_SIZE);

        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int z = 0; z < CHUNK_SIZE; z++) {
                int worldX = x + chunkX * CHUNK_SIZE;
                int worldZ = z + chunkZ * CHUNK_SIZE;
                double perlinNoise = PerlinNoise.octavePerlin(worldX * scaleFactor, 0, worldZ * scaleFactor, 4, 0.6);
                for (int h = 0; h < CHUNK_SIZE; h++) {
                    int worldY = h + chunkY * CHUNK_SIZE;
                    int height = (int)(perlinNoise * worldZ * worldZ * valleyScale);
                    if (worldY > height) {
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

        // generate rails
        if (chunkZ == 0) {

        }
    }

    public BlockType getBlock(int x, int y, int z) {
        try {
            return blocks[x][y][z];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid block access!");
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
