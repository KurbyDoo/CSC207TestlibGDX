package io.github.testlibgdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;

public class SphereFactory implements Disposable {

    private int divisionU = 10;
    private int divisionV = 10;
    private float width = 0;
    private float height = 0;
    private float depth = 0;
    private final Material material = new Material(ColorAttribute.createDiffuse(Color.GREEN));
    private final long attributes = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal;

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
        ModelBuilder modelBuilder = new ModelBuilder();
        this.model = modelBuilder.createSphere(this.width,
            this.height,
            this.depth,
            this.divisionU,
            this.divisionV,
            this.material,
            this.attributes);
    }

    private void initModel() {
        ModelBuilder modelBuilder = new ModelBuilder();
        model = modelBuilder.createSphere(width, height, depth, divisionU, divisionV, material, attributes);
    }

    public Sphere createSphere(){
        return new Sphere(model);
    }

    @Override
    public void dispose(){this.model.dispose();}

}
