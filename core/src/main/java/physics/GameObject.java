package physics;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.utils.Disposable;

public class GameObject extends ModelInstance implements Disposable {

    public final btCollisionObject body;
    public boolean moving;

    public GameObject(Model model, String node, btCollisionShape shape){
        super(model, node);
        body = new btCollisionObject();
        body.setCollisionShape(shape);
    }

    @Override
    public void dispose () {
        body.dispose();
    }

    static class Constructor implements Disposable {
        public final Model model;
        public final String node;
        public final btCollisionShape shape;

        public Constructor (Model model, String node, btCollisionShape shape) {
            this.model = model;
            this.node = node;
            this.shape = shape;
        }

        public GameObject construct () {
            return new GameObject(model, node, shape);
        }

        @Override
        public void dispose () {
            shape.dispose();
        }
    }

}
