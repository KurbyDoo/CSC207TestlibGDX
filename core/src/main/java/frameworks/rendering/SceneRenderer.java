package frameworks.rendering;

import domain.entities.Chunk;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SceneRenderer implements RenderPresenter {
    private Environment environment;
    private PerspectiveCamera cam;
    private adapters.controllers.CameraInputController cameraController;
    private ModelBatch modelBatch;
    private List<ModelInstance> models = new ArrayList<>();
    private BlockingQueue<ModelInstance> toAdd = new LinkedBlockingQueue<>();
    private ChunkMeshBuilder meshBuilder;

    public SceneRenderer() {
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        modelBatch = new ModelBatch();
        meshBuilder = new ChunkMeshBuilder();

        cam = new PerspectiveCamera(80, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(0, 200f, 0);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();

        cameraController = new adapters.controllers.CameraInputController(cam);
        Gdx.input.setInputProcessor(cameraController);
        Gdx.input.setCursorCatched(true);
    }

    @Override
    public void presentChunk(Chunk chunk) {
        ModelInstance model = meshBuilder.build(chunk);
        toAdd.add(model);
    }

    private void updateRenderList() {
        ModelInstance instance;
        while ((instance = toAdd.poll()) != null) {
            models.add(instance);
        }
    }

    public void render() {
        updateRenderList();

        cameraController.update(Gdx.graphics.getDeltaTime());

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        modelBatch.begin(cam);
        for (ModelInstance modelInstance : models) {
            modelBatch.render(modelInstance, environment);
        }
        modelBatch.end();
    }

    public void dispose() {
        modelBatch.dispose();
        models.clear();
    }
}
