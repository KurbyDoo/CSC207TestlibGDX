package view;

public class ViewManager {
    private Viewable mainView;
    public ViewManager() {
        mainView = new GameView();
        mainView.createView();
    }

    public void render() {
        if (mainView != null) mainView.renderView();
    }

    public void dispose() {
        if (mainView != null) mainView.disposeView();
    }
}
