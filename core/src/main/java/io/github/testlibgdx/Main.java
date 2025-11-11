package io.github.testlibgdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.physics.bullet.Bullet;
import presentation.view.ViewManager;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    public ViewManager viewManager;

    @Override
    public void create() {
        Bullet.init(); // must be initialized before any bullet calls
        viewManager = new ViewManager();
    }

    @Override
    public void render() {
        viewManager.render();
    }

    @Override
    public void dispose() {
        viewManager.dispose();
    }
}
