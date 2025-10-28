package Entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameMeshBuilder {

    public List<ModelInstance> build(World world) {
        List<ModelInstance> instances = new ArrayList<ModelInstance>();
        for (Chunk chunk : world.getChunks().values()) {
            instances.add(build(chunk));
        }
        return instances;
    }

    public ModelInstance build(Chunk chunk) {
        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        Material material = new Material(ColorAttribute.createDiffuse(Color.GRAY));
        // TODO: Replace with texture aliasing
        MeshPartBuilder meshBuilder = modelBuilder.part("chunk", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal, material);

        for (int x = 0; x < Chunk.CHUNK_SIZE; x++) {
            for (int y = 0; y < Chunk.CHUNK_SIZE; y++) {
                for (int z = 0; z < Chunk.CHUNK_SIZE; z++) {
                    BlockType blockType = chunk.getBlock(x, y, z);
                    if (blockType == BlockType.AIR) continue;

                    material = new Material(ColorAttribute.createDiffuse(new Color(0.5f, 0.5f, (float)y / Chunk.CHUNK_SIZE, 1.0f)));

                    float worldX = x + chunk.getChunkX() * Chunk.CHUNK_SIZE;
                    float worldY = y + chunk.getChunkY() * Chunk.CHUNK_SIZE;
                    float worldZ = z + chunk.getChunkZ() * Chunk.CHUNK_SIZE;

                    // Top face (y+)
                    if (y == Chunk.CHUNK_SIZE - 1 || chunk.getBlock(x, y + 1, z) == BlockType.AIR) {
//                        modelBuilder.part("top", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal, material)
                        meshBuilder
                            .rect(worldX, worldY + 1, worldZ + 1,
                                worldX + 1, worldY + 1, worldZ + 1,
                                worldX + 1, worldY + 1, worldZ,
                                worldX, worldY + 1, worldZ,
                                0, 1, 0);
                    }
                    // Bottom face (y-)
                    if (y == 0 || chunk.getBlock(x, y - 1, z) == BlockType.AIR) {
//                        modelBuilder.part("bottom", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal, material)
                        meshBuilder
                            .rect(worldX, worldY, worldZ,
                                worldX + 1, worldY, worldZ,
                                worldX + 1, worldY, worldZ + 1,
                                worldX, worldY, worldZ + 1,
                                0, -1, 0);
                    }
                    // North face (z+)
                    if (z == Chunk.CHUNK_SIZE - 1 || chunk.getBlock(x, y, z + 1) == BlockType.AIR) {
//                        modelBuilder.part("north", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal, material)
                        meshBuilder
                            .rect(worldX, worldY, worldZ + 1,
                                worldX + 1, worldY, worldZ + 1,
                                worldX + 1, worldY + 1, worldZ + 1,
                                worldX, worldY + 1, worldZ + 1,
                                0, 0, 1);
                    }
                    // South face (z-)
                    if (z == 0 || chunk.getBlock(x, y, z - 1) == BlockType.AIR) {
//                        modelBuilder.part("south", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal, material)
                        meshBuilder
                            .rect(worldX + 1, worldY, worldZ,
                                worldX, worldY, worldZ,
                                worldX, worldY + 1, worldZ,
                                worldX + 1, worldY + 1, worldZ,
                                0, 0, -1);
                    }
                    // East face (x+)
                    if (x == Chunk.CHUNK_SIZE - 1 || chunk.getBlock(x + 1, y, z) == BlockType.AIR) {
//                        modelBuilder.part("east", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal, material)
                        meshBuilder
                            .rect(worldX + 1, worldY, worldZ + 1,
                                worldX + 1, worldY, worldZ,
                                worldX + 1, worldY + 1, worldZ,
                                worldX + 1, worldY + 1, worldZ + 1,
                                1, 0, 0);
                    }
                    // West face (x-)
                    if (x == 0 || chunk.getBlock(x - 1, y, z) == BlockType.AIR) {
//                        modelBuilder.part("west", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal, material)
                        meshBuilder
                            .rect(worldX, worldY, worldZ,
                                worldX, worldY, worldZ + 1,
                                worldX, worldY + 1, worldZ + 1,
                                worldX, worldY + 1, worldZ,
                                -1, 0, 0);
                    }
                }
            }
        }
        Model model = modelBuilder.end();
        return new ModelInstance(model);
    }

}
