package infrastructure.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.Vector3;
import domain.entities.Chunk;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ObjectRenderer {
    public Environment environment;

    public PerspectiveCamera camera;
    public ModelBatch modelBatch;
    public List<ModelInstance> models = new ArrayList<>();

    public BlockingQueue<ModelInstance> toAdd = new LinkedBlockingQueue<>();

    public ObjectRenderer(PerspectiveCamera camera) {
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        modelBatch = new ModelBatch();

        this.camera = camera;
    }

    public void add(ModelInstance modelInstance) {
        toAdd.add(modelInstance);
    }

    private void updateRenderList() {
        ModelInstance instance;
        while ((instance = toAdd.poll()) != null) {
            models.add(instance);
        }
    }

    public void render() {
        updateRenderList();

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        modelBatch.begin(camera);
        for (ModelInstance modelInstance : models) {
            modelBatch.render(modelInstance, environment);
        }
        modelBatch.end();
    }

    public void dispose() {
        modelBatch.dispose();
        models.clear();
    }

    public void removeChunksFarFrom(Vector3 playerChunk, int renderDistance) {
        models.removeIf(instance -> {
            Vector3 pos = instance.transform.getTranslation(new Vector3());

            int chunkX = (int) Math.floor(pos.x / Chunk.CHUNK_SIZE);
            int chunkY = (int) Math.floor(pos.y / Chunk.CHUNK_SIZE);
            int chunkZ = (int) Math.floor(pos.z / Chunk.CHUNK_SIZE);

            // Compare distance without creating extra Vector3 object
            int dx = chunkX - (int)playerChunk.x;
            int dy = chunkY - (int)playerChunk.y;
            int dz = chunkZ - (int)playerChunk.z;

            double distance = Math.sqrt(dx*dx + dy*dy + dz*dz);
            return distance > renderDistance;
        });
    }

    public void hideChunk(Vector3 chunkPos) {
        for (ModelInstance instance : models) {
            if (instance.userData instanceof Vector3) {
                Vector3 pos = (Vector3) instance.userData;
                if (pos.epsilonEquals(chunkPos, 0.1f)) {
                    instance.transform.setTranslation(0, -9999, 0); // hide underground
                }
            }
        }
    }

    public void showChunk(Vector3 chunkPos) {
        for (ModelInstance instance : models) {
            if (instance.userData instanceof Vector3) {
                Vector3 pos = (Vector3) instance.userData; // Manual cast for Java 8
                if (pos.epsilonEquals(chunkPos, 0.1f)) {
                    // Move it back to its actual chunk position
                    instance.transform.setTranslation(
                        pos.x * Chunk.CHUNK_SIZE,
                        pos.y * Chunk.CHUNK_SIZE,
                        pos.z * Chunk.CHUNK_SIZE
                    );
                }
            }
        }
    }
}
