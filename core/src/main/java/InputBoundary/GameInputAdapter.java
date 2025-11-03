package InputBoundary;

import UseCases.PlayerMovement.PlayerMovementInputBoundary;
import UseCases.PlayerMovement.PlayerMovementInputData;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.utils.IntIntMap;

public class GameInputAdapter extends InputAdapter {
    private final IntIntMap keys = new IntIntMap();
    private final PlayerMovementInputBoundary playerMovementInteractor;

    private int lastMouseX = -1;
    private int lastMouseY = -1;
    private float currentDeltaX = 0;
    private float currentDeltaY = 0;

    public GameInputAdapter(PlayerMovementInputBoundary playerMovementInteractor) {
        this.playerMovementInteractor = playerMovementInteractor;
    }

    @Override
    public boolean keyDown(int keycode) {
        keys.put(keycode, keycode);
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        keys.remove(keycode, 0);
        return true;
    }

    /**
     * This method now ONLY records the latest mouse position.
     * No deltas are read or accumulated here.
     */
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        if (lastMouseX == -1) {
            lastMouseX = screenX;
            lastMouseY = screenY;
        }

        currentDeltaX += screenX - lastMouseX;
        currentDeltaY += screenY - lastMouseY;

        lastMouseX = screenX;
        lastMouseY = screenY;

        return true;
    }

    /**
     * Called once per frame to process all captured input.
     * @param deltaTime The time since the last frame.
     */
    public void processInput(float deltaTime) {
        if (!Gdx.input.isCursorCatched()) {
            currentDeltaX = 0;
            currentDeltaY = 0;
        }

        PlayerMovementInputData inputData = new PlayerMovementInputData(
            keys.containsKey(Input.Keys.W),
            keys.containsKey(Input.Keys.S),
            keys.containsKey(Input.Keys.A),
            keys.containsKey(Input.Keys.D),
            keys.containsKey(Input.Keys.SHIFT_LEFT),
            currentDeltaX,
            -currentDeltaY
        );

        playerMovementInteractor.execute(inputData, deltaTime);

        currentDeltaX = 0;
        currentDeltaY = 0;

        if (Gdx.input.isCursorCatched()) {
            int centerX = Gdx.graphics.getWidth() / 2;
            int centerY = Gdx.graphics.getHeight() / 2;
            Gdx.input.setCursorPosition(centerX, centerY);
            lastMouseX = centerX;
            lastMouseY = centerY;
        }
    }
}
