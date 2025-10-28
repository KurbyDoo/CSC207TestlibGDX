package Entity;

public class Chunk {
    public static final int CHUNK_SIZE = 16;
    private BlockType[][][] blocks = new BlockType[CHUNK_SIZE][CHUNK_SIZE][CHUNK_SIZE];
    private final int chunkX, chunkY, chunkZ;
    public Chunk(int chunkX, int chunkY, int chunkZ) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.chunkY = chunkY;



        // fill with stone;
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int z = 0; z < CHUNK_SIZE; z++) {
                double worldX = x + chunkX * CHUNK_SIZE;
                double worldZ = z + chunkZ * CHUNK_SIZE;
                double perlinNoise = PerlinNoise.octavePerlin(worldX * 0.025, 0, worldZ * 0.025, 8, 0.2);
                int height = (int)(perlinNoise * CHUNK_SIZE * CHUNK_SIZE);
                for (int h = 0; h < CHUNK_SIZE; h++) {
                    int worldY = h + chunkY * CHUNK_SIZE;
                    blocks[x][h][z] = (worldY <= height) ? BlockType.STONE : BlockType.AIR;
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
