package io.github.testlibgdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.math.Vector3;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    public ObjectRenderer objectRenderer;
    public CubeFactory cubeFactory;

    public SphereFactory sphere;

    @Override
    public void create() {
        objectRenderer = new ObjectRenderer();
        cubeFactory = new CubeFactory();
        sphere = new SphereFactory(0,0,10,40,40);
        // Create grid of cubes
        for (int i = -50; i <= 50; i++) {
            for (int j = -50; j <= 50; j++) {
                objectRenderer.add(cubeFactory.createCube(new Vector3(i, 1, j)));
            }
        }

        //Sphere hovering above the origin
        objectRenderer.add(sphere.createSphere());

    }


    @Override
    public void render() {
        objectRenderer.render();
    }

    @Override
    public void dispose() {
        objectRenderer.dispose();
        cubeFactory.dispose();
        sphere.dispose();
    }
}
