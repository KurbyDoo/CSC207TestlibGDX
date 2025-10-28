package io.github.testlibgdx;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;

public class Cylinder extends ModelInstance {
    private final Vector3 position = new Vector3();

    public Cylinder(Model model) {
        super(model);
    }

    public float getX() {
        transform.getTranslation(position);
        return position.x;
    }

    public float getY() {
        transform.getTranslation(position);
        return position.y;
    }

    public float getZ() {
        transform.getTranslation(position);
        return position.z;
    }
}
