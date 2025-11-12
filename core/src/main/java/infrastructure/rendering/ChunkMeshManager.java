package infrastructure.rendering;

import java.util.ArrayList;
import java.util.List;

public class ChunkMeshManager {
    static public List<ChunkMeshData> data = new ArrayList<ChunkMeshData>();

    public void addData(ChunkMeshData cmd){data.add(cmd);}

    public void dispose(){
        for(ChunkMeshData d : data){
            d.getTriangleMesh().dispose();
            d.getBvhTriangle().dispose();
        }
    }

}
