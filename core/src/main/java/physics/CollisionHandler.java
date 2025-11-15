package physics;

import com.badlogic.gdx.physics.bullet.collision.ContactListener;
import com.badlogic.gdx.physics.bullet.collision.btCollisionWorld;

import java.util.ArrayList;

public class CollisionHandler extends ContactListener {
    public ArrayList<GameObject> collisionBlocks;

    private BroadphaseInterface broadphase = new DbvtBroadphase();
    private CollisionConfiguration config = new DefaultCollisionConfiguration();
    private CollisionDispatcher dispatcher = new CollisionDispatcher(config);

    btCollisionWorld world = new btCollisionWorld(dispatcher, broadphase, config);


    public CollisionHandler(){}


    public void add(GameObject object){
        collisionBlocks.add(object);
    }

    public boolean checkCollision(){

    }

}
