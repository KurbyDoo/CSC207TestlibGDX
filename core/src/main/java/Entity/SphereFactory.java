package Entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.utils.Disposable;

public class SphereFactory implements Disposable {

    private int divisionU = 100;
    private int divisionV = 100;
    private float width = 10;
    private float height = 10;
    private float depth = 10;
    private Material material;
    private long attributes;

    Model model;

    public SphereFactory(){
        initModel();
    }

    public SphereFactory(float width, float height, float depth, int divisionU, int divisionV) {
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.divisionU = divisionU;
        this.divisionV = divisionV;
        material =  new Material(ColorAttribute.createDiffuse(Color.GRAY));
        attributes =  VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal;
        ModelBuilder modelBuilder = new ModelBuilder();
        model = modelBuilder.createSphere(1f, 1f, 1f, 30, 30, material, attributes);
    }

    private void initModel() {
        ModelBuilder modelBuilder = new ModelBuilder();
        model = modelBuilder.createSphere(width, height, depth, divisionU, divisionV, material, attributes);
    }

    public Sphere createSphere(){
        return new Sphere(model);
    }

    @Override
    public void dispose(){
        this.model.dispose();
    }

}
