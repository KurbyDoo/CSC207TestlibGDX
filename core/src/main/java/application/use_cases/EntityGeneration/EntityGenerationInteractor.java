package application.use_cases.EntityGeneration;

import com.badlogic.gdx.Gdx;
import domain.entities.Zombie;
import infrastructure.rendering.ObjectRenderer;
import net.mgsx.gltf.loaders.gltf.GLTFLoader;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneAsset;

public class EntityGenerationInteractor implements EntityGenerationInputBoundary{
    private Zombie zombie;
    private Scene scene;
    private SceneAsset sceneAsset;
    private ObjectRenderer objectRenderer;

    public EntityGenerationInteractor(ObjectRenderer objectRenderer) {
        System.out.println("EntityGenerationInteractor initialized.");
        this.objectRenderer = objectRenderer;
        sceneAsset = new GLTFLoader().load(Gdx.files.internal("models/model.gltf"));
        scene = new Scene(sceneAsset.scene);
    }

    @Override
    public void execute(EntityGenerationInputData inputData) {
        System.out.println("EntityGenerationInteractor executed.");
        objectRenderer.addZombieToScene(scene);
    }
}
