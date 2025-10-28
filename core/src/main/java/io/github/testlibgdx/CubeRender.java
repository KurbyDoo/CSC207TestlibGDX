package io.github.testlibgdx;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;

public class CubeRender extends ModelInstance {
    private final Vector3 position = new Vector3();
    public CubeRender(Model model) {
        super(model);
    }

    public float getX() {
        super.transform.getTranslation(position);
        return position.x;
    }

    public float getY() {
        super.transform.getTranslation(position);
        return position.y;
    }

    public float getZ() {
        super.transform.getTranslation(position);
        return position.z;
    }

}
