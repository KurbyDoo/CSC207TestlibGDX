package io.github.testlibgdx;

import com.badlogic.gdx.ApplicationAdapter;
import presentation.view.ViewManager;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    public ViewManager viewManager;

    @Override
    public void create() {
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
