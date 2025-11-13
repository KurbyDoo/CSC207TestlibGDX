package infrastructure.rendering;

import com.badlogic.gdx.graphics.g3d.Model;
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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.*;

import java.util.ArrayList;
import java.util.List;

public class GameMeshBuilder {
    private final Vector3 p1 = new Vector3();
    private final Vector3 p2 = new Vector3();
    private final Vector3 p3 = new Vector3();
    private final World world;

    public GameMeshBuilder(World world) {
        this.world = world;
    }

    ModelBuilder modelBuilder;
    ModelInstance modelInstance;

//    public List<ModelInstance> build(World world) {
//        List<ModelInstance> instances = new ArrayList<>();
//        for (Chunk chunk : world.getChunks().values()) {
//            instances.add(build(chunk).getModel());
//            instances.add(build(chunk));
//        }
//        return instances;
//    }

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

    public ChunkMeshData build(Chunk chunk) {
        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        btTriangleMesh triangleMesh = new btTriangleMesh();
        buildType(chunk, triangleMesh, modelBuilder, BlockType.GRASS);
        buildType(chunk, triangleMesh, modelBuilder, BlockType.DIRT);
        buildType(chunk, triangleMesh, modelBuilder, BlockType.STONE);

        btBvhTriangleMeshShape bvhTriangle = null;

        // THIS CRASHES IF YOU PASS AN EMPTY MESH
        if (triangleMesh.getNumTriangles() != 0) {
            bvhTriangle = new btBvhTriangleMeshShape(triangleMesh, true);
        }

        Model completeModel = modelBuilder.end();

        ChunkMeshData chunkMeshData = new ChunkMeshData(completeModel, triangleMesh, bvhTriangle);
//        chunkMeshData.generateGameObject(completeModel, "bvhTriangle");

        return chunkMeshData;
    }

    private void buildType(Chunk chunk, btTriangleMesh triangleMesh, ModelBuilder modelBuilder, BlockType type) {
        Material material = getBlockMaterial(type);
        MeshPartBuilder meshBuilder = modelBuilder.part(type.toString(), GL20.GL_TRIANGLES, Usage.Position | Usage.Normal, material);

        for (int x = 0; x < Chunk.CHUNK_SIZE; x++) {
            for (int y = 0; y < Chunk.CHUNK_SIZE; y++) {
                for (int z = 0; z < Chunk.CHUNK_SIZE; z++) {
                    if (chunk.getBlock(x, y, z) == type) {
                        buildBlockFaces(meshBuilder, triangleMesh, chunk, x, y, z);
                    }
                }
            }
        }
    }


