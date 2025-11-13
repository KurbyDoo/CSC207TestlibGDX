package domain.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import infrastructure.rendering.ObjectRenderer;
import net.mgsx.gltf.loaders.gltf.GLTFLoader;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneAsset;
import net.mgsx.gltf.scene3d.scene.SceneManager;

public class Zombie {
    final private ObjectRenderer objectRenderer;

    public Zombie(ObjectRenderer objectRenderer) {
        this.objectRenderer = objectRenderer;
    }

    public void createZombie() {
        System.out.println("Zombie created");
        Model zombieModel;
        ModelBuilder modelBuilder = new ModelBuilder();
        zombieModel = modelBuilder.createBox(1f, 1f, 1f,
            new Material(ColorAttribute.createDiffuse(Color.BLUE)),
            VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        ModelInstance zombieInstance = new ModelInstance(zombieModel);
        zombieInstance.transform.setToTranslation(0f, 14f, 0f);
        objectRenderer.addZombieInstance(zombieInstance);
    }

    public void renderZombie() {
    }
}
