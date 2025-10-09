package io.github.testlibgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;

import java.util.ArrayList;
import java.util.List;

public class ObjectRenderer {
    public Environment environment;
    public PerspectiveCamera cam;
    public CameraInputController camController;
    public ModelBatch modelBatch;
    public List<ModelInstance> models = new ArrayList<>();

    public CubeFactory cubeFactory;

    public ObjectRenderer() {
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        modelBatch = new ModelBatch();

        cam = new PerspectiveCamera(80, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(10f, 10f, 10f);
        cam.lookAt(0,0,0);
        cam.near = 1f;
        cam.far = 100f;
        cam.update();

        // Camera Controller
        camController = new CameraInputController(cam);
        Gdx.input.setInputProcessor(camController);
    }

    public void add(ModelInstance modelInstance) {
        models.add(modelInstance);
    }

    public void render() {
        camController.update();

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
