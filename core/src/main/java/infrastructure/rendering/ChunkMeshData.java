package infrastructure.rendering;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.physics.bullet.collision.btBvhTriangleMeshShape;
import com.badlogic.gdx.physics.bullet.collision.btTriangleMesh;
import domain.entities.Chunk;
import domain.entities.World;

public class ChunkMeshData {
    final public ModelInstance model;
    final public btTriangleMesh triangle;
    final public btBvhTriangleMeshShape shape;

    public ChunkMeshData(){
        model = null;
        triangle = null;
        shape = null;
    }

    public ChunkMeshData(ModelInstance model, btTriangleMesh triangle, btBvhTriangleMeshShape shape){
        this.model = model;
        this.triangle = triangle;
        this.shape = shape;
    }

    public ModelInstance getModel(){
        return this.model;
    }

    public btTriangleMesh getTriangleMesh(){
        return this.triangle;
    }

    public btBvhTriangleMeshShape getBvhTriangle(){
        return this.shape;
    }

}
