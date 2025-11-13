package io.github.testlibgdx;

import com.badlogic.gdx.physics.bullet.collision.btBvhTriangleMeshShape;
import domain.entities.Chunk;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.utils.ArrayMap;

import java.util.ArrayList;

import infrastructure.rendering.ChunkMeshData;
import infrastructure.rendering.GameMeshBuilder;
import infrastructure.rendering.ObjectRenderer;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

// TODO: Eventually make this multithreaded
public class ChunkLoader {
    private BlockingQueue<Chunk> chunksToLoad;
    private final GameMeshBuilder meshBuilder;
    private final ObjectRenderer objectRenderer;

    private final int BUFFER_SIZE = 32;
    // private HashMap<String, GameObject.Constructor> constructor = new HashMap<>(tring.class, GameObject.Constrctor.class);

    private ArrayList<GameObject.Constructor> constructor2;

    public ChunkLoader(GameMeshBuilder meshBuilder, ObjectRenderer objectRenderer) {
        this.meshBuilder = meshBuilder;
        this.objectRenderer = objectRenderer;
        chunksToLoad = new LinkedBlockingQueue<>();
    }

    public void addChunkToLoad(Chunk chunk) {
        chunksToLoad.add(chunk);
    }

    public void loadChunks() {
        try {
            Chunk chunk;
            for (int i = 0; i < BUFFER_SIZE && ((chunk = chunksToLoad.poll()) != null); i++) {

                ChunkMeshData chunkMesh;
                chunkMesh = meshBuilder.build(chunk);
                objectRenderer.add(chunkMesh.getModel());
//                objectRenderer.add(meshBuilder.build(chunk));
                objectRenderer.collisionAssets(chunkMesh.getBvhTriangle());
                objectRenderer.addMeshData(chunkMesh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
