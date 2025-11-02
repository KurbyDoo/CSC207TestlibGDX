package io.github.testlibgdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;


public class CylinderFactory implements Disposable {
    private ModelBuilder modelBuilder;
    private final Model unitCylinderModel;
    private final ObjectMap<Color, Material> materialCache = new ObjectMap<>();

    public CylinderFactory() {
        modelBuilder = new ModelBuilder();
        unitCylinderModel = modelBuilder.createCylinder(
            1f, 4.0f, 1f, 20,
            new Material(ColorAttribute.createDiffuse(Color.LIGHT_GRAY)),
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
    public Cylinder createCylinder(Vector3 position) {
        Cylinder cylinder = new Cylinder(unitCylinderModel);
        cylinder.transform.setTranslation(position);

        // Decide the color according to the location
        Color color = (position.len() < 25)
            ? ((position.len() < 23) ? Color.SKY : Color.CYAN)
            : Color.NAVY;
        cylinder.materials.get(0).set(getMaterial(color));

        return cylinder;
    }

    @Override
    public void dispose(){
        unitCylinderModel.dispose();
    }

}

