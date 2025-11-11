package io.github.testlibgdx;

import java.util.ArrayList;

public class CollisionHandler {
    public GameObject obj1;
    public GameObject obj2;
    public ArrayList<GameObject> collisionBlocks;


    public CollisionHandler(){}


    public void add(GameObject object){
        collisionBlocks.add(object);
    }

//    public boolean checkCollision(){
//
//    }

}
