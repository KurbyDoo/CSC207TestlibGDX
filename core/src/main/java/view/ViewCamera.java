package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;

public class ViewCamera extends PerspectiveCamera {
    public ViewCamera() {
        super(80, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.near = 1f;
        this.far = 300f;
        this.update();
    }
}
