package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;

public class ViewCamera extends PerspectiveCamera {
    public ViewCamera() {
        super(80, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.near = 0.5f;
        this.far = 200f;
        this.update();
    }
}
