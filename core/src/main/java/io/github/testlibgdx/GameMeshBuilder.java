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
                return Color.GRAY;
        }
    }

    public ModelInstance build(Chunk chunk) {
        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();

        // --- Pass 1: Build all GRASS blocks ---
        Material grassMaterial = new Material(ColorAttribute.createDiffuse(getBlockColor(BlockType.GRASS)));
        MeshPartBuilder meshBuilder = modelBuilder.part("grass", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal, grassMaterial);

        for (int x = 0; x < Chunk.CHUNK_SIZE; x++) {
            for (int y = 0; y < Chunk.CHUNK_SIZE; y++) {
                for (int z = 0; z < Chunk.CHUNK_SIZE; z++) {
                    if (chunk.getBlock(x, y, z) == BlockType.GRASS) {
                        buildBlockFaces(meshBuilder, chunk, x, y, z);
                    }
                }
            }
        }

        // --- Pass 2: Build all DIRT blocks ---
        Material dirtMaterial = new Material(ColorAttribute.createDiffuse(getBlockColor(BlockType.DIRT)));
        meshBuilder = modelBuilder.part("dirt", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal, dirtMaterial);

        for (int x = 0; x < Chunk.CHUNK_SIZE; x++) {
            for (int y = 0; y < Chunk.CHUNK_SIZE; y++) {
                for (int z = 0; z < Chunk.CHUNK_SIZE; z++) {
                    if (chunk.getBlock(x, y, z) == BlockType.DIRT) {
                        buildBlockFaces(meshBuilder, chunk, x, y, z);
                    }
                }
            }
        }

        // --- Pass 3: Build all STONE blocks ---
        Material stoneMaterial = new Material(ColorAttribute.createDiffuse(getBlockColor(BlockType.STONE)));
        meshBuilder = modelBuilder.part("stone", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal, stoneMaterial);

        for (int x = 0; x < Chunk.CHUNK_SIZE; x++) {
            for (int y = 0; y < Chunk.CHUNK_SIZE; y++) {
                for (int z = 0; z < Chunk.CHUNK_SIZE; z++) {
                    if (chunk.getBlock(x, y, z) == BlockType.STONE) {
                        buildBlockFaces(meshBuilder, chunk, x, y, z);
                    }
                }
            }
        }

        return new ModelInstance(modelBuilder.end());
    }

    private void buildBlockFaces(MeshPartBuilder meshBuilder, Chunk chunk, int x, int y, int z) {
        int worldX = x + chunk.getChunkX() * Chunk.CHUNK_SIZE;
        int worldY = y + chunk.getChunkY() * Chunk.CHUNK_SIZE;
        int worldZ = z + chunk.getChunkZ() * Chunk.CHUNK_SIZE;

        // Top face (y+)
        if (y == Chunk.CHUNK_SIZE - 1 || chunk.getBlock(x, y + 1, z) == BlockType.AIR) {
            meshBuilder
                .rect(worldX, worldY + 1, worldZ + 1,
                    worldX + 1, worldY + 1, worldZ + 1,
                    worldX + 1, worldY + 1, worldZ,
                    worldX, worldY + 1, worldZ,
                    0, 1, 0);
        }
        // Bottom face (y-)
        if (y == 0 || chunk.getBlock(x, y - 1, z) == BlockType.AIR) {
            meshBuilder
                .rect(worldX, worldY, worldZ,
                    worldX + 1, worldY, worldZ,
                    worldX + 1, worldY, worldZ + 1,
                    worldX, worldY, worldZ + 1,
                    0, -1, 0);
        }
        // North face (z+)
        if (z == Chunk.CHUNK_SIZE - 1 || chunk.getBlock(x, y, z + 1) == BlockType.AIR) {
            meshBuilder
                .rect(worldX, worldY, worldZ + 1,
                    worldX + 1, worldY, worldZ + 1,
                    worldX + 1, worldY + 1, worldZ + 1,
                    worldX, worldY + 1, worldZ + 1,
                    0, 0, 1);
        }
        // South face (z-)
        if (z == 0 || chunk.getBlock(x, y, z - 1) == BlockType.AIR) {
            meshBuilder
                .rect(worldX + 1, worldY, worldZ,
                    worldX, worldY, worldZ,
                    worldX, worldY + 1, worldZ,
                    worldX + 1, worldY + 1, worldZ,
                    0, 0, -1);
        }
        // East face (x+)
        if (x == Chunk.CHUNK_SIZE - 1 || chunk.getBlock(x + 1, y, z) == BlockType.AIR) {
            meshBuilder
                .rect(worldX + 1, worldY, worldZ + 1,
                    worldX + 1, worldY, worldZ,
                    worldX + 1, worldY + 1, worldZ,
                    worldX + 1, worldY + 1, worldZ + 1,
                    1, 0, 0);
        }
        // West face (x-)
        if (x == 0 || chunk.getBlock(x - 1, y, z) == BlockType.AIR) {
            meshBuilder
                .rect(worldX, worldY, worldZ,
                    worldX, worldY, worldZ + 1,
                    worldX, worldY + 1, worldZ + 1,
                    worldX, worldY + 1, worldZ,
                    -1, 0, 0);
        }
    }

}
