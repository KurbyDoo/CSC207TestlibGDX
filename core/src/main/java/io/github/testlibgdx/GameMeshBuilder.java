package io.github.testlibgdx;

import Entity.BlockType;
import Entity.Chunk;
import Entity.World;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.model.data.ModelData;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;

import java.util.ArrayList;
import java.util.List;

public class GameMeshBuilder {

    public List<ModelInstance> build(World world) {
        List<ModelInstance> instances = new ArrayList<ModelInstance>();
        for (Chunk chunk : world.getChunks().values()) {
            instances.add(build(chunk));
        }
        return instances;
    }

    private Color getBlockColor(BlockType blockType) {
        switch (blockType) {
            case GRASS:
                return new Color(0.2f, 0.8f, 0.2f, 1f); // Green
            case DIRT:
                return new Color(0.6f, 0.4f, 0.2f, 1f); // Brown
            case STONE:
                return new Color(0.5f, 0.5f, 0.5f, 1f); // Grey
            default:
                return Color.WHITE;
        }
    }

    public ModelInstance build(Chunk chunk) {
        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();

        // Process each axis for greedy meshing
        for (int axis = 0; axis < 3; axis++) {
            for (int dir = -1; dir <= 1; dir += 2) {
                greedyMeshAxis(chunk, modelBuilder, axis, dir);
            }
        }

        return new ModelInstance(modelBuilder.end());
    }

    private void greedyMeshAxis(Chunk chunk, ModelBuilder modelBuilder, int axis, int direction) {
        int u = (axis + 1) % 3;
        int v = (axis + 2) % 3;

        int[] x = new int[3];
        int[] q = new int[3];

        boolean[][] mask = new boolean[Chunk.CHUNK_SIZE][Chunk.CHUNK_SIZE];
        BlockType[][] blockMask = new BlockType[Chunk.CHUNK_SIZE][Chunk.CHUNK_SIZE];

        q[axis] = direction;

        // Iterate through each slice along the axis
        for (x[axis] = -1; x[axis] < Chunk.CHUNK_SIZE; ) {
            // Compute the mask
            int n = 0;
            for (x[v] = 0; x[v] < Chunk.CHUNK_SIZE; x[v]++) {
                for (x[u] = 0; x[u] < Chunk.CHUNK_SIZE; x[u]++) {
                    BlockType currentBlock = getBlock(chunk, x[0], x[1], x[2]);
                    BlockType neighborBlock = getBlock(chunk, x[0] + q[0], x[1] + q[1], x[2] + q[2]);

                    boolean currentSolid = currentBlock != BlockType.AIR;
                    boolean neighborSolid = neighborBlock != BlockType.AIR;

                    // Face is visible if one side is solid and the other is air
                    if (currentSolid != neighborSolid) {
                        mask[x[v]][x[u]] = direction > 0 ? currentSolid : neighborSolid;
                        blockMask[x[v]][x[u]] = direction > 0 ? currentBlock : neighborBlock;
                    } else {
                        mask[x[v]][x[u]] = false;
                        blockMask[x[v]][x[u]] = BlockType.AIR;
                    }
                }
            }

            x[axis]++;

            // Generate mesh from mask using binary greedy meshing
            n = 0;
            for (int j = 0; j < Chunk.CHUNK_SIZE; j++) {
                for (int i = 0; i < Chunk.CHUNK_SIZE; ) {
                    if (mask[j][i]) {
                        BlockType blockType = blockMask[j][i];
                        
                        // Compute width using binary operations
                        int w = 1;
                        while (i + w < Chunk.CHUNK_SIZE && mask[j][i + w] && blockMask[j][i + w] == blockType) {
                            w++;
                        }

                        // Compute height using binary operations
                        int h = 1;
                        boolean done = false;
                        while (j + h < Chunk.CHUNK_SIZE && !done) {
                            for (int k = 0; k < w; k++) {
                                if (!mask[j + h][i + k] || blockMask[j + h][i + k] != blockType) {
                                    done = true;
                                    break;
                                }
                            }
                            if (!done) {
                                h++;
                            }
                        }

                        // Clear the mask for the merged quad
                        for (int l = 0; l < h; l++) {
                            for (int k = 0; k < w; k++) {
                                mask[j + l][i + k] = false;
                            }
                        }

                        // Add the merged face
                        x[u] = i;
                        x[v] = j;

                        int[] du = new int[3];
                        int[] dv = new int[3];
                        du[u] = w;
                        dv[v] = h;

                        addQuad(modelBuilder, chunk, blockType,
                                x[0], x[1], x[2],
                                x[0] + du[0], x[1] + du[1], x[2] + du[2],
                                x[0] + du[0] + dv[0], x[1] + du[1] + dv[1], x[2] + du[2] + dv[2],
                                x[0] + dv[0], x[1] + dv[1], x[2] + dv[2],
                                axis, direction);

                        i += w;
                    } else {
                        i++;
                    }
                }
            }
        }
    }

    private BlockType getBlock(Chunk chunk, int x, int y, int z) {
        if (x < 0 || y < 0 || z < 0 || x >= Chunk.CHUNK_SIZE || y >= Chunk.CHUNK_SIZE || z >= Chunk.CHUNK_SIZE) {
            return BlockType.AIR;
        }
        return chunk.getBlock(x, y, z);
    }

    private void addQuad(ModelBuilder modelBuilder, Chunk chunk, BlockType blockType,
                         float x1, float y1, float z1,
                         float x2, float y2, float z2,
                         float x3, float y3, float z3,
                         float x4, float y4, float z4,
                         int axis, int direction) {
        
        Color color = getBlockColor(blockType);
        Material material = new Material(ColorAttribute.createDiffuse(color));
        MeshPartBuilder meshBuilder = modelBuilder.part("face", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal | Usage.ColorUnpacked, material);

        float worldX = chunk.getChunkX() * Chunk.CHUNK_SIZE;
        float worldY = chunk.getChunkY() * Chunk.CHUNK_SIZE;
        float worldZ = chunk.getChunkZ() * Chunk.CHUNK_SIZE;

        // Calculate normal based on axis and direction
        float nx = 0, ny = 0, nz = 0;
        if (axis == 0) nx = direction;
        else if (axis == 1) ny = direction;
        else if (axis == 2) nz = direction;

        // Add the quad with proper winding order
        if (direction > 0) {
            meshBuilder.rect(
                worldX + x1, worldY + y1, worldZ + z1,
                worldX + x2, worldY + y2, worldZ + z2,
                worldX + x3, worldY + y3, worldZ + z3,
                worldX + x4, worldY + y4, worldZ + z4,
                nx, ny, nz);
        } else {
            meshBuilder.rect(
                worldX + x4, worldY + y4, worldZ + z4,
                worldX + x3, worldY + y3, worldZ + z3,
                worldX + x2, worldY + y2, worldZ + z2,
                worldX + x1, worldY + y1, worldZ + z1,
                nx, ny, nz);
        }
    }

}
