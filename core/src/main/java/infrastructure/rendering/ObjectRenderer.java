package infrastructure.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;
import com.badlogic.gdx.math.Vector3;
import net.mgsx.gltf.loaders.gltf.GLTFLoader;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneAsset;
import net.mgsx.gltf.scene3d.scene.SceneManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ObjectRenderer {
    public Environment environment;

    public PerspectiveCamera camera;
    public ModelBatch modelBatch;
    public List<ModelInstance> models = new ArrayList<>();

    // testing with a zombie instance
    public ModelInstance zombieInstance;
    // add scene attributes (to load models)
    private SceneManager sceneManager;
//    private SceneAsset sceneAsset;
//    private Scene scene;

    public BlockingQueue<ModelInstance> toAdd = new LinkedBlockingQueue<>();

    public ObjectRenderer(PerspectiveCamera camera) {
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        modelBatch = new ModelBatch();

        this.camera = camera;

        //set up scene
//        sceneAsset = new GLTFLoader().load(Gdx.files.internal("models/model.gltf"));
//        scene = new Scene(sceneAsset.scene);
        sceneManager = new SceneManager();
        sceneManager.setShaderProvider(new DefaultShaderProvider());
//        sceneManager.addScene(scene);
        sceneManager.setCamera(camera);
        sceneManager.setAmbientLight(1f);
    }

    public void add(ModelInstance modelInstance) {
        toAdd.add(modelInstance);
    }

    public void addZombieInstance(ModelInstance zombieInstance) {
        this.zombieInstance = zombieInstance;
    }

    public void addZombieToScene(Scene scene) {
        System.out.println("Zombie added to scene.");
        sceneManager.addScene(scene);
    }

    private void updateRenderList() {
        ModelInstance instance;
        while ((instance = toAdd.poll()) != null) {
            models.add(instance);
        }
    }

    public void render(Float deltaTime, Vector3 playerPos) {
        updateRenderList();

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        // Move zombie
        //zombieInstance.transform.translate(deltaTime*2, 0f, 0f);
        if (zombieInstance != null) {
            Vector3 zombiePos = zombieInstance.transform.getTranslation(new Vector3());
            Vector3 direction = new Vector3(playerPos).sub(zombiePos).nor();
            float speed = 2f;
            zombiePos.add(direction.scl(deltaTime * speed));
            zombieInstance.transform.setTranslation(zombiePos);
        }

        // render zombie model
//        Vector3 zombiePos = scene.modelInstance.transform.getTranslation(new Vector3());
//        Vector3 direction = new Vector3(playerPos).sub(zombiePos).nor();
//        float speed = 2f;
//        zombiePos.add(direction.scl(deltaTime * speed));
//        scene.modelInstance.transform.setTranslation(zombiePos);
//        //scene.modelInstance.transform.setToTranslation(0, 14f, 0f);
        sceneManager.update(deltaTime);
        sceneManager.render();

        modelBatch.begin(camera);

        for (ModelInstance modelInstance : models) {
            modelBatch.render(modelInstance, environment);
        }
        //render zombie
        //modelBatch.render(zombieInstance, environment);

        modelBatch.end();
    }

    public void dispose() {
        modelBatch.dispose();
        models.clear();
        sceneManager.dispose();
//        sceneAsset.dispose();
    }
}