    private void buildBlockFaces(MeshPartBuilder meshBuilder, btTriangleMesh triangleMesh, Chunk chunk, int x, int y, int z) {
        int worldX = x + chunk.getChunkX() * Chunk.CHUNK_SIZE;
        int worldY = y + chunk.getChunkY() * Chunk.CHUNK_SIZE;
        int worldZ = z + chunk.getChunkZ() * Chunk.CHUNK_SIZE;

        boolean removeDuplicateVertices = false;
        // Top face (y+)
        if (world.getBlock(worldX, worldY + 1, worldZ) == BlockType.AIR) {
            meshBuilder
                .rect(worldX, worldY + 1, worldZ + 1,
                    worldX + 1, worldY + 1, worldZ + 1,
                    worldX + 1, worldY + 1, worldZ,
                    worldX, worldY + 1, worldZ,
                    0, 1, 0);

            // Generate individual TriangleMeshes.
            triangleMesh.addTriangle(
                p1.set(x, y + 1, z + 1),
                p2.set(x + 1, y + 1, z + 1),
                p3.set(x + 1, y + 1, z),
                removeDuplicateVertices);
            triangleMesh.addTriangle(
                p1.set(x, y + 1, z + 1),
                p2.set(x + 1, y + 1, z),
                p3.set(x, y + 1, z),
                removeDuplicateVertices);

        }
        // Bottom face (y-)
        if (world.getBlock(worldX, worldY - 1, worldZ) == BlockType.AIR) {
            meshBuilder
                .rect(worldX, worldY, worldZ,
                    worldX + 1, worldY, worldZ,
                    worldX + 1, worldY, worldZ + 1,
                    worldX, worldY, worldZ + 1,
                    0, -1, 0);

            triangleMesh.addTriangle(
                p1.set(x, y, z),
                p2.set(x + 1, y, z),
                p3.set(x + 1, y, z + 1),
                removeDuplicateVertices);
            triangleMesh.addTriangle(
                p1.set(x, y, z),
                p2.set(x + 1, y, z + 1),
                p3.set(x, y, z + 1),
                removeDuplicateVertices);
        }
//         North face (z+)
        if (world.getBlock(worldX, worldY, worldZ + 1) == BlockType.AIR) {
            meshBuilder
                .rect(worldX, worldY, worldZ + 1,
                    worldX + 1, worldY, worldZ + 1,
                    worldX + 1, worldY + 1, worldZ + 1,
                    worldX, worldY + 1, worldZ + 1,
                    0, 0, 1);

            triangleMesh.addTriangle(
                p1.set(x, y, z + 1),
                p2.set(x + 1, y, z + 1),
                p3.set(x + 1, y + 1, z + 1),
                removeDuplicateVertices
            );
            triangleMesh.addTriangle(
                p1.set(x, y, z + 1),
                p2.set(x + 1, y + 1, z + 1),
                p3.set(x, y + 1, z + 1),
                removeDuplicateVertices
            );

        }
        // South face (z-)
        if (world.getBlock(worldX, worldY, worldZ - 1) == BlockType.AIR) {
            meshBuilder
                .rect(worldX + 1, worldY, worldZ,
                    worldX, worldY, worldZ,
                    worldX, worldY + 1, worldZ,
                    worldX + 1, worldY + 1, worldZ,
                    0, 0, -1);

            triangleMesh.addTriangle(
                p1.set(x + 1, y, z),
                p2.set(x, y, z),
                p3.set(x, y + 1, z),
                removeDuplicateVertices
            );
            triangleMesh.addTriangle(
                p1.set(x + 1, y, z),
                p2.set(x, y + 1, z),
                p3.set(x + 1, y + 1, z),
                removeDuplicateVertices
            );

        }
        // East face (x+)
        if (world.getBlock(worldX + 1, worldY, worldZ) == BlockType.AIR) {
            meshBuilder
                .rect(worldX + 1, worldY, worldZ + 1,
                    worldX + 1, worldY, worldZ,
                    worldX + 1, worldY + 1, worldZ,
                    worldX + 1, worldY + 1, worldZ + 1,
                    1, 0, 0);

            triangleMesh.addTriangle(
                p1.set(x + 1, y, z + 1),
                p2.set(x + 1, y, z),
                p3.set(x + 1, y + 1, z),
                removeDuplicateVertices
            );
            triangleMesh.addTriangle(
                p1.set(x + 1, y, z + 1),
                p2.set(x + 1, y + 1, z),
                p3.set(x + 1, y + 1, z + 1),
                removeDuplicateVertices
            );
        }
        // West face (x-)
        if (world.getBlock(worldX - 1, worldY, worldZ) == BlockType.AIR) {
            meshBuilder
                .rect(worldX, worldY, worldZ,
                    worldX, worldY, worldZ + 1,
                    worldX, worldY + 1, worldZ + 1,
                    worldX, worldY + 1, worldZ,
                    -1, 0, 0);

            triangleMesh.addTriangle(
                p1.set(x, y, z),
                p2.set(x, y, z + 1),
                p3.set(x, y + 1, z + 1),
                removeDuplicateVertices
            );
            triangleMesh.addTriangle(
                p1.set(x, y, z),
                p2.set(x, y + 1, z + 1),
                p3.set(x, y + 1, z),
                removeDuplicateVertices
            );
        }
    }

    /**
     * Takes all the triangleMesh faces built from Build to generate a BvhTriangleMeshShape and return it.
     * @return btBvhTriangleMeshShape
     */
}
