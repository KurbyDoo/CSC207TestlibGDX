package io.github.testlibgdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;

public class CubeFactory implements Disposable {
//    Performance (e.g., bullets): UseObject Pooling.
//    For the Future: Entity-Component-System with Ashley. Scalable pattern for building complex games.
    public ModelBuilder modelBuilder;
    public Model unitCubeModel;

    private final ObjectMap<Color, Material> materialCache = new ObjectMap<>();


    public CubeFactory() {
        modelBuilder = new ModelBuilder();
        unitCubeModel = modelBuilder.createBox(
            1f, 1f, 1f,
            new Material(ColorAttribute.createDiffuse(Color.GRAY)),
            VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal
        );
    }

    private Material getMaterial(Color color) {
        if (materialCache.containsKey(color)) {
            return materialCache.get(color);
        }
        Material newMaterial = new Material(ColorAttribute.createDiffuse(color));
        materialCache.put(color, newMaterial);
        return newMaterial;
    }

    public Cube createCube(Vector3 position) {
        Cube cube = new Cube(unitCubeModel);
        cube.transform.setTranslation(position);

        cube.materials.get(0).set(getMaterial(position.len() < 25 ? position.len() < 23 ? Color.GREEN : Color.YELLOW : Color.BLUE));
        return cube;
    }

    @Override
    public void dispose() {
        unitCubeModel.dispose();
    }
}
