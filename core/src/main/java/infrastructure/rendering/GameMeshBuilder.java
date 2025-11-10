package infrastructure.rendering;

import domain.entities.BlockType;
import domain.entities.Chunk;
import domain.entities.World;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;

import java.util.ArrayList;
import java.util.List;

public class GameMeshBuilder {
    private final World world;
    public GameMeshBuilder(World world) {
        this.world = world;
    }

    public List<ModelInstance> build(World world) {
        List<ModelInstance> instances = new ArrayList<>();
        for (Chunk chunk : world.getChunks().values()) {
            instances.add(build(chunk));
        }
        return instances;
    }

    private Material getBlockMaterial(BlockType blockType) {
        switch (blockType) {
            case GRASS:
                return new Material(ColorAttribute.createDiffuse(new Color(0.2f, 0.8f, 0.2f, 1f))); // Green
            case DIRT:
                return new Material(ColorAttribute.createDiffuse(new Color(0.6f, 0.4f, 0.2f, 1f))); // Brown
            case STONE:
                return new Material(ColorAttribute.createDiffuse(new Color(0.5f, 0.5f, 0.5f, 1f))); // Grey
            default:
                return new Material(ColorAttribute.createDiffuse(Color.GRAY));
        }
    }

    public ModelInstance build(Chunk chunk) {
        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();

        buildType(chunk, modelBuilder, BlockType.GRASS);
        buildType(chunk, modelBuilder, BlockType.DIRT);
        buildType(chunk, modelBuilder, BlockType.STONE);

        return new ModelInstance(modelBuilder.end());
    }

    private void buildType(Chunk chunk, ModelBuilder modelBuilder, BlockType type) {
        Material material = getBlockMaterial(type);
        MeshPartBuilder meshBuilder = modelBuilder.part(type.toString(), GL20.GL_TRIANGLES, Usage.Position | Usage.Normal, material);

        for (int x = 0; x < Chunk.CHUNK_SIZE; x++) {
            for (int y = 0; y < Chunk.CHUNK_SIZE; y++) {
                for (int z = 0; z < Chunk.CHUNK_SIZE; z++) {
                    if (chunk.getBlock(x, y, z) == type) {
                        buildBlockFaces(meshBuilder, chunk, x, y, z);
                    }
                }
            }
        }
    }

    private void buildBlockFaces(MeshPartBuilder meshBuilder, Chunk chunk, int x, int y, int z) {
        int worldX = x + chunk.getChunkX() * Chunk.CHUNK_SIZE;
        int worldY = y + chunk.getChunkY() * Chunk.CHUNK_SIZE;
        int worldZ = z + chunk.getChunkZ() * Chunk.CHUNK_SIZE;

        // Top face (y+)
        if (world.getBlock(worldX, worldY + 1, worldZ) == BlockType.AIR) {
            meshBuilder
                .rect(worldX, worldY + 1, worldZ + 1,
                    worldX + 1, worldY + 1, worldZ + 1,
                    worldX + 1, worldY + 1, worldZ,
                    worldX, worldY + 1, worldZ,
                    0, 1, 0);
        }
        // Bottom face (y-)
        if (world.getBlock(worldX, worldY - 1, worldZ) == BlockType.AIR) {
            meshBuilder
                .rect(worldX, worldY, worldZ,
                    worldX + 1, worldY, worldZ,
                    worldX + 1, worldY, worldZ + 1,
                    worldX, worldY, worldZ + 1,
                    0, -1, 0);
        }
//         North face (z+)
        if (world.getBlock(worldX, worldY, worldZ + 1) == BlockType.AIR) {
            meshBuilder
                .rect(worldX, worldY, worldZ + 1,
                    worldX + 1, worldY, worldZ + 1,
                    worldX + 1, worldY + 1, worldZ + 1,
                    worldX, worldY + 1, worldZ + 1,
                    0, 0, 1);
        }
        // South face (z-)
        if (world.getBlock(worldX, worldY, worldZ - 1) == BlockType.AIR) {
            meshBuilder
                .rect(worldX + 1, worldY, worldZ,
                    worldX, worldY, worldZ,
                    worldX, worldY + 1, worldZ,
                    worldX + 1, worldY + 1, worldZ,
                    0, 0, -1);
        }
        // East face (x+)
        if (world.getBlock(worldX + 1, worldY, worldZ) == BlockType.AIR) {
            meshBuilder
                .rect(worldX + 1, worldY, worldZ + 1,
                    worldX + 1, worldY, worldZ,
                    worldX + 1, worldY + 1, worldZ,
                    worldX + 1, worldY + 1, worldZ + 1,
                    1, 0, 0);
        }
        // West face (x-)
        if (world.getBlock(worldX - 1, worldY, worldZ) == BlockType.AIR) {
            meshBuilder
                .rect(worldX, worldY, worldZ,
                    worldX, worldY, worldZ + 1,
                    worldX, worldY + 1, worldZ + 1,
                    worldX, worldY + 1, worldZ,
                    -1, 0, 0);
        }
    }

}
