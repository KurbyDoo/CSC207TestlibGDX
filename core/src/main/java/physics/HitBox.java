package physics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btSphereShape;
import com.badlogic.gdx.utils.Disposable;

import static com.badlogic.gdx.graphics.GL20.GL_ONE_MINUS_SRC_ALPHA;
import static com.badlogic.gdx.graphics.GL20.GL_SRC_ALPHA;

/**
 * This is a helper class to a GameObject
 */
public class HitBox extends Model implements Disposable {

    public enum ShapeTypes{
        SPHERE,
        BOX
        // can add other shapes but I think these two are the most useful
    }

    private final ModelBuilder model = new ModelBuilder();
    private Model complete = new Model();

    // transparent red material
    static private final Color TRANSPARENT = new Color(1f, 0f, 0f, 0.5f); //RED GREEN BLUE ALPHA
    static private final BlendingAttribute ATTRIBUTE = new BlendingAttribute(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    static private final Material material = new Material(ColorAttribute.createDiffuse(TRANSPARENT), ATTRIBUTE);

    public int w;
    public int h;
    public int d;
    public String id;
    public ShapeTypes type;

    // potentially expand more constructors for different shapes
    public HitBox(String id, ShapeTypes type, int w, int h, int d){
        this.id = id;
        this.type = type;
        this.w = w;
        this.h = h;
        this.d = d;
    }

    public GameObject Construct(){

        switch(type){
            case BOX:
                complete = model.createBox(w, h, d, material, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
                return new GameObject(complete, "box", new btBoxShape(new Vector3())).construct();

            case SPHERE:
                complete = model.createSphere(w, h, d, 8, 8, material, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
                return new GameObject.Constructor(complete, "sphere", new btSphereShape(3f)).construct();
            default:
                throw new IllegalArgumentException("Unknown Shape type: " + type);
        }

    }

    @Override
    public void dispose(){
        complete.dispose();
    }

}
