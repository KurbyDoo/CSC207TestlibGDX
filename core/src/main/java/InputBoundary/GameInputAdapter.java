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
    private float deltaX = 0;
    private float deltaY = 0;

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

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // Accumulate mouse movement to be processed once per frame.
        deltaX += Gdx.input.getDeltaX();
        deltaY += Gdx.input.getDeltaY();
        return true;
    }

    /**
     * Called once per frame to process all captured input.
     * @param deltaTime The time since the last frame.
     */
    public void processInput(float deltaTime) {
        PlayerMovementInputData inputData = new PlayerMovementInputData(
            keys.containsKey(Input.Keys.W),
            keys.containsKey(Input.Keys.S),
            keys.containsKey(Input.Keys.A),
            keys.containsKey(Input.Keys.D),
            keys.containsKey(Input.Keys.SHIFT_LEFT),
            deltaX,
            deltaY
        );

        playerMovementInteractor.execute(inputData, deltaTime);

        // Reset mouse deltas after they have been processed.
        deltaX = 0;
        deltaY = 0;
    }
}
