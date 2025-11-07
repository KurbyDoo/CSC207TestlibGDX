import com.badlogic.gdx.physics.bullet.collision.*;

public final class PhysicsSystem implements Disposable {
    public final btCollisionWorld world; // or btDiscreteDynamicsWorld
    private final btDefaultCollisionConfiguration config;
    private final btCollisionDispatcher dispatcher;
    private final btDbvtBroadphase broadphase;

    // Store per-chunk handles so we can dispose later
    static final class ChunkHandle {
        final btTriangleIndexVertexArray vertexArray;
        final btBvhTriangleMeshShape shape;
        final btCollisionObject obj;
        ChunkHandle(btTriangleIndexVertexArray va, btBvhTriangleMeshShape sh, btCollisionObject o) {
            this.vertexArray = va; this.shape = sh; this.obj = o;
        }
        void dispose(btCollisionWorld world) {
            world.removeCollisionObject(obj);
            obj.dispose(); shape.dispose(); vertexArray.dispose();
        }
    }
    private final Map<Object, ChunkHandle> chunks = new HashMap<>();

    public PhysicsSystem() {
        Bullet.init(); // must be called before any Bullet class usage [2](http://xoppa.github.io/blog/using-the-libgdx-3d-physics-bullet-wrapper-part1/)
        config = new btDefaultCollisionConfiguration();
        dispatcher = new btCollisionDispatcher(config);
        broadphase = new btDbvtBroadphase();
        world = new btCollisionWorld(dispatcher, broadphase, config);
    }

    /** World-space mesh: identity transform. Returns a key you can store on the Chunk. */
    public Object addChunk(Model model, Object key) {
        btTriangleIndexVertexArray va = new btTriangleIndexVertexArray(model.meshParts);
        btBvhTriangleMeshShape shape = new btBvhTriangleMeshShape(va, true); // static BVH shape [3](https://www.youtube.com/playlist?list=PLjUR2MkQ0cuEda0_f-CoAZBowEN8gNSfJ)
        btCollisionObject obj = new btCollisionObject();
        obj.setCollisionShape(shape);
        obj.setWorldTransform(new Matrix4()); // identity for world-space vertices
        world.addCollisionObject(obj);
        chunks.put(key, new ChunkHandle(va, shape, obj));
        return key;
    }

    public void removeChunk(Object key) {
        ChunkHandle h = chunks.remove(key);
        if (h != null) h.dispose(world);
    }

    /** Call once per frame. For collision-only, run discrete collision. */
    public void step() {
        world.performDiscreteCollisionDetection(); // collision-only path [4](https://github.com/libgdx/libgdx/issues/4932)
    }

    @Override
    public void dispose() {
        // clean up all chunks
        for (ChunkHandle h : chunks.values()) h.dispose(world);
        chunks.clear();
        world.dispose();
        broadphase.dispose(); dispatcher.dispose(); config.dispose();
    }
}
