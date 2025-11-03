package Entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;

public class PyramidFactory implements Disposable {

    public ModelBuilder modelBuilder;
    public Model unitPyramidModel;
    private final ObjectMap<Color, Material> materialCache = new ObjectMap<>();

    public PyramidFactory() {
        modelBuilder = new ModelBuilder();
        unitPyramidModel = modelBuilder.createCone(
            1f, 1.5f, 1f,
            4,
            new Material(ColorAttribute.createDiffuse(Color.RED)),
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

    public Pyramid createPyramid(Vector3 position) {
        Pyramid pyramid = new Pyramid(unitPyramidModel);
        pyramid.transform.setTranslation(position);

        // Example: color based on distance from origin (like CubeFactory)
        Color color = (position.len() < 25) ? ((position.len() < 23) ? Color.PURPLE : Color.ORANGE) : Color.MAGENTA;
        pyramid.materials.get(0).set(getMaterial(color));
        return pyramid;
    }

    @Override
    public void dispose() {
        unitPyramidModel.dispose();
    }
}
